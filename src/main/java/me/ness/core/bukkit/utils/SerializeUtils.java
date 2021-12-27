package me.ness.core.bukkit.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.io.*;

@UtilityClass
public class SerializeUtils {

    public byte[] serializeObject(Object object){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object deserializeObject(byte[] data){
        try {
            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String serializeItemStack(){
        return null;
    }

    public ItemStack deserializeItemStack(){
        return null;
    }

    public String serializeLocation(Location location){
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String world = location.getWorld().getName();
        return x+" ; "+y+" ; "+z+" ; "+yaw+" ; "+pitch+" ; "+world;
    }

    public Location deserializeLocation(String data){
        String[] parts = data.split(" ; ");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        float yaw = Float.parseFloat(parts[3]);
        float pitch = Float.parseFloat(parts[4]);
        World world = Bukkit.getWorld(parts[5]);
        Location location = new Location(world, x,y,z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}
