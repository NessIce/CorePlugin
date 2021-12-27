package me.ness.core.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerChangeEvent extends Event {

    private Player player;
    private String adress;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public ServerChangeEvent(Player player, String adress){
        this.player = player;
        this.adress = adress;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
