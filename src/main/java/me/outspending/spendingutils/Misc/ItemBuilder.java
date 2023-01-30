package me.outspending.spendingutils.Misc;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private Material material;
    private String name;
    private String[] lores;
    private ItemFlag[] flags;
    private boolean unbreakable = false;
    private int amount = 1;

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLores(String... lores) {
        this.lores = lores;
        return this;
    }

    public ItemBuilder setItemFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.setDisplayName(name);
        if (lores != null) meta.setLore(Arrays.asList(lores));
        if (flags != null) meta.addItemFlags(flags);
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return item;
    }
}
