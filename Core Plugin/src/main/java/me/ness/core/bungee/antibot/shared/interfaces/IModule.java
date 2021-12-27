package me.ness.core.bungee.antibot.shared.interfaces;

import me.ness.core.bungee.antibot.utils.ConfigUtil;

public interface IModule {

	String getName();

	void reload(ConfigUtil configUtil);
}
