package com.nlstn.coords.executors;

import java.util.Optional;

import com.nlstn.coords.Coordinate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can delete coords yet");
            return true;
        }

        String owner = ((Player) sender).getUniqueId().toString();

        Optional<Coordinate> coordinateResult = plugin.findCoord(args[0], owner);

        if (!coordinateResult.isPresent()) {
            sender.sendMessage("Coordinate '" + args[0] + "' not found");
            return true;
        }

        plugin.removeCoord(coordinateResult.get());
        sender.sendMessage("Deleted " + args[0]);

        return true;
    }

}
