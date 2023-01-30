package me.outspending.spendingutils.GUI;

import me.outspending.spendingutils.GUI.Events.SpendingGUIClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GUIEvents implements Listener, GUIStorage {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (GUIManager.isSpendingGUI(e.getClickedInventory())) {
                e.setCancelled(true);
                SpendingGUI gui = GUIManager.getSpendingGUI(e.getClickedInventory());
                Consumer<Player> action = gui.getSlotAction(e.getSlot());
                Bukkit.getPluginManager().callEvent(new SpendingGUIClickEvent(gui));
                if (action != null) action.accept((Player) e.getWhoClicked());
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player player) {
            if (GUIManager.isSpendingGUI(e.getInventory())) {
                SpendingGUI gui = GUIManager.getSpendingGUI(e.getInventory());
                guis.remove(gui.getName());
                Consumer<Player> closeAction = gui.getCloseAction();
                Bukkit.getPluginManager().callEvent(new SpendingGUIClickEvent(gui));
                if (closeAction != null) closeAction.accept(player);
            }
        }
    }
}
