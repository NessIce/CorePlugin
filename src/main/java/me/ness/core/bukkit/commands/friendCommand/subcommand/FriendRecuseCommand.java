package me.ness.core.bukkit.commands.friendCommand.subcommand;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.friends.FriendStatus;
import me.ness.core.bukkit.managers.AccountManager;
import me.ness.core.bukkit.utils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendRecuseCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length > 0){
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

            accountPlayer.getMineFriend().removeFriend(target.getUniqueId().toString());
            player.sendMessage("§cVocê negou o pedido de amizade do jogador §7"+target.getName()+"§c!");

            accountTarget.getMineFriend().addFriend(player.getUniqueId().toString(), FriendStatus.BLOCK);
            target.sendMessage("§cO jogador §7"+player.getName()+" §cnegou o seu pedido de amizade!");
            return;
        }
        player.sendMessage("§cUtilize /amigo negar <Jogador>");
    }
}
