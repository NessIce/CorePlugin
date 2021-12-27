package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;

public class FastChatModule extends PunishableModule {

	private final ModuleManager moduleManager;
	private int time = 1000;

	public FastChatModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "fastchat";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 3, 2), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_fastchat%");
		time = 1000;
	}

	public boolean check(Connection connection) {
		PlayerModule playerModule = moduleManager.getPlayerModule();
		BotPlayer botPlayer = playerModule.get(connection.getAddress().getHostString());

		return (botPlayer == null || System.currentTimeMillis() - botPlayer.getLastConnection() < time
				|| !botPlayer.isSettings());
	}
}
