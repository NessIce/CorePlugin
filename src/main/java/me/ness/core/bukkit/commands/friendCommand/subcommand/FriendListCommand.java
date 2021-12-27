package me.ness.core.bukkit.commands.friendCommand.subcommand;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.managers.AccountManager;
import me.ness.core.bukkit.utils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FriendListCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player)sender;

        MineAccount accountPlayer = AccountManager.getAccount(player.getUniqueId());

        if(accountPlayer.getMineFriend().getFriends().isEmpty()){
            player.sendMessage("§cVocê não possui nenhum amigo na lista!");
            return;
        }

        player.sendMessage("");
        player.sendMessage(" §eTodos seus amigos §71/1");
        player.sendMessage("");
        accountPlayer.getMineFriend().getFriends().keySet().forEach((friend)->{
            String online = Bukkit.getPlayer(UUID.fromString(friend))!=null?"§aOnline":"§cOffline";
            player.sendMessage("§7 * "+friend+" ["+online+"]");
        });
        player.sendMessage("");
    }
}
