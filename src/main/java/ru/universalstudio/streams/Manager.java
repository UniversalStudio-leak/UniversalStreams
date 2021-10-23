package ru.universalstudio.streams;

import java.math.*;
import java.text.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.configuration.file.*;
import net.milkbowl.vault.permission.*;
import ru.universalstudio.streams.common.*;
import ru.universalstudio.streams.commands.*;
import ru.universalstudio.streams.inventory.*;
import ru.universalstudio.streams.configuration.*;
import ru.universalstudio.streams.inventory.common.EventListener;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class Manager extends JavaPlugin {

    private static Manager instance;
    private ConfigurationGeneration config;
    private Permission permission;
    private MySQL sql;
    private InventoryManager inv;

    public InventoryManager getInv() {
        return this.inv;
    }

    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[UniversalStreams] Plugin recompiled and cracked by NaulbiMIX | Sponsored by FlatiCommunity (https://t.me/flaticommunity) | Specially publication for https://teletype.in/@naulbimix/rumine"); // да и кстати на деле если чё, то сурсы писал я сам, идею брал у универсалов. а по закону идею пиздить не запрещёно, поэтому у меня авторское право на это говно :)
        this.config = new ConfigurationGeneration(this, "config.yml");
        ConfigurationGeneration configuration = new ConfigurationGeneration(this, "config.yml");
        FileConfiguration fileConfiguration = configuration.yamlLoad();
      /*  if (fileConfiguration.getString("KEY") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[UniversalStreams] You are not licensed! You can buy it on our website: u-studio.su");
            Bukkit.getServer().shutdown();
        } else { */
            this.sql = new MySQL(this.getConfig().getString("MySQL.HOSTNAME"), this.getConfig().getString("MySQL.USERNAME"), this.getConfig().getString("MySQL.PASSWORD"), this.getConfig().getString("MySQL.DB"), this.getConfig().getString("MySQL.PORT"));
            if ((new UGuard("лицензию спиздили", "и крякнули", this)).register()) {
                this.inv = new InventoryManager();
                Bukkit.getPluginManager().registerEvents(new EventListener(), this);
                this.getCommand("streams").setExecutor(new StreamsCommand());
                this.getCommand("stream").setExecutor(new StreamCommand());
                this.permission = hookPermissions();
                Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                    @Override
                    public void run() {
                        int countStreams = StreamManager.getStreams().size();
                        broadcast(InventoryManager.getTextWithCommand(msg("BROADCAST_MESSAGE_STREAM").replace("{count}", countStreams + " " + Manager.format(countStreams, "трансляция", "трансляции", "трасляций")), msg("BROADCAST_LIVE_MESSAGE"), "/streams"));
                    }
                }, 0L, 12000L);
            }
     //   }
    }

    public MySQL getSql() {
        return this.sql;
    }

    public static String format(long var0, String var2, String var3, String var4) {
        if (var0 % 100L > 10L && var0 % 100L < 15L) {
            return var4;
        } else {
            switch((int)(var0 % 10L)) {
                case 1:
                    return var2;
                case 2:
                case 3:
                case 4:
                    return var3;
                default:
                    return var4;
            }
        }
    }

    public String number(BigInteger bigInteger) {
        return NumberFormat.getInstance(Locale.US).format(bigInteger);
    }

    public static Manager getInstance() {
        return instance;
    }

    public void broadcast(BaseComponent baseComponent) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(baseComponent);
        }
    }

    private static Permission hookPermissions() {
        Permission permission = null;
        RegisteredServiceProvider registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Permission.class);
        permission = (Permission)registeredServiceProvider.getProvider();
        if (permission.getName().equals("SuperPerms")) {
            return null;
        }
        return permission;
    }

    public String getGroup(String group) {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("PREFIX_GROUPS." + this.permission.getPrimaryGroup("", group), "&r"));
    }

    public FileConfiguration getConfig() {
        return this.config.yaml();
    }

    public String msg(String s) {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(s));
    }
}
