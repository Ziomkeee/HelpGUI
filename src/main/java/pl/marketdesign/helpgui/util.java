package pl.marketdesign.helpgui;

import java.util.HashMap;

import static pl.marketdesign.helpgui.help.cfg;

public class util {

    public static HashMap<String, String> lang = new HashMap<String, String>();

    public static String getColor(String color) {
        String text = color.replace("&", "§");
        return text;
    }

    public static void loadLang() {
        lang.put("prefix", getColor(cfg.getConfig().getString("MSG.prefix")));
        lang.put("noperm", getColor(cfg.getConfig().getString("MSG.noperm")));
        lang.put("notexist", getColor(cfg.getConfig().getString("MSG.notexist")));
        lang.put("actionnotexist", getColor(cfg.getConfig().getString("MSG.actionnotexist")));
        lang.put("reload", getColor(cfg.getConfig().getString("MSG.reload")));
        lang.put("noitem", getColor(cfg.getConfig().getString("MSG.noitem")));
    }

}
