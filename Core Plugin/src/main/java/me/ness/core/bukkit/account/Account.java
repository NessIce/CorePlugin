package me.ness.core.bukkit.account;

import me.ness.core.bukkit.account.friends.MineFriend;
import me.ness.core.bukkit.account.party.MineParty;
import me.ness.core.bukkit.account.scoreboard.MineBoard;
import me.ness.core.bukkit.messages.LangMessage;

import java.util.UUID;

public interface Account {

    UUID getUuid();

    String getName();

    LangMessage.Language getLanguage();

    AccountData getData();

    MineFriend getMineFriend();

    MineParty getMineParty();

    MineBoard getMineBoard();

    boolean isOnline();

    Account clone();
}
