package com.nlstn.coords.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SaveExecutor extends CoordExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.save();
        sender.sendMessage("Saved Coords");
        return false;
    }

}
