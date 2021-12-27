package me.ness.core.bukkit;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.managers.AccountManager;
import me.ness.core.bukkit.managers.CommandManager;
import me.ness.core.bukkit.managers.EventsManager;
import me.ness.core.bukkit.mongo.MongoStorage;
import me.ness.core.bukkit.plugin.PluginMessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreBukkit extends JavaPlugin {

    private static CoreBukkit plugin;
    //test2
    @Override
    public void onEnable() {
        plugin = this;

        if(!MongoStorage.connect()){
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PluginMessageChannel.loadChannel();

        EventsManager.loadEvents();
        CommandManager.loadCommands();

        Bukkit.getConsoleSender().sendMessage("§6[Core] §aHabilitado com sucesso!");
    }

    @Override
    public void onDisable() {
        AccountManager.getAccounts().values().forEach(MineAccount::saveData);
        Bukkit.getConsoleSender().sendMessage("§6[Core] §cDesabilitado com sucesso!");
    }

    public static CoreBukkit getCore(){
        return plugin;
    }
}
