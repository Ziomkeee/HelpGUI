package pl.marketdesign.helpgui;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.marketdesign.helpgui.GUI.actionsEvent;
import pl.marketdesign.helpgui.GUI.loadGUI;

import java.lang.reflect.Field;

public class help extends JavaPlugin {

    public static config cfg;
    public static String prefix = "[HelpGUI] ";

    public void onEnable() {
        loadConfig();

        //Load
        util.loadLang();
        loadGUI.loadMainGUI();
        loadGUI.loadOtherGUI();

        regCmd();
        getServer().getPluginManager().registerEvents(new actionsEvent(), this);

    }

    private void regCmd() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(cfg.getConfig().getString("cmd"), new cmd(cfg.getConfig().getString("cmd")));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        cfg = new config();
        cfg.setup();
    }


}
