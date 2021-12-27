package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.*;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

	private final ModuleManager moduleManager;

	public PlayerDisconnectListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		NotificationsModule notificationsModule = moduleManager.getNotificationsModule();
		PlayerModule playerModule = moduleManager.getPlayerModule();
		SettingsModule settingsModule = moduleManager.getSettingsModule();
		WhitelistModule whitelistModule = moduleManager.getWhitelistModule();
		ProxiedPlayer proxiedPlayer = event.getPlayer();
		String ip = proxiedPlayer.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);
		long currentTime = System.currentTimeMillis();

		if (proxiedPlayer.getPing() < 500 && (!whitelistModule.isRequireSwitch() || botPlayer.getSwitchs() > 1)
				&& currentTime - botPlayer.getLastConnection() >= whitelistModule.getTimeWhitelist()) {
			whitelistModule.setWhitelisted(ip, true);
		}

		botPlayer.removeAccount(proxiedPlayer.getName());
		botPlayer.resetSwitchs();
		notificationsModule.setNotifications(proxiedPlayer, false);
		settingsModule.removePending(botPlayer);

		if (botPlayer.getAccounts().isEmpty()) {
			botPlayer.setSettings(false);
			playerModule.setOffline(botPlayer);
		}
	}
}
