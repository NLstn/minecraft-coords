package com.nlstn.coords.executors;

import java.util.Optional;

import com.nlstn.coords.Coordinate;
import com.nlstn.coords.Main;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AddExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Command line cannot add coords!");
            return false;
        }

        if (sender instanceof BlockCommandSender) {
            sender.sendMessage("Command blocks cannot add coords!");
            return false;
        }

        if (args.length != 1 && args.length != 2)
            return false;

        Optional<Coordinate> existing = plugin.findCoord(args[0]);
        if (existing.isPresent()) {
            sender.sendMessage("ID '" + args[0] + "' already in use");
            return true;
        }

        Player player = (Player) sender;

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        String owner = String.valueOf(player.getUniqueId().toString());
        if (args.length == 2) {
            if (args[1].equals("-g")) {
                owner = Main.OWNER_GLOBAL;
            } else {
                sender.sendMessage("Unknown second argument '" + args[1] + "', expected '-g'");
                return false;
            }
        }

        Coordinate coord = new Coordinate(args[0], owner, x + ", " + y + ", " + z);

        plugin.addCoord(coord);

        sender.sendMessage("Added " + args[0] + " as " + x + "/" + y + "/" + z);

        return true;
    }

}
