package me.ness.core.bukkit.managers;

import me.ness.core.bukkit.CoreBukkit;
import me.ness.core.bukkit.listeners.ClickEvent;
import me.ness.core.bukkit.listeners.JoinEvent;
import me.ness.core.bukkit.listeners.SpawnEvent;
import me.ness.core.bukkit.listeners.WeatherEvent;
import me.ness.core.bukkit.listeners.account.AccountLoadEvent;
import me.ness.core.bukkit.listeners.account.AccountSaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;


public class EventsManager{

    public static void loadEvents() {
        CoreBukkit coreBukkit = CoreBukkit.getCore();
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new AccountLoadEvent(), coreBukkit);
        pluginManager.registerEvents(new AccountSaveEvent(), coreBukkit);
        pluginManager.registerEvents(new ClickEvent(), coreBukkit);
        pluginManager.registerEvents(new JoinEvent(), coreBukkit);
        pluginManager.registerEvents(new WeatherEvent(), coreBukkit);
        pluginManager.registerEvents(new SpawnEvent(), coreBukkit);
    }
}