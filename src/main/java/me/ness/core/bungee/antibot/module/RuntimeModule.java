package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class RuntimeModule implements IModule {

	private final Runtime runtime = Runtime.getRuntime();
	private final Collection<String> blacklisted = new HashSet<>(), addCommands = new HashSet<>(), removeCommands = new HashSet<>();
	private long lastUpdateTime = 0;
	private int time = 20000;
	private boolean enabled = true;

	@Override
	public String getName() {
		return "runtime";
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		enabled = false;
		time = 20000;

		addCommands.add("iptables -A INPUT -s %address% -j DROP");

		removeCommands.add("iptables -D INPUT -s %address% -j DROP");
	}

	public void update() {
		if (enabled && !removeCommands.isEmpty()) {
			long currentTime = System.currentTimeMillis();

			if (time != -1 && currentTime - lastUpdateTime > time) {
				lastUpdateTime = currentTime;

				try {
					for (String address : new HashSet<>(blacklisted)) {
						removeBlacklisted(address);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addBlacklisted(String address) throws IOException {
		if (enabled && !blacklisted.contains(address)) {
			for (String command : addCommands) {
				runtime.exec(command.replace("%address%", address).replace("%time%", String.valueOf(time)));
			}

			blacklisted.add(address);
		}
	}

	public void removeBlacklisted(String address) throws IOException {
		if (enabled && blacklisted.contains(address)) {
			for (String command : removeCommands) {
				if (!command.isEmpty()) {
					runtime.exec(command.replace("%address%", address).replace("%time%", String.valueOf(time)));
				}
			}

			blacklisted.remove(address);
		}
	}
}
