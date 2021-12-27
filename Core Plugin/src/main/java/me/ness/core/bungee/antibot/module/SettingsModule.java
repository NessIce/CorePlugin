package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;

import java.util.Collection;
import java.util.HashSet;

public class SettingsModule extends PunishableModule {

	private final Collection<BotPlayer> pending = new HashSet<>();
	private int delay = 5000;
	private boolean switching = false;

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "settings";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 0, 0), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_settings%");
		delay = 10000;
		switching = false;
	}

	public Collection<BotPlayer> getPending() {
		return pending;
	}

	public void addPending(BotPlayer botPlayer) {
		pending.add(botPlayer);
	}

	public void removePending(BotPlayer botPlayer) {
		pending.remove(botPlayer);
	}

	public long getDelay() {
		return delay;
	}

	public boolean isSwitching() {
		return switching;
	}
}
