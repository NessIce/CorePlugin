package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;

public class RateLimitModule extends PunishableModule {

	private final ModuleManager moduleManager;
	private int maxOnline = 3;
	private int throttle = 800;

	public RateLimitModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "ratelimit";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(8, 3, 2), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_ratelimit%");

		maxOnline = 3;
		throttle = 1000;
	}

	public boolean check(Connection connection) {
		PlayerModule playerModule = moduleManager.getPlayerModule();
		BotPlayer botPlayer = playerModule.get(connection.getAddress().getHostString());
		Incoming incoming = botPlayer.getIncoming();
		long lastConnection = botPlayer.getLastConnection();
		boolean isThrottle = (incoming.getCPS() != 0 || incoming.getPPS() < 0) && System.currentTimeMillis() - lastConnection < throttle;

		return thresholds.meet(incoming) || isThrottle
				|| botPlayer.getAccounts().size() > maxOnline;
	}
}
