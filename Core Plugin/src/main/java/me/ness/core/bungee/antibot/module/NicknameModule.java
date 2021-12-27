package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.extendables.PunishableModule;
import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

public class NicknameModule extends PunishableModule {

	private final Collection<String> blacklist = new HashSet<>();
	private static final String MCSPAM_WORDS = "(Craft|Beach|Actor|Games|Tower|Elder|Mine|Nitro|Worms|Build|Plays|Hyper|Crazy|Super|_Itz|Slime)";
	private static final String MCSPAM_SUFFIX = "(11|50|69|99|88|HD|LP|XD|YT)";
	private static final Pattern PATTERN = Pattern.compile("^" + MCSPAM_WORDS + MCSPAM_WORDS + MCSPAM_SUFFIX);
	private String lastNickname = "A";

	@Override
	public void reload(ConfigUtil configUtil) {
		super.name = "nickname";

		super.enabled = true;
		super.thresholds = new Threshold(new Incoming(0, 4, 0), false);

		punishCommands.clear();
		punishCommands.add("disconnect %kick_nickname%");

		blacklist.clear();
		blacklist.addAll(Arrays.asList("mcspam", "mcdrop", "mcstorm"));
	}

	@Override
	public boolean meet(Incoming...incoming) {
		return this.enabled && (thresholds.meet(incoming));
	}

	public boolean check(Connection connection) {
		if (connection instanceof ProxiedPlayer) {
			String name = ((ProxiedPlayer) connection).getName();

			if (!name.equals(lastNickname) && name.length() == lastNickname.length()) {
				return true;
			} else {
				String lowerName = name.toLowerCase();

				for (String blacklisted : blacklist) {
					if (lowerName.contains(blacklisted)) {
						return true;
					}
				}

				return PATTERN.matcher(name).find();
			}
		}

		return false;
	}

	public String getLastNickname() {
		return lastNickname;
	}

	public void setLastNickname(String nickname) {
		lastNickname = nickname;
	}
}
