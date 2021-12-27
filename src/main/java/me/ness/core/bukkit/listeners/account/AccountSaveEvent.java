package me.ness.core.bukkit.listeners.account;

import me.ness.core.bukkit.events.ServerChangeEvent;
import me.ness.core.bukkit.managers.AccountManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class AccountSaveEvent implements Listener {

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e){
        Player player = e.getPlayer();
        AccountManager.getAccount(player.getUniqueId()).saveData();

    }

    @EventHandler
    public void onServerChangeEvent(ServerChangeEvent e){
        Player player = e.getPlayer();

        AccountManager.getAccount(player.getUniqueId()).saveData();

    }
}

