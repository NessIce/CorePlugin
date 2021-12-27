package me.ness.core.bungee.managers;

import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.listeners.ConnectedEvent;
import me.ness.core.bungee.listeners.PingEvent;
import me.ness.core.bungee.listeners.SwithEvent;
import me.ness.core.bungee.listeners.antibot.*;
import net.md_5.bungee.api.plugin.PluginManager;

public class EventBungeeManager {

    private static final CoreBungee plugin = CoreBungee.getCore();

    public static void loadEvents(){
        PluginManager pluginManager = plugin.getProxy().getPluginManager();
        ModuleManager moduleManager = plugin.getAntiBot().getModuleManager();

        pluginManager.unregisterListeners(plugin);
        pluginManager.registerListener(plugin, new ChatListener(moduleManager));
        pluginManager.registerListener(plugin, new PlayerDisconnectListener(moduleManager));
        pluginManager.registerListener(plugin, new PlayerHandshakeListener(moduleManager));
        pluginManager.registerListener(plugin, new PostLoginListener(moduleManager));
        pluginManager.registerListener(plugin, new PreLoginListener(moduleManager));
        pluginManager.registerListener(plugin, new ProxyPingListener(moduleManager));
        pluginManager.registerListener(plugin, new ServerSwitchListener(moduleManager));
        pluginManager.registerListener(plugin, new SettingsChangedListener(moduleManager));

        pluginManager.registerListener(plugin, new SwithEvent());
        pluginManager.registerListener(plugin, new ConnectedEvent());
        pluginManager.registerListener(plugin, new PingEvent());
    }

    public static void unloadEvents(){
        PluginManager pluginManager = plugin.getProxy().getPluginManager();;
        pluginManager.unregisterListeners(plugin);
    }
}
