package me.ness.core.bukkit.account;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MineLiveAccount extends MineAccount {

    private final Player player;

    public MineLiveAccount(MineAccount account) {
        super(account);
        this.player = Bukkit.getPlayer(account.getUuid());
    }

    public Player getPlayer() {
        return player;
    }
}
