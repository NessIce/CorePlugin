package me.ness.core.bukkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.ness.core.bukkit.CoreBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BungeeUtils {

    public static Map<String, Integer> SERVER_COUNTS = new HashMap<>();

    public static int getCountServer(String server){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        Player player = new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(0);

        player.sendPluginMessage(CoreBukkit.getCore(), "minevil", out.toByteArray());

        if(!SERVER_COUNTS.containsKey(server)){
            SERVER_COUNTS.put(server, 0);
        }

        return SERVER_COUNTS.get(server);
    }
}
