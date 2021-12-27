package me.ness.core.bukkit.commands.friendCommand;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.friends.FriendStatus;
import me.ness.core.bukkit.commands.friendCommand.subcommand.*;
import me.ness.core.bukkit.managers.AccountManager;
import me.ness.core.bukkit.managers.CommandManager;
import me.ness.core.bukkit.utils.chat.ClickableMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class FriendCommand extends CommandManager {

    private final FriendListCommand friendList;
    private final FriendDeleteCommand friendDelete;
    private final FriendAcceptCommand friendAccept;
    private final FriendRecuseCommand friendRecuse;
    private final FriendIgnoreCommand friendIgnore;
    private final FriendCancelCommand friendCancel;
    private final FriendDeleteAllCommand friendDeleteAll;

    public FriendCommand(){
        super("friend", "amigo");
        this.friendList = new FriendListCommand();
        this.friendDelete = new FriendDeleteCommand();
        this.friendAccept = new FriendAcceptCommand();
        this.friendRecuse = new FriendRecuseCommand();
        this.friendIgnore = new FriendIgnoreCommand();
        this.friendCancel = new FriendCancelCommand();
        this.friendDeleteAll = new FriendDeleteAllCommand();
    }

    /**
     * Comandos do /amigo
     *
     * /amigo <Jogador>
     * /amigo listar
     * /amigo excluir <Jogador>
     * /amigo aceitar <Jogador>
     * /amigo negar <Jogador>
     * /amigo ignorar <Jogador>
     * /amigo cancelar <Jogador>
     * /amigo excluirtodos
     *
     */

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return;
        }

        Player player = (Player)sender;

        if(args.length>0){
            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

            switch (args[0].toLowerCase()){

                case "listar" : case "list":{
                    this.friendList.execute(sender, newArgs);
                    return;
                }
                case "excluir" : case "delete":{
                    this.friendDelete.execute(sender, newArgs);
                    return;
                }
                case "aceitar" : case "accept":{
                    this.friendAccept.execute(sender, newArgs);
                    return;
                }
                case "negar" : case "recuse":{
                    this.friendRecuse.execute(sender, newArgs);
                    return;
                }
                case "ignorar" : case "ignore":{
                    this.friendIgnore.execute(sender, newArgs);
                    return;
                }
                case "cancelar" : case "cancel":{
                    this.friendCancel.execute(sender, newArgs);
                    return;
                }
                case "excluirtodos" : case "deleteall":{
                    this.friendDeleteAll.execute(sender, newArgs);
                    return;
                }
                default:{
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target==null){
                        player.sendMessage("§cEste jogador não está online!");
                        return;
                    }
                    if(target==player){
                        player.sendMessage("§cVocê não pode enviar solicitação para si mesmo!");
                        return;
                    }

                    /*
                     * Solicitação de amizade enviada pelo player
                     */
                    MineAccount accountPlayer = AccountManager.getAccount(player.getUniqueId());

                    if(accountPlayer.getMineFriend().hasFriend(target.getUniqueId().toString())){
                        FriendStatus friendStatus = accountPlayer.getMineFriend().getFriend(target.getUniqueId().toString());
                        switch (friendStatus){
                            case PENDING_SENDING:{
                                player.sendMessage("§cVocê já enviou uma solicitação de amizade para este jogador!");
                                return;
                            }
                            case PENDING_RECIVING:{
                                player.sendMessage("§cVocê tem um pedido de amizade deste jogador, utilize /amigo aceitar "+target.getName()+"!");
                                return;
                            }
                            case ACTIVE:{
                                player.sendMessage("§cVocê já é amigo deste jogador!");
                                return;
                            }
                            case BLOCK:{
                                player.sendMessage("§cVocê está proibido de enviar solicitações de amizade para este jogador!");
                                return;
                            }
                        }
                    }

                    accountPlayer.getMineFriend().addFriend(target.getUniqueId().toString(), FriendStatus.PENDING_SENDING);

                    player.sendMessage("");
                    player.sendMessage("§eSolicitação de amizade enviada para §7"+target.getName()+"§e.");
                    player.sendMessage("");


                    /*
                     * Solicitação de amizade recebida pelo target
                     */
                    MineAccount accountTarget = AccountManager.getAccount(target.getUniqueId());
                    accountTarget.getMineFriend().addFriend(player.getUniqueId().toString(), FriendStatus.PENDING_RECIVING);

                    target.sendMessage("");
                    target.sendMessage("§aVocê recebeu uma solicitação de amizade de §7"+player.getName());

                    ClickableMessage clickableMessage = new ClickableMessage("§aClique ");
                    clickableMessage.addExtra(
                            "§a§lAQUI",
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo aceitar "+player.getName()),
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(
                                    "§aClique aqui para aceitar.\n" +
                                    "§7/amigo aceitar "+player.getName())));
                    clickableMessage.addExtra(" §apara aceitar e ");
                    clickableMessage.addExtra(
                            "§c§lAQUI",
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo negar "+player.getName()),
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(
                                    "§cClique aqui para aceitar.\n" +
                                    "§7/amigo negar "+player.getName())));
                    clickableMessage.addExtra(" §apara negar.");
                    clickableMessage.send(target);

                    target.sendMessage("");
                    return;
                }
            }
        }
        sender.sendMessage("");
        sender.sendMessage("§a/amigo <Jogador> - §7enviar um pedido de amizade.");
        sender.sendMessage("§a/amigo listar - §7listar todos os amigos.");
        sender.sendMessage("§a/amigo excluir <Jogador> - §7excluir um amigo da lista.");
        sender.sendMessage("§a/amigo aceitar <Jogador> - §7aceitar um pedido de amizade.");
        sender.sendMessage("§a/amigo negar <Jogador> - §7recusar um pedido de amizade.");
        sender.sendMessage("§a/amigo ignorar <Jogador> - §7ignorar um jogador indesejado.");
        sender.sendMessage("§a/amigo excluirtodos - §7excluir todos os amigos da lista.");
        sender.sendMessage("");
    }
}
