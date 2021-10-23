package ru.universalstudio.streams.configuration;

import java.io.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class ConfigurationGeneration implements ConfigurationImpl {

    private FileConfiguration config;
    private String fileName;
    private Plugin plugin;

    public ConfigurationGeneration(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.config = this.yamlLoad();
    }

    public FileConfiguration yaml() {
        return this.config;
    }

    public FileConfiguration yamlLoad() {
        File file = new File(this.plugin.getDataFolder(), this.fileName);
        InputStream inputStream = this.plugin.getResource(this.fileName);
        try {
            YamlConfiguration.loadConfiguration(file).save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            this.config.save(new File(this.plugin.getDataFolder(), this.fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfiguration() {
        this.config = this.yamlLoad();
    }
}
