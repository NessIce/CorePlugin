package me.ness.core.bungee.antibot.shared.extendables;

import me.ness.core.bungee.antibot.shared.instanceables.Threshold;
import me.ness.core.bungee.antibot.shared.interfaces.IPunishModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import me.ness.core.bungee.antibot.utils.Incoming;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.HashSet;

public class PunishableModule implements IPunishModule {

    protected Collection<String> punishCommands = new HashSet<>();
    protected Threshold thresholds;
    protected String name = "<N/A>";
    protected boolean enabled = true;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void reload(ConfigUtil configUtil) {
        Configuration configYml = configUtil.getConfiguration("%datafolder%/config.yml");
        int pps = configYml.getInt(name + ".threshold.pps", 0);
        int cps = configYml.getInt(name + ".threshold.cps", 0);
        int jps = configYml.getInt(name + ".threshold.jps", 0);
        
        enabled = configYml.getBoolean(name + ".enabled", enabled);
        thresholds = new Threshold(new Incoming(pps, cps, jps), false);
    }

    @Override
    public Collection<String> getPunishCommands() {
        return punishCommands;
    }

    public boolean meet(Incoming ...incoming) {
        return this.enabled && (thresholds.meet(incoming));
    }
}
