package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.CounterModule;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.PlayerModule;
import me.ness.core.bungee.antibot.module.SettingsModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Punish;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {

	private final ModuleManager moduleManager;
	private final SettingsModule settingsModule;

	public ServerSwitchListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
		this.settingsModule = moduleManager.getSettingsModule();
		moduleManager.getBlacklistModule();
		moduleManager.getWhitelistModule();
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onServerSwitch(ServerSwitchEvent event) {
		PlayerModule playerModule = moduleManager.getPlayerModule();
		ProxiedPlayer proxiedPlayer = event.getPlayer();
		String ip = proxiedPlayer.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);

		if (settingsModule.isSwitching()) {
			CounterModule counterModule = moduleManager.getCounterModule();
			boolean switched = botPlayer.getSwitchs() > 0;

			if (switched && settingsModule.meet(counterModule.getCurrent(), counterModule.getLast())
					&& !botPlayer.isSettings()) {
				new Punish(moduleManager, moduleManager.getDefaultLanguage(), settingsModule, proxiedPlayer, event);
			}
		}

		botPlayer.addSwitch();
	}
}
