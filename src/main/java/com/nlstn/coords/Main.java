package com.nlstn.coords;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.nlstn.coords.executors.AddExecutor;
import com.nlstn.coords.executors.DelExecutor;
import com.nlstn.coords.executors.ListExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static final String OWNER_GLOBAL = "GLOBAL";

    private List<Coordinate> coords;
    private int changeCounter;

    @Override
    public void onEnable() {

        FileConfiguration config = getConfig();
        config.addDefault("saveInterval", 5);
        config.options().copyDefaults();
        saveConfig();

        coords = CoordsFileHandler.loadFile(this);

        getLogger().info(coords.size() + " coordinates loaded");

        getCommand("addCoord").setExecutor(new AddExecutor());
        getCommand("listCoords").setExecutor(new ListExecutor());
        getCommand("delCoord").setExecutor(new DelExecutor());
    }

    @Override
    public void onDisable() {

        try {
            CoordsFileHandler.saveCoords(this, coords);
        } catch (IOException e) {
            getLogger().warning("Failed to save data file: " + e.getMessage());
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        getLogger().info("Sender: " + sender.getName() + " Command: " + command.getName() + " Label: " + label);
        return false;
    }

    public void addCoord(Coordinate coord) {
        coords.add(coord);
        changeCounter++;
        checkSave();
    }

    public void removeCoord(Coordinate coord) {
        coords.remove(coord);
        changeCounter++;
        checkSave();
    }

    private void checkSave() {
        if (changeCounter < getConfig().getInt("saveInterval"))
            return;
        try {
            CoordsFileHandler.saveCoords(this, coords);
        } catch (Exception e) {
            getLogger().info("Failed to save data file: " + e.getMessage());
        }
    }

    public List<Coordinate> getCoords() {
        return coords;
    }

    public Optional<Coordinate> findCoord(String id) {
        return coords.stream().filter(coord -> coord.getId().equals(id)).findAny();
    }

}
