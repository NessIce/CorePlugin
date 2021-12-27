package me.ness.core.bungee.listeners;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PingEvent implements Listener {

    private static final List<String> motds;

    static {
        motds = new ArrayList<>();

        motds.add("§6§lMINEVIL NETWORK  §7【1.8.x - 1.18.x】\n" +
                "§e➥ §fVenha redefinir seu conceito de diversão!");

        motds.add("§6§lMINEVIL NETWORK  §7【1.8.x - 1.18.x】\n" +
                "§e➥ §fAcesse a nossa loja: §bloja.minevil.net!");

        motds.add("§6§lMINEVIL NETWORK  §7【1.8.x - 1.18.x】\n" +
                "§e➥ §fSkyWars, FlagWars, BedWars §ee muito mais!");
    }

    @EventHandler
    public void onPingEvent(ProxyPingEvent e){
        e.getResponse().setDescription(motds.get(new Random().nextInt(motds.size())));
    }
}
