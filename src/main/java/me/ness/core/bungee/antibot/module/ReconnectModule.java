package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.PendingConnection;

public class ReconnectModule extends PunishableModule {

	private final ModuleManager moduleManager;
	private int timesPing = 1;
	private int timesConnect = 3;
	private long throttle = 800;

	public ReconnectModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "reconnect";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 3, 0), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_reconnect%");

		timesPing = 1;
		timesConnect = 3;
		throttle = 1250;
	}

	public boolean check(Connection connection) {
		if (connection instanceof PendingConnection) {
			PlayerModule playerModule = moduleManager.getPlayerModule();
			BotPlayer botPlayer = playerModule.get(connection.getAddress().getHostString());
			String name = ((PendingConnection) connection).getName(), lastNickname = botPlayer.getLastNickname();
			int repings = botPlayer.getRepings(), reconnects = botPlayer.getReconnects() + 1;
			long currentTimeMillis = System.currentTimeMillis();

			if (!lastNickname.equals(name) || (timesPing > 0 && (currentTimeMillis - botPlayer.getLastPing() < 550))
					|| currentTimeMillis - botPlayer.getLastConnection() < throttle) {
				botPlayer.setReconnects(0);
				botPlayer.setRepings(0);
				botPlayer.setLastNickname(name);
			} else {
				return (reconnects < timesConnect || repings < timesPing);
			}
		}

		return true;
	}

	public int getTimesConnect() {
		return timesConnect;
	}
}
