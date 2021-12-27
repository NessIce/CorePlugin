package me.ness.core.bungee.listeners;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectedEvent implements Listener {

    @EventHandler
    public void onConnectedEvent(ServerConnectedEvent e){
        TextComponent header = new TextComponent();
        header.addExtra("\n§6§lMINEVIL NETWORK\n");
        header.addExtra("§fwww.minevil.net\n");

        TextComponent footer = new TextComponent();
        footer.addExtra("\n");
        footer.addExtra("§eDiscord: §fwww.minevil.net/discord\n");
        footer.addExtra("§eTwitter: §fwww.minevil.net/twitter\n");
        footer.addExtra("§eLoja: §floja.minevil.net\n");
        footer.addExtra("\n");
        footer.addExtra("§e          Venha redefinir seu conceito de diversão! acesse: §bloja.minevil.net          §e\n");
        footer.addExtra("\n");

        e.getPlayer().setTabHeader(header, footer);
    }
}
