package me.ness.core.bungee.antibot.module;

import me.ness.core.bungee.antibot.shared.interfaces.IModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;

public class CounterModule implements IModule {

	private final Incoming current = new Incoming();
	private final Incoming last = new Incoming();
	private int totalIncome = 0;
	private int totalBlocked = 0;

    @Override
    public String getName() {
        return "Counter";
    }

    @Override
    public void reload(ConfigUtil configUtil) {
        // Não será carregada!
    }
    
    public void update() {
		current.reset();
		last.reset();
		totalIncome = 0;
    }

	public Incoming getCurrent() {
		return current;
	}

	public Incoming getLast() {
		return last;
	}

	public int getTotalIncome() {
		return totalIncome;
	}

	public void addIncoming() {
		totalIncome++;
	}

	public void addTotalBlocked() {
		totalBlocked++;
	}

	public int getTotalBlocked() {
		return totalBlocked;
	}
}
