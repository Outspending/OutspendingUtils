package me.outspending.spendingutils.GUI;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public interface GUIStorage {

    Map<Inventory, SpendingGUI> guis = new HashMap<>();
}
