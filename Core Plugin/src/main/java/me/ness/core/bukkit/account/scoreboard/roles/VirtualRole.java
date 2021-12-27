package me.ness.core.bukkit.account.scoreboard.roles;

public class VirtualRole {

    private final String name, prefix, permission;

    public VirtualRole(String name, String prefix, String permission) {
        this.name = name;
        this.prefix = prefix;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPermission() {
        return permission;
    }
}
