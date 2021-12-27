package me.ness.core.bungee.commands;

import me.ness.core.bungee.CoreBungee;
import me.ness.core.bungee.antibot.module.BlacklistModule;
import me.ness.core.bungee.antibot.module.ModuleManager;
import me.ness.core.bungee.antibot.module.NotificationsModule;
import me.ness.core.bungee.antibot.module.WhitelistModule;
import me.ness.core.bungee.antibot.utils.ConfigUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntibotCommand extends Command {

    private final ConfigUtil configUtil;
    private final ModuleManager moduleManager;
    private final Pattern ipPattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");

    public AntibotCommand() {
        super("antibot", "", "ab");

        this.configUtil = CoreBungee.getCore().getAntiBot().getConfigUtil();
        this.moduleManager = CoreBungee.getCore().getAntiBot().getModuleManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("minevil.antibot.admin")){
            sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para executar este comando!"));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem utilizar este comando!"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)sender;

        BlacklistModule blacklistModule = moduleManager.getBlacklistModule();
        WhitelistModule whitelistModule = moduleManager.getWhitelistModule();

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "notificar": {
                    NotificationsModule notificationsModule = moduleManager.getNotificationsModule();

                    if (!notificationsModule.isEnabled()) {
                        player.sendMessage(TextComponent.fromLegacyText("§cO modulo de notificações esta desativadas!"));
                        return;
                    }

                    boolean hasNotifications = notificationsModule.hasNotifications(player);
                    notificationsModule.setNotifications(player, !hasNotifications);
                    String notiMessage = !hasNotifications ? "§aVocê habilitou as notificações!" : "§cVocê desabilitou as notificações!";
                    player.sendMessage(TextComponent.fromLegacyText(notiMessage));
                    return;
                }

                case "reload": {
                    CoreBungee.getCore().getAntiBot().reload();
                    player.sendMessage(TextComponent.fromLegacyText("§aO sistema de AntiBot foi recarregado com sucesso!"));
                    return;
                }

                case "status": {
                    int currentpps = moduleManager.getCounterModule().getCurrent().getPPS();
                    int currentcps = moduleManager.getCounterModule().getCurrent().getCPS();
                    int currentjps = moduleManager.getCounterModule().getCurrent().getJPS();

                    int totalbls = blacklistModule.getSize();
                    int totalwls = whitelistModule.getSize();

                    player.sendMessage(TextComponent.fromLegacyText("§e" + currentpps + " PPS §7> §e" + currentcps + " CPS §7> §e" + currentjps + " JPS §7> §e" + totalbls + " BLS §7> §e" + totalwls + " WLS"));
                    return;
                }

                case "blacklist": {
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("salvar")) {
                            blacklistModule.save(configUtil);
                            player.sendMessage(TextComponent.fromLegacyText("§aA blacklist foi salva com sucesso!"));
                            return;
                        }
                        if (args[1].equalsIgnoreCase("carregar")) {
                            blacklistModule.load(configUtil);
                            player.sendMessage(TextComponent.fromLegacyText("§eA blacklist foi carregada com sucesso!"));
                            return;
                        }
                        player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot blacklist <carregar/salvar/add/remover>"));
                        return;
                    }

                    if (args.length == 3) {
                        String ip = args[2];
                        Matcher matcher = ipPattern.matcher(ip);

                        if (args[1].equalsIgnoreCase("add")) {
                            if (matcher.matches()) {
                                if (!blacklistModule.isBlacklisted(ip)) {
                                    blacklistModule.setBlacklisted(ip, true);
                                    player.sendMessage(TextComponent.fromLegacyText("§aO ip §f" + ip + "§a foi adcionado a blacklist!"));
                                    return;
                                }
                                player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c já existe na blacklist!"));
                                return;
                            }
                            player.sendMessage(TextComponent.fromLegacyText("§cO ip informado é invalido!"));
                            return;
                        }

                        if (args[1].equalsIgnoreCase("remover")) {
                            if (matcher.matches()) {
                                if (blacklistModule.isBlacklisted(ip)) {
                                    blacklistModule.setBlacklisted(ip, false);
                                    player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c foi removido da blacklist!"));
                                    return;
                                }
                                player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c não esta na blacklist!"));
                                return;
                            }
                            player.sendMessage(TextComponent.fromLegacyText("§cO ip informado é invalido!"));
                            return;
                        }

                        player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot blacklist <add/remover> <ip>"));
                        return;
                    }
                    player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot blacklist <carregar/salvar/add/remover>"));
                    return;
                }

                case "whitelist": {
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("salvar")) {
                            whitelistModule.save(configUtil);
                            player.sendMessage(TextComponent.fromLegacyText("§aA whitelist foi salva com sucesso!"));
                            return;
                        }

                        if (args[1].equalsIgnoreCase("carregar")) {
                            whitelistModule.load(configUtil);
                            player.sendMessage(TextComponent.fromLegacyText("§aA whitelist foi carregada com sucesso!"));
                            return;
                        }
                        player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot whitelist <load/save>"));
                        return;
                    }
                    if (args.length == 3) {
                        String ip = args[2];
                        Matcher matcher = ipPattern.matcher(ip);

                        if (args[1].equalsIgnoreCase("add")) {
                            if (matcher.matches()) {
                                if (!whitelistModule.isWhitelisted(ip)) {
                                    blacklistModule.setBlacklisted(ip, false);
                                    whitelistModule.setWhitelisted(ip, true);
                                    player.sendMessage(TextComponent.fromLegacyText("§aO ip §f" + ip + "§a foi adcionado a whitelist!"));
                                    return;
                                }
                                player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c já existe na whitelist!"));
                                return;
                            }
                            player.sendMessage(TextComponent.fromLegacyText("§cO ip informado é invalido!"));
                            return;
                        }
                        if (args[1].equalsIgnoreCase("remover")) {
                            if (matcher.matches()) {
                                if (whitelistModule.isWhitelisted(ip)) {
                                    whitelistModule.setWhitelisted(ip, false);
                                    player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c foi removido da whitelist!"));
                                    return;
                                }
                                player.sendMessage(TextComponent.fromLegacyText("§cO ip §f" + ip + "§c não existe na whitelist!"));
                                return;
                            }
                            player.sendMessage(TextComponent.fromLegacyText("§cO ip informado é invalido!"));
                            return;
                        }
                        player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot whitelist <add/remover> <ip>"));
                        return;
                    }
                    player.sendMessage(TextComponent.fromLegacyText("§cUtilize /antibot whitelist <carregar/salvar/add/remover>"));
                    return;
                }
            }
        }
        player.sendMessage(TextComponent.fromLegacyText(""));
        player.sendMessage(TextComponent.fromLegacyText(" §c/antibot notificar §7- mostra a barra de notificação!"));
        player.sendMessage(TextComponent.fromLegacyText(" §c/antibot reload §7- recarrega os modulos e arquivos!"));
        player.sendMessage(TextComponent.fromLegacyText(" §c/antibot status §7- mostra o status atual do antibot!"));
        player.sendMessage(TextComponent.fromLegacyText(" §c/antibot blacklist §7- gerenciar a blacklist do antibot!"));
        player.sendMessage(TextComponent.fromLegacyText(" §c/antibot whitelist §7- gerenciar a whitelist do antibot!"));
        player.sendMessage(TextComponent.fromLegacyText(""));
    }
}