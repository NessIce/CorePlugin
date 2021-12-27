package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.FastChatModule;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.PasswordModule;
import me.ness.core.bungee.antibot.module.WhitelistModule;
import me.ness.core.bungee.antibot.shared.instanceables.Punish;
import me.ness.core.bungee.antibot.utils.BungeeUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Locale;

public class ChatListener implements Listener {

	private final ModuleManager moduleManager;

	public ChatListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onChat(ChatEvent event) {
		Connection sender = event.getSender();

		if (!event.isCancelled() && sender instanceof ProxiedPlayer) {
			WhitelistModule whitelistModule = moduleManager.getWhitelistModule();
			ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

			if (!whitelistModule.check(proxiedPlayer)) {
				PasswordModule registerModule = moduleManager.getRegisterModule();
				FastChatModule fastChatModule = moduleManager.getFastChatModule();
				String defaultLanguage = moduleManager.getDefaultLanguage();
				String message = event.getMessage().trim();
				Locale locale = proxiedPlayer.getLocale();
				Incoming currentIncoming = moduleManager.getCounterModule().getCurrent();
				Incoming lastIncoming = moduleManager.getCounterModule().getLast();

				if (locale == null) {
					if (fastChatModule.meet(currentIncoming, lastIncoming)
							&& fastChatModule.check(proxiedPlayer)) {
						new Punish(moduleManager, defaultLanguage, fastChatModule, proxiedPlayer, event);

						moduleManager.getBlacklistModule().setBlacklisted(proxiedPlayer.getAddress().getHostString(),
								true);
					}
				} else {
					String lang = BungeeUtil.getLanguage(proxiedPlayer, defaultLanguage);

					if (fastChatModule.meet(currentIncoming, lastIncoming)
							&& fastChatModule.check(proxiedPlayer)) {
						new Punish(moduleManager, lang, fastChatModule, proxiedPlayer, event);
					} else if (registerModule.meet(currentIncoming, lastIncoming)
							&& registerModule.check(proxiedPlayer, message)) {
						new Punish(moduleManager, lang, registerModule, proxiedPlayer, event);
					} else {
						registerModule.setLastValues(proxiedPlayer.getAddress().getHostString(), message);
					}
				}
			}
		}
	}
}