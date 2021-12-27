package me.ness.core.bungee.antibot;

import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.tasks.AntiBotSecondTask;
import me.ness.core.bungee.antibot.utils.ConfigUtil;

public class AntiBot{

	private ModuleManager moduleManager;
	private ConfigUtil configUtil;

	private boolean running = true;

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public ConfigUtil getConfigUtil(){
		return configUtil;
	}

	public boolean isRunning() {
		return running;
	}


	/**
	 * Iniciar o AntiBot da rede!
	 */
	public void enable() {
		this.configUtil = new ConfigUtil(CoreBungee.getCore());
		reload();

		new Thread(new AntiBotSecondTask(this, moduleManager)).start();
	}

	/**
	 * Desativar o AntiBot da rede!
	 */
	public void disable() {
		running = false;

		moduleManager.getBlacklistModule().save(configUtil);
		moduleManager.getRuntimeModule().update();
		moduleManager.getWhitelistModule().save(configUtil);
	}

	/**
	 * Recarregar o AntiBot da rede!
	 */
	public void reload() {
		configUtil.createConfiguration("%datafolder%/messages.yml");
		configUtil.createConfiguration("%datafolder%/blacklist.yml");
		configUtil.createConfiguration("%datafolder%/whitelist.yml");

		moduleManager = new ModuleManager(CoreBungee.getCore(), configUtil);
		moduleManager.reload();
		CoreBungee.sendMessage("§6[Core] §eModulos do AntiBot carregados!");
	}
}