package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.NotificationsModule;
import me.ness.core.bungee.antibot.module.PlayerModule;
import me.ness.core.bungee.antibot.module.SettingsModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

	private final ModuleManager moduleManager;

	public PostLoginListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onPostLogin(PostLoginEvent event) {
		NotificationsModule notificationsModule = moduleManager.getNotificationsModule();
		PlayerModule playerModule = moduleManager.getPlayerModule();
		SettingsModule settingsModule = moduleManager.getSettingsModule();
		ProxiedPlayer player = event.getPlayer();
		String ip = player.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);

		botPlayer.getIncoming().addJPS();
		botPlayer.addAccount(player.getName());
		moduleManager.getCounterModule().getCurrent().addJPS();
		settingsModule.addPending(botPlayer);
		playerModule.setOnline(botPlayer);

		if (player.hasPermission("antibot.notifications")) {
			notificationsModule.setNotifications(player, true);
		}
	}
}