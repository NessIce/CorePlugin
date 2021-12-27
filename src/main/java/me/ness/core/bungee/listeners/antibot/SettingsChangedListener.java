package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.PlayerModule;
import me.ness.core.bungee.antibot.module.SettingsModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.SettingsChangedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SettingsChangedListener implements Listener {

	private final PlayerModule playerModule;
	private final SettingsModule settingsModule;

	public SettingsChangedListener(ModuleManager moduleManager) {
		this.playerModule = moduleManager.getPlayerModule();
		this.settingsModule = moduleManager.getSettingsModule();
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onSettingsChanged(SettingsChangedEvent event) {
		ProxiedPlayer proxiedPlayer = event.getPlayer();
		String ip = proxiedPlayer.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);

		botPlayer.setSettings(true);
		settingsModule.removePending(botPlayer);
	}
}
