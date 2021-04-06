package com.nlstn.coords.executors;

import com.nlstn.coords.Main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CoordExecutor implements CommandExecutor {

    protected Main plugin;

    public CoordExecutor() {
        this.plugin = JavaPlugin.getPlugin(Main.class);
    }

}
