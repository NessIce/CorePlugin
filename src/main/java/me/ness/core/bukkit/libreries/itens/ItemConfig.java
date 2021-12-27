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
import java.util.*;

public class ItemConfig {

    private Player player;
    private String itemData;
    private Map<String, Object> placeHolders;

    public ItemConfig(String itemData){
        this.itemData = itemData;
        this.placeHolders = new HashMap<>();
    }

    public ItemStack getItem(){
        String[] attributes = itemData.split(" : ");

        for (int index = 0; index < attributes.length; index++){
            for(String place : placeHolders.keySet()){
                if(attributes[index].contains(place)){
                    attributes[index] = attributes[index].replace(place, ""+placeHolders.get(place));
                }
            }
        }

        Material material = Material.getMaterial(attributes[0]);
        int data = Integer.parseInt(attributes[1]);
        int amount = Integer.parseInt(attributes[2]);

        ItemStack item = new ItemStack(material, amount, (short)data);

        if(attributes.length>3){
            ItemMeta meta = item.getItemMeta();

            for (int i = 3; i < attributes.length; i++) {

                if(attributes[i]==null){
                    break;
                }

                String[] attributeData = attributes[i].split("=");
                String caracter = attributeData[0];
                String value = attributeData[1];

                switch (caracter){
                    case "display":{
                        meta.setDisplayName(value.replace("&", "§"));
                        break;
                    }
                    case "lore":{
                        List<String> lore = Arrays.asList(value.split(";"));
                        lore.replaceAll(e -> e.replace("&", "§"));
                        meta.setLore(lore);
                        break;
                    }
                    case "enchants":{
                        for(String s : value.split(";")){
                            String enchantName = s.split(":")[0];
                            int enchantLevel = Integer.parseInt(s.split(":")[1]);
                            meta.addEnchant(Enchantment.getByName(enchantName), enchantLevel, true);
                        }
                        break;
                    }
                    case "flags":{
                        for(String s : value.split(";")){
                            meta.addItemFlags(ItemFlag.valueOf(s));
                        }
                        break;
                    }
                    case "url":{
                        if (value.isEmpty()) { break; }
                        SkullMeta headMeta = (SkullMeta) meta;
                        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", attributeData[1]).getBytes());
                        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
                        Field profileField;
                        try {
                            profileField = headMeta.getClass().getDeclaredField("profile");
                            profileField.setAccessible(true);
                            profileField.set(headMeta, profile);
                        }catch (Exception e) { e.printStackTrace(); }
                        break;
                    }
                    case "player":{
                        SkullMeta headMeta = (SkullMeta) meta;
                        headMeta.setOwner(value);
                        break;
                    }
                }
            }
            item.setItemMeta(meta);
        }
        return item;
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public ItemConfig addHolder(String string, Object value){
        placeHolders.put(string, value);
        return this;
    }

    public Map<String, Object> getPlaceHolders() {
        return placeHolders;
    }

    public void setPlaceHolders(Map<String, Object> placeHolders) {
        this.placeHolders = placeHolders;
    }
}
