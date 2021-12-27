package me.ness.core.bungee.antibot.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Locale;

public class BungeeUtil {

    public static String getLanguage(ProxiedPlayer proxiedPlayer, String defaultString) {
        final Locale locale = proxiedPlayer.getLocale();

        if (locale == null) {
            return defaultString;
        } else {
            return locale.toLanguageTag();
        }
    }
}