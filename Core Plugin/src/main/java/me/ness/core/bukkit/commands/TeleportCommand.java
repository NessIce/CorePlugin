package me.ness.core.bukkit.commands;

import me.ness.core.bukkit.managers.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class TeleportCommand extends CommandManager {

    public TeleportCommand() {
        super("teleport", "tp");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        Bukkit.getConsoleSender().sendMessage("Testando o comando!");
    }


}
