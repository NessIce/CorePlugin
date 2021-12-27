package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PasswordModule extends PunishableModule {

	private final Collection<String> authCommands = new HashSet<>();
	private String lastAddress = "";
	private String lastPassword = "";

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "password";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 0, 0), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_password%");

		authCommands.clear();
		authCommands.addAll(Arrays.asList("/register", "/registrar", "/login", "/logar"));
	}

	public void setLastValues(String address, String command) {
		if (command.contains(" ")) {
			String[] splittedCommand = command.split(" ");
			String password = splittedCommand[1];

			lastAddress = address;
			lastPassword = password;
		}
	}

	public boolean check(Connection connection, String command) {
		String address = connection.getAddress().getHostString();

		if (command.contains(" ")) {
			for (String authCommand : authCommands) {
				if (command.startsWith(authCommand)) {
					String[] splittedCommand = command.split(" ");
					String password = splittedCommand[1];

					return !address.equals(lastAddress) && password.equals(lastPassword);
				}
			}
		}
		return false;
	}
}
