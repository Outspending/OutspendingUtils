package me.outspending.spendingutils.GUI;

import org.bukkit.inventory.ItemStack;

public class GUISlot {

    private final int slot;
    private final SpendingGUI gui;
    private final GUIItem item;

    public GUISlot(int slot, SpendingGUI gui, ItemStack item) {
        this.slot = slot;
        this.gui = gui;
        this.item = new GUIItem(item);
    }

    public int getSlot() {
        return slot;
    }

    public SpendingGUI getGUI() {
        return gui;
    }

    public GUIItem getItem() {
        return item;
    }

    public ItemStack getItemStack() {
        return item.getItem();
    }
}
