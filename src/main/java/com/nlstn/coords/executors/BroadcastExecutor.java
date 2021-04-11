package com.nlstn.coords.executors;

import java.util.Optional;

import com.nlstn.coords.Coordinate;
import com.nlstn.coords.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available to players");
            return true;
        }

        if (args.length != 1 && args.length != 2)
            return false;

        String owner = ((Player) sender).getUniqueId().toString();

        if (args.length == 2 && args[1].equals("-g"))
            owner = Main.OWNER_GLOBAL;

        Optional<Coordinate> coordOpt = plugin.findCoord(args[0], owner);
        if (!coordOpt.isPresent()) {
            sender.sendMessage("Coordinate " + args[0] + " not found");
            return true;
        }

        Coordinate coord = coordOpt.get();

        Bukkit.broadcastMessage(coord.toString());

        return false;
    }

}
