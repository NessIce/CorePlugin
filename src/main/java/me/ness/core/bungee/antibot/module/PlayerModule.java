package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PlayerModule implements IModule {

    private Map<String, BotPlayer> players = new HashMap<>();
    private Collection<BotPlayer> offlinePlayers = new HashSet<>();

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public void reload(ConfigUtil configUtil) {
       //Não vai ser carregado
    }

    public BotPlayer get(String hostString) {
        BotPlayer botPlayer;

        if (players.containsKey(hostString)) {
            botPlayer = players.get(hostString);
        } else {
            botPlayer = new BotPlayer(hostString);

            players.put(hostString, botPlayer);
        }

        return botPlayer;
    }

    public void setOnline(BotPlayer botPlayer) {
        offlinePlayers.remove(botPlayer);
    }

    public void setOffline(BotPlayer botPlayer) {
        offlinePlayers.add(botPlayer);
    }

    public Collection<BotPlayer> getOfflinePlayers() {
        return offlinePlayers;
    }

    public void remove(BotPlayer botPlayer) {
        String hostAddress = botPlayer.getHostAddress();

        players.remove(hostAddress);
        offlinePlayers.remove(botPlayer);
    }

    public int getCacheTime() {
        int cacheTime = 30000;
        return cacheTime;
    }
}