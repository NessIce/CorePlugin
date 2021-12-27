package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.*;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.shared.instanceables.Punish;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

	private final ModuleManager moduleManager;
	private final BlacklistModule blacklistModule;
	private final PlayerModule playerModule;
	private final RateLimitModule rateLimitModule;
	private final WhitelistModule whitelistModule;

	public ProxyPingListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
		this.blacklistModule = moduleManager.getBlacklistModule();
		this.playerModule = moduleManager.getPlayerModule();
		this.rateLimitModule = moduleManager.getRateLimitModule();
		this.whitelistModule = moduleManager.getWhitelistModule();
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onProxyPing(ProxyPingEvent event) {
		if (event.getResponse() == null || event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
			return;
		}

		Connection connection = event.getConnection();
		String locale = moduleManager.getDefaultLanguage(); // Can't get locale on PreLogin.
		String ip = connection.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);
		long currentTimeMillis = System.currentTimeMillis();
		CounterModule counterModule = moduleManager.getCounterModule();
		Incoming current = counterModule.getCurrent();
		Incoming last = counterModule.getLast();

		if (whitelistModule.meet(current, last)) {
			new Punish(moduleManager, locale, whitelistModule, connection, event);

			whitelistModule.setLastLockout(currentTimeMillis);
		} else if (blacklistModule.meet(current, last) && blacklistModule.check(connection)) {
			new Punish(moduleManager, locale, blacklistModule, connection, event);

		} else if (rateLimitModule.meet(botPlayer.getIncoming())) {
			new Punish(moduleManager, locale, rateLimitModule, connection, event);

			blacklistModule.setBlacklisted(ip, true);
		}

		botPlayer.setLastPing(currentTimeMillis);
	}
}
