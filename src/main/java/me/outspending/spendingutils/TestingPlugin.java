package me.outspending.spendingutils;

import me.outspending.spendingutils.API.Message.MiniMessage;
import me.outspending.spendingutils.API.Storage.PlayerVariable;
import me.outspending.spendingutils.API.Storage.VariableStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TestingPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        new VariableStorage(this, 20 * 60 * 5);
        for (Player plr : Bukkit.getOnlinePlayers()) {
            VariableStorage.loadAllPlayerVariables(plr, true);
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        MiniMessage.broadcastMessage("<rainbow>Testing this message!");
        Bukkit.broadcastMessage("Finished loading in " + (System.currentTimeMillis() - start) + "ms!");
    }

    @Override
    public void onDisable() {
        VariableStorage.saveAllVariables(false);

        var adventure = MiniMessage.adventure();
        if (adventure != null) {
            adventure.close();
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        PlayerVariable<Object> variable = PlayerVariable.createOrGetVariable(e.getPlayer(), "balance", 0);
        variable.setValue(variable.getValueAsDouble() + 1);
        Bukkit.broadcastMessage(variable.getValueAsString());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        VariableStorage.savePlayerVariables(e.getPlayer(), true);
        VariableStorage.unloadAllPlayerVariables(e.getPlayer());
        Bukkit.getLogger().log(Level.FINE, "Saved and unloaded variables for " + e.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        VariableStorage.loadAllPlayerVariables(e.getPlayer(), true);

        int amount = VariableStorage.amountOfPlayerVariables(e.getPlayer());
        Bukkit.getLogger().log(Level.FINE, "Successfully loaded " + amount + " variables for " + e.getPlayer().getName());
    }
}
