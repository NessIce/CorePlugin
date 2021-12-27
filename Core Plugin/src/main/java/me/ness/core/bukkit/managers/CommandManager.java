package me.ness.core.bukkit.managers;

import me.ness.core.bukkit.commands.GameModeCommand;
import me.ness.core.bukkit.commands.TeleportCommand;
import me.ness.core.bukkit.commands.friendCommand.FriendCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;

public abstract class CommandManager extends BukkitCommand {

    public CommandManager(String cmd, String... aliases){
        super(cmd);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register(this.getName(), "ness", this);
        } catch (ReflectiveOperationException ex) {
            Bukkit.getConsoleSender().sendMessage("§6[Core] §cNão pode registrar o comando: "+ ex);
        }
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        perform(sender, s, args);
        return false;
    }

    public abstract void perform(CommandSender sender, String label, String[] args);

    public static void loadCommands(){
        new FriendCommand();
        new GameModeCommand();
        new TeleportCommand();
    }
}
