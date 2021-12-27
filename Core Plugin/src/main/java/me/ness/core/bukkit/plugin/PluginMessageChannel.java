package me.ness.core.bukkit.plugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.ness.core.bukkit.CoreBukkit;
import me.ness.core.bukkit.events.ServerChangeEvent;
import me.ness.core.bukkit.utils.BungeeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginMessageChannel implements PluginMessageListener {

    public static void loadChannel(){
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(CoreBukkit.getCore(), "minevil", new PluginMessageChannel());
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(CoreBukkit.getCore(), "minevil");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase( "minevil")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase( "swithServerEvent")){
            String playerName = in.readUTF();
            String adress = in.readUTF();

            ServerChangeEvent serverChangeEvent = new ServerChangeEvent(Bukkit.getPlayer(playerName),adress);
            Bukkit.getPluginManager().callEvent(serverChangeEvent);
            return;
        }

        if(subChannel.equalsIgnoreCase("PlayerCount")){
            String server = in.readUTF();
            int playercount = in.readInt();

            BungeeUtils.SERVER_COUNTS.put(server, playercount);
        }
    }
}
