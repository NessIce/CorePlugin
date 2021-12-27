package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.PendingConnection;

public class AccountsModule extends PunishableModule {

	private final ModuleManager moduleManager;
	private int limit = 2;

	public AccountsModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "accounts";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 6, 1), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_accounts%");

		limit = 2;
	}

	public boolean check(Connection connection) {
		if (connection instanceof PendingConnection) {
			PlayerModule playerModule = moduleManager.getPlayerModule();
			BotPlayer botPlayer = playerModule.get(connection.getAddress().getHostString());

			return botPlayer.getTotalAccounts() >= limit;
		}
		return false;
	}
}
