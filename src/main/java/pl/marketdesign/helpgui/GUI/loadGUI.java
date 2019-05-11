package pl.marketdesign.helpgui.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.marketdesign.helpgui.help;
import pl.marketdesign.helpgui.util;

import java.util.ArrayList;
import java.util.HashMap;

import static pl.marketdesign.helpgui.help.cfg;

public class loadGUI {

    public static HashMap<String, Inventory> listGUI = new HashMap<String, Inventory>();

    //Load items for MainGUI
    public static void loadMainGUI() {
        Inventory mainInv = Bukkit.createInventory(null, cfg.getMainGUI().getInt("GUI.size"), util.getColor(cfg.getMainGUI().getString("GUI.name")));
        for(String slot : cfg.getMainGUI().getConfigurationSection("GUI.items").getKeys(false)) {
            ConfigurationSection config = cfg.getMainGUI().getConfigurationSection("GUI.items."+slot);
            String material = config.getString("material").toUpperCase();
            ItemStack item = new ItemStack(Material.getMaterial(material), config.getInt("amount"));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(util.getColor(config.getString("name")));
            ArrayList<String> lore = new ArrayList<String>();
            for(String s : config.getStringList("lore")) {
                lore.add(util.getColor(s));
            }
            meta.setLore(lore);
            if(config.getBoolean("enchant") == true) {
                meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            item.setItemMeta(meta);
            mainInv.setItem(Integer.parseInt(slot), item);
        }
        listGUI.put("mainGUI", mainInv);
        System.out.println(help.prefix + cfg.getConfig().getString("MSG.load").replace("%name%", "MainGUI"));
    }

    //Load GUI and items for otherGUI
    public static void loadOtherGUI() {
        for(String name : cfg.getOtherGUI().getConfigurationSection("GUI").getKeys(false)) {
            ConfigurationSection config = cfg.getOtherGUI().getConfigurationSection("GUI."+name);
            Inventory inv = Bukkit.createInventory(null, config.getInt("size"), util.getColor(config.getString("name"))+"§0");
            for(String slot : config.getConfigurationSection("items").getKeys(false)) {
                ConfigurationSection config2 = config.getConfigurationSection("items."+slot);

                String material = config2.getString("material").toUpperCase();
                ItemStack item = new ItemStack(Material.getMaterial(material), config2.getInt("amount"));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(util.getColor(config2.getString("name")));
                ArrayList<String> lore = new ArrayList<String>();
                for(String s : config2.getStringList("lore")) {
                    lore.add(util.getColor(s));
                }
                meta.setLore(lore);
                if(config2.getBoolean("enchant") == true) {
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                item.setItemMeta(meta);
                inv.setItem(Integer.parseInt(slot), item);
            }
            listGUI.put(name, inv);
            System.out.println(help.prefix + cfg.getConfig().getString("MSG.load").replace("%name%", name));
        }
    }
}
