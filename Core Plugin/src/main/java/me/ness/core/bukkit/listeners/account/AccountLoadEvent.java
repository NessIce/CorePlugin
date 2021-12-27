package me.ness.core.bukkit.listeners.account;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.managers.AccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AccountLoadEvent implements Listener {

    @EventHandler
    public void onLoginEvent(AsyncPlayerPreLoginEvent e){
        MineAccount account = AccountManager.registerAccount(e.getUniqueId(), e.getName());

        if(!account.loadData()){
            e.setKickMessage("MINEVIL NETWORK\n\n"
                    + "Uma falha aconteceu ao tentar carregar sua\n"
                    + "conta, entre em contato com o suporte.");
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }
}
