package me.ness.core.bukkit.utils.nms;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class NMS {

    private final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage()
            .getName().replace(".", ",").split(",")[3];


    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + SERVER_VERSION + "." + name);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Class<?> getNMSArrayClass(String name) {
        try {
            return Class.forName("[Lnet.minecraft.server." + SERVER_VERSION + "." + name + ";");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION + "." + name);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
