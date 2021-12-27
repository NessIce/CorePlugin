package me.ness.core.bukkit.commands.friendCommand.subcommand;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.friends.FriendStatus;
import me.ness.core.bukkit.managers.AccountManager;
import me.ness.core.bukkit.utils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendAcceptCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args){
        Player player = (Player) sender;
        if(args.length>0){
            MineAccount accountPlayer = AccountManager.getAccount(player.getUniqueId());

            Player target = Bukkit.getPlayer(args[0]);
            if(target==null){
                player.sendMessage("§cEste jogador não está online!");
                return;
            }
            MineAccount accountTarget = AccountManager.getAccount(target.getUniqueId());

            if(!accountPlayer.getMineFriend().hasFriend(target.getUniqueId().toString())){
                player.sendMessage("§cVocê não recebeu uma relação de amizade com este jogador");
                return;
            }

            //Status de amizade do target para o player que executou o comando!
            FriendStatus playerStatus = accountPlayer.getMineFriend().getFriend(target.getUniqueId().toString());

            if(playerStatus!=FriendStatus.PENDING_RECIVING){
                player.sendMessage("§cVocê não tem uma solicitação de amizade recebida deste jogador!");
                return;
            }

            accountPlayer.getMineFriend().addFriend(target.getUniqueId().toString(), FriendStatus.ACTIVE);
            player.sendMessage("");
            player.sendMessage(" §eAgora o jogador §7"+target.getName()+"§e é seu amigo!");
            player.sendMessage("");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);

            accountTarget.getMineFriend().addFriend(player.getUniqueId().toString(), FriendStatus.ACTIVE);
            target.sendMessage("");
            target.sendMessage(" §eAgora o jogador §7"+player.getName()+"§e é seu amigo!");
            target.sendMessage("");
            target.playSound(target.getLocation(), Sound.LEVEL_UP, 1,1);
            return;
        }
        player.sendMessage("§cUtilize /amigo aceitar <Jogador>");
    }
}
