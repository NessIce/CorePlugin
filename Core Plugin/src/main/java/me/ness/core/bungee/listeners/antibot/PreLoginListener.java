package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.*;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Punish;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {

	private final ModuleManager moduleManager;
	private final AccountsModule accountsModule;
	private final BlacklistModule blacklistModule;
	private final NicknameModule nicknameModule;
	private final PlayerModule playerModule;
	private final RateLimitModule rateLimitModule;
	private final ReconnectModule reconnectModule;
	private final WhitelistModule whitelistModule;

	public PreLoginListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
		this.accountsModule = moduleManager.getAccountsModule();
		this.blacklistModule = moduleManager.getBlacklistModule();
		this.nicknameModule = moduleManager.getNicknameModule();
		this.rateLimitModule = moduleManager.getRateLimitModule();
		this.playerModule = moduleManager.getPlayerModule();
		this.reconnectModule = moduleManager.getReconnectModule();
		this.whitelistModule = moduleManager.getWhitelistModule();
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onPreLogin(PreLoginEvent event) {
		if (event.isCancelled()) {
			return;
		}

		PendingConnection connection = event.getConnection();

		if (whitelistModule.check(connection)) {
			return;
		}

		String locale = moduleManager.getDefaultLanguage(); // Can't get locale on PreLogin.
		String ip = connection.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);
		String name = connection.getName();
		long currentTimeMillis = System.currentTimeMillis();
		CounterModule counterModule = moduleManager.getCounterModule();
		Incoming current = counterModule.getCurrent();
		Incoming last = counterModule.getLast();

		if (nicknameModule.meet(current, last) && nicknameModule.check(connection)) {
			new Punish(moduleManager, locale, nicknameModule, connection, event);
		} else if (whitelistModule.meet(current, last)) {
			new Punish(moduleManager, locale, blacklistModule, connection, event);

			whitelistModule.setLastLockout(currentTimeMillis);
		} else if (blacklistModule.meet(current, last) && blacklistModule.check(connection)) {
			new Punish(moduleManager, locale, blacklistModule, connection, event);
		} else if (rateLimitModule.meet(botPlayer.getIncoming())) {
			new Punish(moduleManager, locale, rateLimitModule, connection, event);

			blacklistModule.setBlacklisted(ip, true);
		} else if (accountsModule.meet(current, last) && accountsModule.check(connection)) {
			new Punish(moduleManager, locale, accountsModule, connection, event);
		} else if (reconnectModule.meet(current, last) && reconnectModule.check(connection)) {
			botPlayer.setReconnects(botPlayer.getReconnects() + 1);

			new Punish(moduleManager, locale, reconnectModule, connection, event);
		}

		botPlayer.setLastNickname(name);
		nicknameModule.setLastNickname(name);
		botPlayer.setLastConnection(currentTimeMillis);
	}
}
