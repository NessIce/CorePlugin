package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class PlaceholderModule implements IModule {

	private final String pluginVersion;
	private final Map<String, String> placeholders = new HashMap<>();
	private final Collection<String> locales = new HashSet<>();
	private String defaultLang;

	public PlaceholderModule(Plugin plugin) {
		pluginVersion = plugin.getDescription().getVersion();
	}

	@Override
	public String getName() {
		return "placeholder";
	}

	private String setPlaceholders(String string, String locale) {
		for (Entry<String, String> entry : placeholders.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (string.contains(key)) {
				string = string.replace(key, value);
			} else if (locale != null && locale.length() > 1) {
				String keyLocaleReplaced = key.replace("%" + locale + "_", "%");

				if (string.contains(keyLocaleReplaced)) {
					string = setPlaceholders(string.replace(keyLocaleReplaced, value), locale);
				}
			}
		}

		return string;
	}

	public String setPlaceholders(ModuleManager moduleManager, String string, String locale,
			String address, String checkName) {
		if (locales.contains(locale)) {
			string = setPlaceholders(string, locale);
		} else if (locale != null && locale.length() > 2 && locales.contains(locale.substring(0, 2))) {
			string = setPlaceholders(string, locale.substring(0, 2));
		} else {
			string = setPlaceholders(string, defaultLang);
		}

		if (moduleManager != null) {
			if (address != null) {
				ReconnectModule reconnectModule = moduleManager.getReconnectModule();
				PlayerModule playerModule = moduleManager.getPlayerModule();
				BotPlayer botPlayer = playerModule.get(address);

				if (botPlayer != null) {
					Incoming incoming = botPlayer.getIncoming();
					int reconnects = botPlayer.getReconnects(), timesConnect = reconnectModule.getTimesConnect(),
							reconnectTimes = reconnects > timesConnect ? 0 : timesConnect - reconnects;

					string = string.replace("%reconnect_times%", String.valueOf(reconnectTimes))
							.replace("%addresspps%", String.valueOf(incoming.getPPS()))
							.replace("%addresscps%", String.valueOf(incoming.getCPS()))
							.replace("%addressjps%", String.valueOf(incoming.getJPS())).replace("%address%", address);
				}
			}

			CounterModule counterModule = moduleManager.getCounterModule();
			Incoming current = counterModule.getCurrent();
			Incoming last = counterModule.getLast();

			string = string.replace("%lastpps%", String.valueOf(last.getPPS()))
					.replace("%lastcps%", String.valueOf(last.getCPS()))
					.replace("%lastjps%", String.valueOf(last.getCPS()))
					.replace("%currentpps%", String.valueOf(current.getPPS()))
					.replace("%currentcps%", String.valueOf(current.getCPS()))
					.replace("%currentjps%", String.valueOf(current.getCPS()))
					.replace("%currentincoming%", String.valueOf(counterModule.getTotalIncome()))
					.replace("%totalblocked%", String.valueOf(counterModule.getTotalBlocked()))
					.replace("%totalbls%", String.valueOf(moduleManager.getBlacklistModule().getSize()))
					.replace("%totalwls%", String.valueOf(moduleManager.getWhitelistModule().getSize()));
		}

		if (checkName != null) {
			string = string.replace("%check%", checkName);
		}

		return ChatColor.translateAlternateColorCodes('&', string.replace("%version%", pluginVersion));
	}

	public String setPlaceholders(String string) {
		return setPlaceholders(null, string, null, null, null);
	}

	public String setPlaceholders(ModuleManager moduleManager, String string) {
		return setPlaceholders(moduleManager, string, null, null, null);
	}

	public String setPlaceholders(ModuleManager moduleManager, String string, String locale) {
		return setPlaceholders(moduleManager, string, locale, null, null);
	}

	public String setPlaceholders(ModuleManager moduleManager, String string, String locale,
			String address) {
		return setPlaceholders(moduleManager, string, locale, address, null);
	}

	private void addSection(StringBuilder path, Configuration section) {
		for (String key : section.getKeys()) {
			Object value = section.get(key);

			if (value instanceof Configuration) {
				addSection(new StringBuilder(path).append(".").append(key), (Configuration) value);
			} else if (defaultLang != null && value instanceof String) {
				placeholders.put(("%" + String.valueOf(path) + "." + key + "%").replace(".", "_")
						.replace("%_", "%"), setPlaceholders((String) value));
			}
		}
	}

	@Override
	public void reload(ConfigUtil configUtil) {
		Configuration messagesYml = configUtil.getConfiguration("%datafolder%/messages.yml");
		StringBuilder path = new StringBuilder();

		defaultLang = "pt-BR";
		placeholders.clear();

		for (String key : messagesYml.getKeys()) {
			if (key.length() < 6) {
				locales.add(key);
			}
		}

		addSection(path, messagesYml);
	}
}