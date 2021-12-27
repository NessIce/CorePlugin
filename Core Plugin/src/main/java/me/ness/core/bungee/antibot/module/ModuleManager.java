package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Punish;
import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.BungeeUtil;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.HashSet;

public class ModuleManager {

	private final ProxyServer proxyServer;
	private final ConfigUtil configUtil;

	private final IModule[] modules = new IModule[14];
	private String defaultLanguage;

	public ModuleManager(Plugin plugin, ConfigUtil configUtil) {
		this.proxyServer = plugin.getProxy();
		this.configUtil = configUtil;

		this.modules[0] = new AccountsModule(this);
		this.modules[1] = new BlacklistModule(this);
		this.modules[2] = new FastChatModule(this);
		this.modules[3] = new NicknameModule();
		this.modules[4] = new NotificationsModule(this, plugin.getLogger());
		this.modules[5] = new PlaceholderModule(plugin);
		this.modules[6] = new PlayerModule();
		this.modules[7] = new RateLimitModule(this);
		this.modules[8] = new ReconnectModule(this);
		this.modules[9] = new PasswordModule();
		this.modules[10] = new RuntimeModule();
		this.modules[11] = new SettingsModule();
		this.modules[12] = new WhitelistModule(this);
		this.modules[13] = new CounterModule();
	}

	public void reload() {
		try {
			Configuration config = configUtil.getConfiguration("%datafolder%/config.yml");
			String lang = config.getString("lang");

			for (IModule module : modules) {
				module.reload(configUtil);
			}

			if (lang != null) {
				defaultLanguage = lang;
			} else {
				defaultLanguage = "en";
			}
		} catch (Exception exception) {
			CoreBungee.sendMessage("§cUma falha aconteceu ao recarregar os modulos do AntiBot!");
			throw exception;
		}
	}

	public void update() {
		PlayerModule playerModule = getPlayerModule();
		SettingsModule settingsModule = getSettingsModule();
		CounterModule counterModule = getCounterModule();

		long currentTime = System.currentTimeMillis();
		long settingsDelay = settingsModule.getDelay();
		int cacheTime = playerModule.getCacheTime();
		boolean settingsModuleMeet = settingsModule.meet(counterModule.getCurrent(), counterModule.getLast());

		getRuntimeModule().update();
		counterModule.update();

		try {
			Collection<BotPlayer> pendingPlayers = settingsModule.getPending();
			Collection<BotPlayer> offlinePlayers = playerModule.getOfflinePlayers();

			for (BotPlayer botPlayer : new HashSet<>(offlinePlayers)) {
				if (botPlayer == null || currentTime - botPlayer.getLastConnection() > cacheTime) {
					offlinePlayers.remove(botPlayer);
					pendingPlayers.remove(botPlayer);
				} else if (settingsModuleMeet && pendingPlayers.contains(botPlayer)) {
					if (botPlayer.isSettings()) {
						pendingPlayers.remove(botPlayer);
					} else if (currentTime - botPlayer.getLastConnection() >= settingsDelay) {
						for (String playerName : botPlayer.getAccounts()) {
							ProxiedPlayer player = proxyServer.getPlayer(playerName);

							if (player != null) {
								String language = BungeeUtil.getLanguage(player, defaultLanguage);

								new Punish(this, language, settingsModule, player, null);
								pendingPlayers.remove(botPlayer);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			CoreBungee.sendMessage("§cUma falha aconteceu no AntiBot: "+e.getClass().getName()+"! (ModuleManager.java)");
		}
	}

	public AccountsModule getAccountsModule() {
		return (AccountsModule) this.modules[0];
	}

	public BlacklistModule getBlacklistModule() {
		return (BlacklistModule) this.modules[1];
	}

	public FastChatModule getFastChatModule() {
		return (FastChatModule) this.modules[2];
	}

	public NicknameModule getNicknameModule() {
		return (NicknameModule) this.modules[3];
	}

	public NotificationsModule getNotificationsModule() {
		return (NotificationsModule) this.modules[4];
	}

	public PlaceholderModule getPlaceholderModule() {
		return (PlaceholderModule) this.modules[5];
	}

	public PlayerModule getPlayerModule() {
		return (PlayerModule) this.modules[6];
	}

	public RateLimitModule getRateLimitModule() {
		return (RateLimitModule) this.modules[7];
	}

	public ReconnectModule getReconnectModule() {
		return (ReconnectModule) this.modules[8];
	}

	public PasswordModule getRegisterModule() {
		return (PasswordModule) this.modules[9];
	}

	public RuntimeModule getRuntimeModule() {
		return (RuntimeModule) this.modules[10];
	}

	public SettingsModule getSettingsModule() {
		return (SettingsModule) this.modules[11];
	}

	public WhitelistModule getWhitelistModule() {
		return (WhitelistModule) this.modules[12];
	}

	public CounterModule getCounterModule() {
		return (CounterModule) this.modules[13];
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}
}