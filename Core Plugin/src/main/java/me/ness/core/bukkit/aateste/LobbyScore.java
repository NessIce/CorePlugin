package me.ness.core.bukkit.aateste;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.scoreboard.VirtualScoreboard;
import me.ness.core.bukkit.utils.BungeeUtils;

public class LobbyScore extends VirtualScoreboard {

    public LobbyScore() {
        super("lScoreboard");
        super.setHealth(false);
        super.setTicks(3);
    }

    @Override
    public void sidebar(MineAccount mineAccount){
        getSidebar().setTitle("§6§lMINEVIL NETWORK", "§e§lMINEVIL NETWORK", "§f§lMINEVIL NETWORK", "§e§lMINEVIL NETWORK");

        getSidebar().addLine("§1");
        getSidebar().addLine(" §fGrupo: §e", mineAccount.getName());
        getSidebar().addLine("§2");
        getSidebar().addLine(" §fServidores:");
        getSidebar().addLine("  §fMinigames: §a", BungeeUtils.getCountServer("lobby"));
        getSidebar().addLine("  §fSurvival: §c", BungeeUtils.getCountServer("survival"));
        getSidebar().addLine("§3");
        getSidebar().addLine("§ewww.minevil.net");
    }

    @Override
    public void prefix() {
        getRoles().addRole("aAdministrador", "§4§lADMIN§4 ", "tag.master");
        getRoles().addRole("bModerador", "§2§lMOD§2 ", "tag.moderador");
        getRoles().addRole("cJogador", "§7§lMEMBRO§7 ", "");
    }
}
