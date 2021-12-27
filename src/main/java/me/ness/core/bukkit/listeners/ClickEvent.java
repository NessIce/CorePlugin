package me.ness.core.bukkit.listeners;

import me.ness.core.bukkit.libreries.menus.Menu;
import me.ness.core.bukkit.utils.BungeeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClickEvent(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();

        Player player = (Player)e.getWhoClicked();
        BungeeUtils.SERVER_COUNTS.put("lobby", BungeeUtils.SERVER_COUNTS.get("lobby")+1);

        if(e.getCurrentItem()==null){
            return;
        }

        ItemStack item = e.getCurrentItem();

        if(e.getCurrentItem().getType()== Material.AIR||!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        if(holder instanceof Menu){
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handler(item, player);
        }
    }
}
