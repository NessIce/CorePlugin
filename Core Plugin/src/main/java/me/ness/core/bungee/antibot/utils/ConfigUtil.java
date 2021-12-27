package me.ness.core.bungee.antibot.utils;

import me.ness.core.bungee.CoreBungee;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigUtil {

	private final Plugin plugin;
	private final TaskScheduler scheduler;

	public ConfigUtil(Plugin plugin) {
		this.plugin = plugin;
		this.scheduler = plugin.getProxy().getScheduler();
	}

	public Configuration getConfiguration(String file) {
		file = replaceDataFolder(file);

		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(file));
		} catch (IOException e) {
			return new Configuration();
		}
	}

	public void createConfiguration(String file) {
		try {
			file = replaceDataFolder(file);

			File configFile = new File(file);

			if (!configFile.exists()) {
				String[] files = file.split("/");

				InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);
				File parentFile = configFile.getParentFile();

				if (parentFile != null)
					parentFile.mkdirs();

				if (inputStream != null) {
					Files.copy(inputStream, configFile.toPath());
				} else {
					configFile.createNewFile();
				}
			}
		}catch (IOException e) {
			CoreBungee.sendMessage("§cFalha ao carregar o arquivo "+file+"!");
		}
	}

	public void saveConfiguration(Configuration configuration, String file) {
		String replacedFile = replaceDataFolder(file);

		this.scheduler.runAsync(plugin, () -> {
			try {
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(replacedFile));
			} catch (IOException e) {
				CoreBungee.sendMessage("§cFalha ao salvar o arquivo "+file+"!");
			}
		});
	}

	public void deleteConfiguration(String file) {
		String replacedFile = replaceDataFolder(file);
		File file1 = new File(replacedFile);

		if (file1.exists()) {
			file1.delete();
			CoreBungee.sendMessage("§cFalha ao deletar o arquivo "+file+"!");
		}
	}

	private String replaceDataFolder(String string) {
		File dataFolder = plugin.getDataFolder();
		return string.replace("%datafolder%", dataFolder.toPath().toString());
	}
}