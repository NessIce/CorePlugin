package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class BlacklistModule extends PunishableModule {

	private static final String BLACKLIST_PATH = "%datafolder%/blacklist.yml";
	private final Collection<String> blacklist = new HashSet<>();

	private final ModuleManager moduleManager;

	public BlacklistModule(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "blacklist";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 2, 0), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_blacklist%");

		load(configUtil);
	}

	public void setBlacklisted(String address, boolean blacklist) {
		if (blacklist) {
			moduleManager.getWhitelistModule().setWhitelisted(address, false);

			try {
				moduleManager.getRuntimeModule().addBlacklisted(address);
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.blacklist.add(address);
		} else {
			this.blacklist.remove(address);
		}
	}

	public int getSize() {
		return blacklist.size();
	}

	public void save(ConfigUtil configUtil) {
		Configuration blacklistYml = configUtil.getConfiguration(BLACKLIST_PATH);

		if (blacklistYml != null) {
			blacklistYml.set("", new ArrayList<>(blacklist));
			configUtil.saveConfiguration(blacklistYml, BLACKLIST_PATH);
		}
	}

	public void load(ConfigUtil configUtil) {
		Configuration blacklistYml = configUtil.getConfiguration(BLACKLIST_PATH);

		this.blacklist.clear();
		this.blacklist.addAll(blacklistYml.getStringList(""));
	}

	public boolean check(Connection connection) {
		return isBlacklisted(connection.getAddress().getHostString());
	}

	public boolean isBlacklisted(String ip) {
		return this.blacklist.contains(ip);
	}

	public Collection<String> getBlacklist() {
		return this.blacklist;
	}
}
