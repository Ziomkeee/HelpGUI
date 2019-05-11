package pl.marketdesign.helpgui.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.marketdesign.helpgui.GUI.loadGUI;
import pl.marketdesign.helpgui.util;

import static pl.marketdesign.helpgui.help.cfg;

public class helpguicmd implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("helpgui")) {
            if(sender.hasPermission("helpgui.reload")) {
                cfg.reloadConfiguration();
                loadGUI.listGUI.clear();
                loadGUI.loadMainGUI();
                loadGUI.loadOtherGUI();
                util.lang.clear();
                util.loadLang();
                sender.sendMessage(util.lang.get("prefix") + util.lang.get("reload"));
            }
        } else {
            sender.sendMessage(util.lang.get("prefix") + util.lang.get("noperm"));
        }
        return false;
    }
}
