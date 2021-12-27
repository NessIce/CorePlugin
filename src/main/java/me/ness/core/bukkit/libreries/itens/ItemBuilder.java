package me.ness.core.bukkit.libreries.itens;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack, String displayName, int data, List<String> lore){
        this.item = itemStack;
        item.setDurability((short)data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public ItemBuilder(Material material, String name) {
        this.item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name, int data) {
        this.item = new ItemStack(material);
        this.item.setDurability((short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name, int data, List<String> lore) {
        this.item = new ItemStack(material, 1, (short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name, int data, List<String> lore, boolean potion) {
        this.item = new ItemStack(material, 1, (short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name, List<String> lore) {
        this.item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name, List<String> lore, boolean glow) {
        this.item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, Enchantment enchantment, int level) {
        this.item = new ItemStack(material);
        this.item.addEnchantment(enchantment, level);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, boolean state) {
        this.item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.spigot().setUnbreakable(state);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(Player player, String name, List<String> lore) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.setOwner(player.getName());
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        this.item.setItemMeta(itemMeta);
    }

    public ItemBuilder(String url, String name, List<String> lore) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty()) {
            return;
        }
        SkullMeta headMeta = (SkullMeta) this.item.getItemMeta();
        headMeta.setDisplayName(name);
        headMeta.setLore(lore);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        this.item.setItemMeta(headMeta);
    }

    public void setDisplayName(String name){
        ItemMeta meta = item.getItemMeta();;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public void setLore(List<String> lore){
        ItemMeta meta = item.getItemMeta();;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
