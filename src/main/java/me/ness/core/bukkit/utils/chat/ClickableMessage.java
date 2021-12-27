package me.ness.core.bukkit.utils.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ClickableMessage {

    private final TextComponent message;

    public ClickableMessage(){
        this.message = new TextComponent(TextComponent.fromLegacyText(""));
    }

    public ClickableMessage(String message){
        this.message = new TextComponent(TextComponent.fromLegacyText(message));
    }

    public void addExtra(String text, ClickEvent clickEvent, HoverEvent hoverEvent){
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        textComponent.setClickEvent(clickEvent);
        textComponent.setHoverEvent(hoverEvent);
        this.message.addExtra(textComponent);
    }

    public void addExtra(String text, ClickEvent clickEvent){
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        textComponent.setClickEvent(clickEvent);
        this.message.addExtra(textComponent);
    }

    public void addExtra(String text, HoverEvent hoverEvent){
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        textComponent.setHoverEvent(hoverEvent);
        this.message.addExtra(textComponent);
    }

    public void addExtra(String text){
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        this.message.addExtra(textComponent);
    }

    public void send(Player player){
        player.spigot().sendMessage(message);
    }

    public void sendAll(){
        Bukkit.getOnlinePlayers().forEach(p->p.spigot().sendMessage(message));
    }

    public void sendList(List<Player> players){
        players.forEach(p->p.spigot().sendMessage(message));
    }
}
