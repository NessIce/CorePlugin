package me.ness.core.bukkit.libreries.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Menu implements InventoryHolder {

    private final String title;
    private final int slot;
    public Inventory inventory;

    public Menu(String title, int size){
        this.title = title;
        this.slot = size;
    }

    public void open(Player player){
        this.inventory = Bukkit.createInventory(this, slot, title);

        itens(player);

        player.openInventory(this.inventory);
    }

    public abstract void itens(Player player);
    public abstract void handler(ItemStack itemStack, Player player);

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
