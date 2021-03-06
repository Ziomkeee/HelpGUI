package pl.marketdesign.helpgui.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.marketdesign.helpgui.util;

import java.util.Map;

import static pl.marketdesign.helpgui.help.cfg;

public class actionsEvent implements Listener {

    @EventHandler
    public void getActions(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        //MainGUI
        if(e.getInventory().equals(loadGUI.listGUI.get("mainGUI"))) {
            e.setCancelled(true);
            if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.DOUBLE_CLICK || e.getClick() == ClickType.DROP) {
                e.setCancelled(true);
                p.closeInventory();
                p.updateInventory();
            }
            for(String slot : cfg.getMainGUI().getConfigurationSection("GUI.items").getKeys(false)) {
                ConfigurationSection config = cfg.getMainGUI().getConfigurationSection("GUI.items."+slot);
                if(e.getSlot() == Integer.parseInt(slot)) {
                    //None
                    if(config.getString("action").equalsIgnoreCase("none")) {
                        return;
                    }
                    String action = "actions." + config.getString("action");
                    getAction(p, action);
                }
            }
        } else {
            //OthersGUI
            for(Map.Entry<String, Inventory> inv : loadGUI.listGUI.entrySet()) {
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
                            //None
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
    }

    private void getAction(Player p, String action ) {
        if(cfg.getConfig().isSet(action)) {
            if(p.hasPermission(cfg.getConfig().getString( action + ".permission"))) {
                for(String execute : cfg.getConfig().getStringList(action + ".execute")) {
                    if(execute.contains("%open%")) {
                        //Open new Inventory
                        String GUI = execute.replace("%open%", "");
                        if(loadGUI.listGUI.get(GUI) != null) {
                            p.openInventory(loadGUI.listGUI.get(GUI));
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
                    } else if(execute.contains("%give%")) {
                        String[] material = execute.replace("%give%", "").split(":");
                        String item = material[0].toUpperCase();
                        int amount = Integer.parseInt(material[1]);
                        ItemStack giveitem = new ItemStack(Material.getMaterial(item), amount);
                        p.getInventory().addItem(giveitem);
                    } else if(execute.contains("%remove%")) {
                        String[] material = execute.replace("%remove%", "").split(":");
                        String item = material[0].toUpperCase();
                        int amount = Integer.parseInt(material[1]);
                        if(p.getInventory().containsAtLeast(new ItemStack(Material.getMaterial(item), amount),amount)) {
                            p.getInventory().removeItem(new ItemStack(Material.getMaterial(item), amount));
                        } else {
                            p.sendMessage(util.lang.get("prefix") + util.lang.get("noitem"));
                            p.closeInventory();
                        }
                    } else if(execute.contains("%teleport%"))
                    {
                        String[] teleport = execute.replace("%teleport%", "").split(":");

                        Location newloc = new Location(Bukkit.getWorld(teleport[0]), Double.parseDouble(teleport[1]),
                                Double.parseDouble(teleport[2]), Double.parseDouble(teleport[3]));
                        p.teleport(newloc);
                    } else if(execute.contains("%close%")) {
                        p.closeInventory();
                    }
                }
            } else {
                p.sendMessage(util.lang.get("prefix") + util.lang.get("noperm"));
                p.closeInventory();
            }
        } else {
            p.sendMessage(util.lang.get("prefix") + util.lang.get("actionnotexist"));
            p.closeInventory();
        }
    }
}
