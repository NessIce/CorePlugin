package me.ness.core.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnEvent implements Listener{

    @EventHandler
    public void onSpawnEvent(CreatureSpawnEvent e){
        if(!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)){
            e.setCancelled(true);
        }
    }
}
