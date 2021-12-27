package me.ness.core.bungee.antibot.tasks;


import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.antibot.AntiBot;
import me.ness.core.bungee.antibot.module.ModuleManager;

public class AntiBotSecondTask implements Runnable {

    private final AntiBot antiBot;
    private final ModuleManager moduleManager;

    public AntiBotSecondTask(AntiBot antiBot, ModuleManager moduleManager) {
        this.antiBot = antiBot;
        this.moduleManager = moduleManager;
    }

    @Override
    public void run() {
        while (antiBot.isRunning()) {
            try {
                moduleManager.update();
                Thread.sleep(1000);
            } catch (Exception e) {
                CoreBungee.sendMessage("§cUma falha aconteceu no AntiBot!");
            }
        }
    }
}
