package me.ness.core.bungee.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Collection;

public class SwithEvent implements Listener {

    @EventHandler
    public void registerEvent(ServerSwitchEvent e){
        ProxiedPlayer player = e.getPlayer();

        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();

        if (networkPlayers == null || networkPlayers.isEmpty() ) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("swithServerEvent");
        out.writeUTF(player.getName());
        out.writeUTF(player.getServer().getInfo().getAddress().getHostName());

        player.getServer().getInfo().sendData( "minevil", out.toByteArray());
    }
}
