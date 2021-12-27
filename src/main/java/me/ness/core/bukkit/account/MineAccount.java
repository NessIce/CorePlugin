package me.ness.core.bukkit.account;

import me.ness.core.bukkit.account.friends.MineFriend;
import me.ness.core.bukkit.account.party.MineParty;
import me.ness.core.bukkit.account.scoreboard.MineBoard;
import me.ness.core.bukkit.messages.LangMessage;
import me.ness.core.bukkit.mongo.storages.AccountStorage;
import me.ness.core.bukkit.mongo.storages.FriendStorage;
import org.bukkit.Bukkit;

import java.util.UUID;

public class MineAccount implements Account{

    private final UUID uuid;
    private final String name;

    private LangMessage.Language language;

    private AccountData data;
    private MineFriend mineFriend;

    private final MineParty mineParty;
    private final MineBoard mineBoard;

    public MineAccount(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        this.language = LangMessage.Language.PT_BR;

        this.mineParty = new MineParty(this);
        this.mineBoard = new MineBoard(this);
    }

    public MineAccount(MineAccount account) {
        this.uuid = account.getUuid();
        this.name = account.getName();

        this.language = account.getLanguage();

        this.data = account.getData();
        this.mineFriend = account.getMineFriend();

        this.mineParty = account.getMineParty();
        this.mineBoard = account.getMineBoard();
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public AccountData getData() {
        return this.data;
    }

    @Override
    public LangMessage.Language getLanguage() {
        return this.language;
    }

    @Override
    public MineFriend getMineFriend() {
        return this.mineFriend;
    }

    @Override
    public MineParty getMineParty() {
        return this.mineParty;
    }

    @Override
    public MineBoard getMineBoard() {
        return this.mineBoard;
    }

    @Override
    public boolean isOnline() {
        return Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline();
    }

    @Override
    public Account clone() {
        return new MineAccount(this);
    }

    public MineLiveAccount getMineLiveAccount(){
        if(isOnline()){
            return new MineLiveAccount(this);
        }
        return null;
    }

    public boolean loadData(){
        this.data = AccountStorage.loadAccount(this.uuid.toString());
        this.mineFriend = FriendStorage.loadMineFriend(this.uuid.toString());

        return this.data != null && this.mineFriend != null;
    }

    public void saveData(){
        AccountStorage.saveAccount(this.uuid.toString(), this.data);
        FriendStorage.saveMineFriend(this.uuid.toString(), this.mineFriend);
    }
}
