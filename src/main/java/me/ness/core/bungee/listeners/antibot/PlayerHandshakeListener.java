package me.ness.core.bungee.listeners.antibot;

import me.ness.core.bungee.antibot.module.CounterModule;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.PlayerModule;
import me.ness.core.bungee.antibot.shared.instanceables.BotPlayer;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerHandshakeListener implements Listener {

	private final ModuleManager moduleManager;
	private final PlayerModule playerModule;

	public PlayerHandshakeListener(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
		this.playerModule = moduleManager.getPlayerModule();
	}

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onPlayerHandshake(PlayerHandshakeEvent event) {
		if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
			return;
		}

		PendingConnection connection = event.getConnection();
		CounterModule counterModule = moduleManager.getCounterModule();
		Incoming incoming = counterModule.getCurrent();
		String ip = connection.getAddress().getHostString();
		BotPlayer botPlayer = playerModule.get(ip);
		int requestedProtocol = event.getHandshake().getRequestedProtocol();

		counterModule.addIncoming();

		if (requestedProtocol == 1) {
			incoming.addPPS();
			botPlayer.getIncoming().addPPS();
			botPlayer.setRepings(botPlayer.getRepings() + 1);
		} else {
			incoming.addCPS();
			botPlayer.getIncoming().addCPS();
		}
	}
}
