package me.ness.core.bukkit.account.scoreboard.roles;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Roles {

    private final List<VirtualRole> roles;

    public Roles(){
        roles = new ArrayList<>();
    }

    public void addRole(String name, String prefix, String permission){
        roles.add(new VirtualRole(name, prefix, permission));
    }

    public VirtualRole getPlayerRole(Player player){
        for(VirtualRole role : roles){
            if(player.hasPermission(role.getPermission())){
                return role;
            }
        }
        return roles.get(roles.size()-1);
    }

    public void forEach(Consumer<? super VirtualRole> consumer){
        roles.forEach(consumer);
    }
}