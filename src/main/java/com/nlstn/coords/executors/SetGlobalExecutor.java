package com.nlstn.coords.executors;

import java.util.Optional;

import com.nlstn.coords.Coordinate;
import com.nlstn.coords.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGlobalExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available to players");
            return true;
        }

        if (args.length != 1 && args.length != 2)
            return false;

        Player player = (Player) sender;

        String owner = player.getUniqueId().toString();

        Optional<Coordinate> coordOpt = plugin.findCoord(args[0], owner);
        if (!coordOpt.isPresent()) {
            sender.sendMessage("Coordinate " + args[0] + " not found");
            return true;
        }
        Coordinate localCoord = coordOpt.get();

        Optional<Coordinate> globalOpt = plugin.findCoord(args[0], Main.OWNER_GLOBAL);
        if (globalOpt.isPresent()) {
            sender.sendMessage("ID " + args[0] + " is already used in global, please specify a new name");
            return true;
        }

        plugin.getCoords().remove(localCoord);
        localCoord.setOwner(Main.OWNER_GLOBAL);
        plugin.getCoords().add(localCoord);
        sender.sendMessage("Changed coordinate " + args[0] + " to global");
        return false;
    }

}
