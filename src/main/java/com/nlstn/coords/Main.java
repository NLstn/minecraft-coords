package com.nlstn.coords;

import java.util.List;
import java.util.Optional;

import com.nlstn.coords.executors.AddExecutor;
import com.nlstn.coords.executors.ClearExecutor;
import com.nlstn.coords.executors.BroadcastExecutor;
import com.nlstn.coords.executors.DelExecutor;
import com.nlstn.coords.executors.ListExecutor;
import com.nlstn.coords.executors.SetGlobalExecutor;
import com.nlstn.coords.executors.SaveExecutor;

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
        getCommand("clearCoords").setExecutor(new ClearExecutor());
        getCommand("setCoordGlobal").setExecutor(new SetGlobalExecutor());
        getCommand("broadcastCoord").setExecutor(new BroadcastExecutor());
        getCommand("saveCoords").setExecutor(new SaveExecutor());
    }

    @Override
    public void onDisable() {
        save();
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

    public void save() {
        try {
            CoordsFileHandler.saveCoords(this, coords);
        } catch (Exception e) {
            getLogger().info("Failed to save data file: " + e.getMessage());
        }
    }

    private void checkSave() {
        if (changeCounter < getConfig().getInt("saveInterval"))
            return;
        save();
    }

    public List<Coordinate> getCoords() {
        return coords;
    }

    public Optional<Coordinate> findCoord(String id, String owner) {
        return coords.stream().filter(coord -> coord.getId().equals(id) && coord.getOwner().equals(owner)).findAny();
    }

}
