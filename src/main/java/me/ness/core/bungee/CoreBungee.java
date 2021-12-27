package me.ness.core.bungee;

import me.ness.core.bungee.antibot.AntiBot;
import me.ness.core.bungee.managers.CommandBungeeManager;
import me.ness.core.bungee.managers.EventBungeeManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungee extends Plugin {

    private static CoreBungee core;

    private AntiBot antiBot;

    @Override
    public void onLoad() {
        core = this;
        antiBot = new AntiBot();
    }

    @Override
    public void onEnable() {
        antiBot.enable();

        EventBungeeManager.loadEvents();
        CommandBungeeManager.loadCommands();

        getProxy().registerChannel("minevil");
        sendMessage("§6[Core] §aHabilitado com sucesso!");
    }

    @Override
    public void onDisable() {
        antiBot.disable();

        EventBungeeManager.unloadEvents();
        CommandBungeeManager.unloadCommands();

        getProxy().unregisterChannel("minevil");

        sendMessage("§6[Core] §cDesabilitado com sucesso!");
    }


    public static CoreBungee getCore() {
        return core;
    }

    public static void sendMessage(String message){
        getCore().getProxy().getConsole().sendMessage(TextComponent.fromLegacyText(message));
    }

    /**
     * Adquirindo instancias da classe principal!
     */

    public AntiBot getAntiBot() {
        return antiBot;
    }
}
