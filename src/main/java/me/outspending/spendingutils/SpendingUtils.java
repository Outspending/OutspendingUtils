package me.outspending.spendingutils;

import me.outspending.spendingutils.GUI.GUIEvents;
import me.outspending.spendingutils.GUI.GUIItem;
import me.outspending.spendingutils.GUI.GUISlot;
import me.outspending.spendingutils.GUI.SpendingGUI;
import me.outspending.spendingutils.Variables.*;
import me.outspending.spendingutils.Variables.Exceptions.DuplicateSerializationException;
import me.outspending.spendingutils.Variables.MCSerializations.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class SpendingUtils {

    public static JavaPlugin plugin;

    public static void setup(JavaPlugin pl) {
        plugin = pl;
        Bukkit.getPluginManager().registerEvents(new GUIEvents(), plugin);

        try {
            VariableSerializing.register(new SERString());
            VariableSerializing.register(new SERLocation());
            VariableSerializing.register(new SERPlayer());
            VariableSerializing.register(new SEROfflinePlayer());
            VariableSerializing.register(new SERInteger());
            VariableSerializing.register(new SERDouble());
            VariableSerializing.register(new SERFloat());
            VariableSerializing.register(new SERBoolean());
            VariableSerializing.register(new SERLong());
        } catch (DuplicateSerializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
