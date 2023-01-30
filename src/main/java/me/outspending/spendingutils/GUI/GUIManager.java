package me.outspending.spendingutils.GUI;

import org.bukkit.inventory.Inventory;

public class GUIManager implements GUIStorage {

    public static boolean isSpendingGUI(Inventory inv) {
        return guis.containsKey(inv);
    }

    public static SpendingGUI getSpendingGUI(Inventory inv) {
        return guis.get(inv);
    }
}
