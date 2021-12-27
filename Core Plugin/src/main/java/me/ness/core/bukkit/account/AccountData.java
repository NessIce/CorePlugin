package me.ness.core.bukkit.account;

import lombok.Getter;
import lombok.Setter;
import me.ness.core.bukkit.account.clan.MineClan;

import java.util.Date;

@Getter
@Setter
public class AccountData {

    private String uniqueID;
    private String name;

    private long coins;

    private MineClan mineClan;

    private Date lastConnection, firstConnection;

    private boolean fly, visible, gore, tell, friend, party, clan;

    public AccountData(String uniqueID) {
        this.uniqueID = uniqueID;
        this.coins = 0;
        this.mineClan = null;
        this.lastConnection = new Date();
        this.firstConnection = new Date();
        this.fly = false;
        this.visible = true;
        this.gore = true;
        this.tell = true;
        this.friend = true;
        this.party = true;
        this.clan = true;
    }
}
