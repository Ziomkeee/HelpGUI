package pl.marketdesign.helpgui;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class config {

    private help plugin = help.getPlugin(help.class);

    public FileConfiguration mainGUI, otherGUI, config;
    public File mainGUIFile, otherGUIFile, configFile;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        mainGUIFile = new File(plugin.getDataFolder(), "maingui.yml");
        otherGUIFile = new File(plugin.getDataFolder(), "othergui.yml");
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!mainGUIFile.exists()) {
            mainGUIFile.getParentFile().mkdirs();
            plugin.saveResource("maingui.yml", false);
        }
        if (!otherGUIFile.exists()) {
            otherGUIFile.getParentFile().mkdirs();
            plugin.saveResource("othergui.yml", false);
        }
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        mainGUI = YamlConfiguration.loadConfiguration(mainGUIFile);
        otherGUI = YamlConfiguration.loadConfiguration(otherGUIFile);
    }

    public FileConfiguration getMainGUI() {
        return mainGUI;
    }

    public FileConfiguration getOtherGUI() { return otherGUI; }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfiguration() {
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        this.mainGUI = YamlConfiguration.loadConfiguration(this.mainGUIFile);
        this.otherGUI = YamlConfiguration.loadConfiguration(this.otherGUIFile);
    }
}