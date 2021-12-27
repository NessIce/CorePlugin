package me.ness.core.bungee.managers;

import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.commands.AntibotCommand;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandBungeeManager {

    private static final CoreBungee plugin = CoreBungee.getCore();

    public static void loadCommands(){
        PluginManager pluginManager = plugin.getProxy().getPluginManager();

        pluginManager.registerCommand(plugin, new AntibotCommand());
    }

    public static void unloadCommands(){
        PluginManager pluginManager = plugin.getProxy().getPluginManager();
        pluginManager.unregisterCommands(plugin);
    }
}
