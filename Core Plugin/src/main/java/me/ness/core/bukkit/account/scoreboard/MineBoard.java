package me.ness.core.bukkit.account.scoreboard;

import me.ness.core.bukkit.CoreBukkit;
import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.scoreboard.sidebar.VirtualLine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineBoard {

    private final MineAccount account;
    private Scoreboard scoreboard;

    public MineBoard(MineAccount account){
        this.account = account;
    }

    public Objective getOrCreateObjective(String name, String value){
        return scoreboard.getObjective(name)!=null? scoreboard.getObjective(name): scoreboard.registerNewObjective(name, value);
    }
    public Team getOrCreateTeam(String name){
        return scoreboard.getTeam(name)!=null? scoreboard.getTeam(name): scoreboard.registerNewTeam(name);
    }



    public void apply(String name){
        //Definindo as variaveis globais
        VirtualScoreboard virtual = VirtualScoreboard.SCOREBOARDS.get(name);
        scoreboard = account.getMineLiveAccount().getPlayer().getScoreboard();

        //Limpando a scoreboard atual!
        scoreboard.getObjectives().forEach(Objective::unregister);
        scoreboard.getTeams().forEach(Team::unregister);

        //Aplicando os padrões da virtualboard
        virtual.sidebar(account);
        virtual.prefix();

        //Criando o sidebar
        Objective sidebar = getOrCreateObjective("Sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        //Criando o healthbar caso esteja ativo
        if(virtual.isHealth()){
            Objective health = getOrCreateObjective("Health", "health");
            health.setDisplaySlot(DisplaySlot.BELOW_NAME);
            health.setDisplayName("§c❤");
        }


        //Criando as teams do roles
        virtual.getRoles().forEach(role-> getOrCreateTeam(role.getName()).setPrefix(role.getPrefix()));


        //Criando a lista de teams da sidebar
        Map<Team, Object> sidebarTeams = new HashMap<>();
        List<VirtualLine> virtualLines = virtual.getSidebar().getReverseLines();
        for(VirtualLine lines : virtualLines){
            sidebar.getScore(lines.getLine()).setScore(virtualLines.indexOf(lines));

            if(lines.getValue()!=null){
                Team team = getOrCreateTeam("score["+virtualLines.indexOf(lines)+"]");
                team.addEntry(lines.getLine());
                team.setSuffix(lines.getValue().toString());
                sidebarTeams.put(team, lines.getValue());
            }
        }


        //Runnable responsavel por atualizar a scoreboard e prefix
        new BukkitRunnable(){
            int titleFrame = 0;
            @Override
            public void run() {
                if(!account.isOnline()){
                    cancel();
                }

                //Atualizando a sidebar
                sidebar.setDisplayName(virtual.getSidebar().getTitle().get(titleFrame));
                sidebarTeams.forEach((team, value)-> team.setSuffix(value.toString()));

                //Atualizando as prefix
                for(Player players : Bukkit.getOnlinePlayers()){
                    Team playersTeam = getOrCreateTeam(virtual.getRoles().getPlayerRole(players).getName());
                    if(!playersTeam.hasEntry(players.getName())){
                        playersTeam.addEntry(players.getName());
                    }
                }
                titleFrame++;

                if(titleFrame > virtual.getSidebar().getTitle().size()-1){
                    titleFrame = 0;
                }
            }
        }.runTaskTimerAsynchronously(CoreBukkit.getCore(), 0, virtual.getTicks());
    }
}
