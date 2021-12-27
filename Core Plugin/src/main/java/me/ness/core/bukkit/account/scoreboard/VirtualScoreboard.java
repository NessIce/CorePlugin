package me.ness.core.bukkit.account.scoreboard;

import me.ness.core.bukkit.account.MineAccount;
import me.ness.core.bukkit.account.scoreboard.roles.Roles;
import me.ness.core.bukkit.account.scoreboard.sidebar.Sidebar;

import java.util.HashMap;
import java.util.Map;

public abstract class VirtualScoreboard {

    public static Map<String, VirtualScoreboard> SCOREBOARDS = new HashMap<>();

    private final String name;
    private final Sidebar sidebar;
    private final Roles roles;
    private boolean health;
    private int ticks;

    public VirtualScoreboard(String name){
        this.name = name;

        sidebar = new Sidebar();
        roles = new Roles();

        health = false;
        ticks = 3;

        SCOREBOARDS.put(name, this);
    }

    public abstract void sidebar(MineAccount mineAccount);
    public abstract void prefix();

    public void setHealth(boolean health) {
        this.health = health;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }



    public String getName() {
        return name;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public Roles getRoles() {
        return roles;
    }

    public boolean isHealth() {
        return health;
    }

    public int getTicks() {
        return ticks;
    }
}
