package me.ness.core.bukkit.listeners;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.managers.AccountManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();

        MineAccount account = AccountManager.getAccount(player.getUniqueId());
        account.getData().setLastConnection(new Date());
    }
}
