package me.outspending.spendingutils.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Supplier;

public class GUIItem {

    private ItemStack item;

    public GUIItem(Material material) {
        new GUIItem(new ItemStack(material));
    }

    public GUIItem(Material material, String name) {
        new GUIItem(material, name, null);
    }

    public GUIItem(Material material, String name, String... lores) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        if (lores != null) {
            item.setLore(Arrays.asList(lores));
        }
        this.item = item;
    }

    public static GUIItem construct(Material material) {
        return GUIItem.construct(material, "");
    }

    public static GUIItem construct(Material material, String name) {
        return GUIItem.construct(material, name, null);
    }

    public static GUIItem construct(Material material, String name, String... lores) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        if (lores != null) meta.setLore(Arrays.asList(lores));
        itemStack.setItemMeta(meta);
        return new GUIItem(itemStack);
    }

    public GUIItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
