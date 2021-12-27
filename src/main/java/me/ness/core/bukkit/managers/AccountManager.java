package me.ness.core.bukkit.managers;

import lombok.experimental.UtilityClass;
import me.ness.core.bukkit.account.MineAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class AccountManager {

    private final Map<UUID, MineAccount> ACCOUNTS = new HashMap<>();

    public MineAccount registerAccount(UUID uuid, String name){
        MineAccount mineAccount = new MineAccount(uuid, name);
        if (!ACCOUNTS.containsKey(uuid)) {
            ACCOUNTS.put(uuid, mineAccount);
        }
        return mineAccount;
    }

    public MineAccount getAccount(UUID uuid){
        return ACCOUNTS.get(uuid);
    }

    public void removeAccount(UUID uuid){
        ACCOUNTS.remove(uuid);
    }

    public Map<UUID, MineAccount> getAccounts(){
        return ACCOUNTS;
    }
}
