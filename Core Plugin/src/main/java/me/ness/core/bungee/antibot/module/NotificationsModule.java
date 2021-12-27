package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationsModule implements IModule {

	private final ModuleManager moduleManager;
	private final Logger logger;
	private final Collection<ProxiedPlayer> notificationPlayers = new HashSet<>();
	private boolean enabled = true, console = true;
	private long lastNotificationTime = System.currentTimeMillis();

	public NotificationsModule(ModuleManager moduleManager, Logger logger) {
		this.moduleManager = moduleManager;
		this.logger = logger;
	}

	@Override
	public String getName() {
		return "notifications";
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		enabled = true;
		console = true;
	}

	public void notify(String locale, String address, String checkName) {
		if (enabled) {
			long currentTime = System.currentTimeMillis();

			if (currentTime > lastNotificationTime + 100) {
				try {
					PlaceholderModule placeholderModule = moduleManager.getPlaceholderModule();
					String notification = placeholderModule.setPlaceholders(moduleManager,
							"%notification_message%", locale, address, checkName);
					BaseComponent[] notificationTextComponent = TextComponent.fromLegacyText(notification);
					ChatMessageType chatMessageType = ChatMessageType.ACTION_BAR;

					if (console) {
						logger.log(Level.INFO, notification);
					}

					for (ProxiedPlayer player : notificationPlayers) {
						player.sendMessage(chatMessageType, notificationTextComponent);
					}

					lastNotificationTime = currentTime;
				} catch (ConcurrentModificationException e) {
					logger.warning("AntiBot catched a CME exception! (NotificationsModule.java)");
				}
			}
		}
	}

	public void setNotifications(ProxiedPlayer player, boolean bool) {
		if (bool) {
			if (!notificationPlayers.contains(player)) {
				notificationPlayers.add(player);
			}
		} else if (notificationPlayers.contains(player)) {
			notificationPlayers.remove(player);
		}
	}

	public boolean hasNotifications(ProxiedPlayer player) {
		return notificationPlayers.contains(player);
	}

	public boolean isEnabled() {
		return enabled;
	}
}