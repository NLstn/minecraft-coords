package com.nlstn.coords.executors;

import java.util.Optional;

import com.nlstn.coords.Coordinate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DelExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;

        Optional<Coordinate> coordinateResult = plugin.findCoord(args[0]);

        if (!coordinateResult.isPresent()) {
            sender.sendMessage("Coordinate '" + args[0] + "' not found");
            return true;
        }

        plugin.removeCoord(coordinateResult.get());
        sender.sendMessage("Deleted " + args[0]);

        return true;
    }

}
