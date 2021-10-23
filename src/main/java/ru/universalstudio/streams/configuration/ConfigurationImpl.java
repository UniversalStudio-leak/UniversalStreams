package ru.universalstudio.streams.configuration;

import org.bukkit.configuration.file.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public interface ConfigurationImpl {

    FileConfiguration yaml();
    FileConfiguration yamlLoad();

    void save();
    void reloadConfiguration();
}
