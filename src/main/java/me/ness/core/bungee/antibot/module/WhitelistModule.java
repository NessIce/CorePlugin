package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class WhitelistModule extends PunishableModule {

	private static final String WHITELIST_PATH = "%datafolder%/whitelist.yml";
	private final ModuleManager moduleManager;
	private final Collection<String> whitelist = new HashSet<>();
	private long lastLockout = 0;
	private int timeWhitelist = 15000;
	private int timeLockout = 20000;
	private boolean requireSwitch = true;

	public WhitelistModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "whitelist";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(40, 0, 20), false);


		punishCommands.clear();
		punishCommands.add("disconnect %kick_whitelist%");

		requireSwitch = false;

		timeWhitelist = 15000;
		timeLockout = 20000;

		load(configUtil);
	}

	public void load(ConfigUtil configUtil) {
		Configuration whitelistYml = configUtil.getConfiguration(WHITELIST_PATH);

		this.whitelist.clear();

		if (whitelistYml != null) {
			this.whitelist.addAll(whitelistYml.getStringList(""));
		}
	}

	public void setWhitelisted(String ip, boolean input) {
		if (input) {
			moduleManager.getBlacklistModule().setBlacklisted(ip, false);
			whitelist.add(ip);
		} else {
			whitelist.remove(ip);
		}
	}

	public int getSize() {
		return whitelist.size();
	}

	public void save(ConfigUtil configUtil) {
		Configuration whitelistYml = configUtil.getConfiguration(WHITELIST_PATH);

		if (whitelistYml != null) {
			whitelistYml.set("", new ArrayList<>(whitelist));
			configUtil.saveConfiguration(whitelistYml, WHITELIST_PATH);
		}
	}

	@Override
	public boolean meet(Incoming...incoming) {
		return this.enabled && (thresholds.meet(incoming)
				|| System.currentTimeMillis() - this.lastLockout < this.timeLockout);
	}

	public boolean check(Connection connection) {
		return whitelist.contains(connection.getAddress().getHostString());
	}

	public boolean isRequireSwitch() {
		return requireSwitch;
	}

	public int getTimeWhitelist() {
		return timeWhitelist;
	}

	public void setLastLockout(long lastLockout) {
		if (System.currentTimeMillis() - this.lastLockout >= this.timeLockout) {
			this.lastLockout = lastLockout;
		}
	}

	public boolean isWhitelisted(String ip) {
		return this.whitelist.contains(ip);
	}

	public Collection<String> getWhitelist() {
		return this.whitelist;
	}
}
