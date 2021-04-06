package com.nlstn.coords.executors;

import java.util.List;
import java.util.stream.Stream;

import com.nlstn.coords.Coordinate;
import com.nlstn.coords.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Coordinate> currentCoords = plugin.getCoords();

        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        final String owner = sender instanceof Player ? String.valueOf(player.getUniqueId().toString())
                : Main.OWNER_GLOBAL;

        Stream<Coordinate> filtered = currentCoords.stream()
                .filter(coord -> coord.getOwner().equals(owner) || coord.getOwner().equals(Main.OWNER_GLOBAL));

        filtered.forEach(coord -> sender.sendMessage(coord.toString()));

        return false;
    }

}
