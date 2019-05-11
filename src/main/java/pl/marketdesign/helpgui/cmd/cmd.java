package pl.marketdesign.helpgui.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import pl.marketdesign.helpgui.GUI.loadGUI;

public class cmd extends BukkitCommand {

    public cmd(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player p = (Player) sender;
        p.openInventory(loadGUI.listGUI.get("mainGUI"));
        return false;
    }

}
