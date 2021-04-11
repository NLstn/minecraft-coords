package com.nlstn.coords.executors;

import java.util.ArrayList;
import java.util.List;

import com.nlstn.coords.Coordinate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearExecutor extends CoordExecutor {

    private List<String> pendingClears = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only supported for the player");
            return true;
        }

        Player player = (Player) sender;

        if (!pendingClears.contains(player.getUniqueId().toString())) {
            pendingClears.add(player.getUniqueId().toString());
            sender.sendMessage("Please execute the command again to confirm");
            return true;
        }

        pendingClears.remove(player.getUniqueId().toString());

        List<Coordinate> toRemove = new ArrayList<Coordinate>();

        plugin.getCoords().stream().filter((coord) -> coord.getOwner().equals(player.getUniqueId().toString()))
                .forEach((coord) -> toRemove.add(coord));

        plugin.getCoords().removeAll(toRemove);
        sender.sendMessage("Removed all your Coordinates");

        return false;
    }

}
