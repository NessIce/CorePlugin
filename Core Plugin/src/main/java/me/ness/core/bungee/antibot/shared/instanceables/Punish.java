package me.ness.core.bungee.antibot.shared.instanceables;

import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.NotificationsModule;
import me.ness.core.bungee.antibot.module.PlaceholderModule;
import me.ness.core.bungee.antibot.shared.interfaces.IPunishModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

import java.util.Collection;

public class Punish {

	public Punish(ModuleManager moduleManager, String locale, IPunishModule punishModule, Connection connection, Event event) {
		PlaceholderModule placeholderModule = moduleManager.getPlaceholderModule();
		NotificationsModule notificationsModule = moduleManager.getNotificationsModule();
		Collection<String> punishCommands = punishModule.getPunishCommands();
		String punishModuleName = punishModule.getName();
		String checkName = punishModuleName.substring(0, 1).toUpperCase() + punishModuleName.substring(1);
		String address = connection.getAddress().getHostString();

		moduleManager.getCounterModule().addTotalBlocked();
		notificationsModule.notify(locale, address, checkName);

		if (event instanceof ProxyPingEvent) {
			ProxyPingEvent proxyPingEvent = (ProxyPingEvent) event;

			proxyPingEvent.setResponse(null);
		} else if (!punishCommands.isEmpty()) {
			String disconnectString = "disconnect ";

			for (String command : punishCommands) {
				command = placeholderModule.setPlaceholders(moduleManager, command, locale, address, checkName);

				if (command.startsWith(disconnectString)) {
					BaseComponent[] textComponent = TextComponent
							.fromLegacyText(command.replace(disconnectString, ""));

					if (event instanceof Cancellable) {
						if (event instanceof PreLoginEvent) {
							PreLoginEvent preLoginEvent = (PreLoginEvent) event;

							preLoginEvent.setCancelReason(textComponent);
						} else {
							connection.disconnect(textComponent);
						}

						((Cancellable) event).setCancelled(true);
					} else {
						connection.disconnect(textComponent);
					}
				} else {
					ProxyServer proxyServer = ProxyServer.getInstance();

					proxyServer.getPluginManager().dispatchCommand(proxyServer.getConsole(), command);
				}
			}
		}
	}
}
