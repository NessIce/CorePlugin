package me.ness.core.bukkit.utils.chat;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ColorMessage {
    public String translate(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
