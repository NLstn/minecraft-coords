package com.nlstn.coords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class CoordsFileHandler {

    private static Path dataPath;

    public static List<Coordinate> loadFile(JavaPlugin plugin) {

        List<Coordinate> result = new ArrayList<>();

        List<String> lines = null;

        try {
            lines = Files.readAllLines(getFilePath(plugin));
        } catch (IOException e) {
            plugin.getLogger().info("No data file found");
            return result;
        }

        lines.stream().forEach((line) -> {
            if (line == null || line.length() == 0 || line.equals(""))
                return;

            String[] parts = line.split("/");
            if (parts.length != 3) {
                plugin.getLogger().warning("Invalid coordinate: \"" + line + "\"");
                return;
            }
            Coordinate coordinate = new Coordinate(parts[0], parts[1], parts[2]);
            result.add(coordinate);
        });

        return result;
    }

    public static void saveCoords(JavaPlugin plugin, List<Coordinate> coords) throws IOException {

        StringBuffer result = new StringBuffer();

        coords.forEach((coord) -> {

            result.append(coord.getId() + "/" + coord.getOwner() + "/" + coord.getCoord() + "\n");

        });
        plugin.getLogger().info("Saving Coords");

        Files.write(getFilePath(plugin), result.toString().getBytes());
    }

    private static Path getFilePath(JavaPlugin plugin) {
        if (dataPath != null)
            return dataPath;

        String fileName = "";
        if (System.getProperty("os.name").contains("win")) {
            fileName = plugin.getDataFolder().getAbsolutePath() + "\\coords.yml";
        } else {
            fileName = plugin.getDataFolder().getAbsolutePath() + "/coords.yml";
        }

        plugin.getLogger().info("Data file name: \"" + fileName + "\"");

        Path folderPath = Paths.get(plugin.getDataFolder().getAbsolutePath());

        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataPath = Paths.get(fileName);
        if (!Files.exists(dataPath)) {
            try {
                Files.createFile(dataPath);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return dataPath;
    }

}
