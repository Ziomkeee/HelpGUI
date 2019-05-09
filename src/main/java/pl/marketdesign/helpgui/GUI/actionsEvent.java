package pl.marketdesign.helpgui.GUI;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import pl.marketdesign.helpgui.util;

import java.util.Map;

import static pl.marketdesign.helpgui.help.cfg;

public class actionsEvent implements Listener {

    @EventHandler
    public void getActions(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        //MainGUI
        if(e.getInventory().equals(loadGUI.mainInv)) {
            e.setCancelled(true);
            if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.DOUBLE_CLICK || e.getClick() == ClickType.DROP) {
                e.setCancelled(true);
                p.closeInventory();
                p.updateInventory();
            }
            for(String slot : cfg.getMainGUI().getConfigurationSection("GUI.items").getKeys(false)) {
                ConfigurationSection config = cfg.getMainGUI().getConfigurationSection("GUI.items."+slot);
                if(e.getSlot() == Integer.parseInt(slot)) {
                    if(config.getString("action").equalsIgnoreCase("none")) {
                        return;
                    }
                    String action = "actions." + config.getString("action");
                    getAction(p, action);
                }
            }
        }

        //OthersGUI
        for(Map.Entry<String, Inventory> inv : loadGUI.otherGUI.entrySet()) {
            if(e.getInventory().equals(inv.getValue())) {
                e.setCancelled(true);
                if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.DOUBLE_CLICK || e.getClick() == ClickType.DROP) {
                    e.setCancelled(true);
                    p.closeInventory();
                    p.updateInventory();
                }
                for(String slot : cfg.getOtherGUI().getConfigurationSection("GUI."+inv.getKey()+".items").getKeys(false)) {
                    ConfigurationSection config = cfg.getOtherGUI().getConfigurationSection("GUI."+inv.getKey()+".items."+slot);
                    if(e.getSlot() == Integer.parseInt(slot)) {
                        if(config.getString("action").equalsIgnoreCase("none")) {
                            return;
                        }
                        String action = "actions." + config.getString("action");
                        getAction(p, action);
                    }
                }
            }
        }
    }

    private void getAction(Player p, String action ) {
        if(cfg.getConfig().isSet(action)) {
            if(p.hasPermission(cfg.getConfig().getString( action + ".permission"))) {
                for(String execute : cfg.getConfig().getStringList(action + ".execute")) {
                    if(execute.contains("%open%")) {
                        //Open new Inventory
                        String GUI = execute.replace("%open%", "");
                        if(loadGUI.otherGUI.get(GUI) != null) {
                            p.openInventory(loadGUI.otherGUI.get(GUI));
                        } else {
                            p.sendMessage(util.lang.get("prefix") + util.lang.get("notexist"));
                        }
                    } else if(execute.contains("%cmd-console%")) {
                        //Execute command by console
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), execute.replace("%cmd-console%", "").replace("%player%", p.getName()));
                    } else if(execute.contains("%cmd-player%")) {
                        //Execute command by player
                        Bukkit.dispatchCommand(p, execute.replace("%cmd-player%", "").replace("%player%", p.getName()));
                    } else if(execute.contains("%send%")) {
                        //Send message to player
                        p.sendMessage(util.getColor(execute.replace("%send%", "").replace("%player%", p.getName())));
                    }
                }
            } else {
                p.sendMessage(util.lang.get("prefix") + util.lang.get("noperm"));
            }
        } else {
            p.sendMessage(util.lang.get("prefix") + util.lang.get("actionnotexist"));
        }
    }
}
