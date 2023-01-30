package me.outspending.spendingutils.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpendingGUI implements GUIStorage {

    private String name;
    private List<Player> players = new ArrayList<>();
    private int rows;
    private Inventory inventory;
    private Map<Integer, Consumer<Player>> slots = new HashMap<>();
    private Consumer<Player> closeAction;

    public SpendingGUI(String name) {
        this(name, 3);
    }

    public SpendingGUI(String name, int rows) {
        this.name = name;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, (rows * 9), name);
        guis.put(inventory, this);
    }

    public void addPlayer(Player player) {
        guis.get(inventory).players.add(player);
        player.openInventory(inventory);
    }

    public void removePlayer(Player player) {
        guis.get(inventory).players.remove(player);
    }

    public void setCloseAction(Consumer<Player> closeAction) {
        guis.get(inventory).closeAction = closeAction;
    }

    public void createGUIItem(GUISlot slot, Consumer<Player> action) {
        slots.put(slot.getSlot(), action);
        inventory.setItem(slot.getSlot(), slot.getItemStack());
    }

    public void createGUIItem(GUISlot slot) {
        slots.put(slot.getSlot(), null);
        inventory.setItem(slot.getSlot(), slot.getItemStack());
    }

    public GUIItem getSlot(int slot) {
        return new GUIItem(inventory.getItem(slot));
    }

    public int getCenter() {
        return Math.round((rows * 9) / 2);
    }

    public int getLeftOfRow(int row) {
        return row * 9;
    }

    public int getRightOfRow(int row) {
        return (row * 9) + 8;
    }

    public void setRow(GUIItem item, int row) {
        if (Math.floor(row / 9) > rows) return;
        int num = row * 9;
        for (int i = num; i < num + 9; i++) {
            inventory.setItem(i, item.getItem());
        }
    }

    public void setAllSlots(GUIItem item) {
        for (int i = 0; i < rows * 9; i++) {
            inventory.setItem(i, item.getItem());
        }
    }

    public void addRow(int amount) {
        if (rows + amount > 6) return;

        guis.get(inventory).rows = rows;
        guis.get(inventory).inventory = Bukkit.createInventory(null, (rows * 9), name);
        updateGUIForAllPlayers();
    }

    public void setBorder(GUIItem item) {
        for (int i = 0; i < rows * 9; i++) {
            if (i < 9 || i > (rows * 9) - 9) {
                inventory.setItem(i, item.getItem());
            } else if (i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, item.getItem());
            }
        }
    }

    public void updateGUIForAllPlayers() {
        Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            player.openInventory(inventory);
        }
    }

    public void updateGUIForPlayer(Player player) {
        player.openInventory(inventory);
    }

    public Consumer<Player> getSlotAction(int slot) {
        return slots.get(slot);
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getRows() {
        return rows;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, Consumer<Player>> getSlots() {
        return slots;
    }

    public Consumer<Player> getCloseAction() {
        return closeAction;
    }
}
