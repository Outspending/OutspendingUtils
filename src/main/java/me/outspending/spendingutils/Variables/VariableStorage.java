package me.outspending.spendingutils.Variables;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.outspending.spendingutils.SpendingUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class VariableStorage {

    public final static JavaPlugin plugin = SpendingUtils.getPlugin();
    public final static Json STORAGE_FILE;
    public final static Map<String, Variable> variables = new HashMap<>();

    static {
        STORAGE_FILE = LightningBuilder
                .fromFile(new File(plugin.getDataFolder(), "variables.json"))
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .setDataType(DataType.UNSORTED)
                .createJson();

        long saveInterval = 20 * 60 * 5;
        new BukkitRunnable() {
            @Override
            public void run() {
                saveAllVariables();
            }
        }.runTaskTimerAsynchronously(plugin, saveInterval, saveInterval);
    }

    public static void savePlayerVariable(PlayerVariable<?> variable) {
        String serialized = VariableSerializing.serializePlayerVariable(variable);
        STORAGE_FILE.getFileData().insert(variable.getPlayer().getUniqueId()+ "." + variable.getName(), serialized);
    }

    public static Collection<Variable> getAllVariables() {
        return variables.values();
    }

    public static Collection<PlayerVariable<?>> getPlayerVariables(Player player) {
        List<PlayerVariable<?>> variables1 = new ArrayList<>(10);
        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?> variable1 && variable1.getPlayer().equals(player)) {
                variables1.add(variable1);
            }
        }
        return variables1;
    }

    public static void saveAndUnloadPlayerVariables(Player player) {
        List<Variable> variablesToRemove = new ArrayList<>();
        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?> variable1 && variable1.getPlayer().equals(player)) {
                savePlayerVariable(variable1);
                variablesToRemove.add(variable1);
            }
        }
        variables.values().removeAll(variablesToRemove);
        STORAGE_FILE.set("reload", true);
    }

    public static Collection<ServerVariable> getServerVariables() {
        List<ServerVariable> serverVariables = new ArrayList<>();
        for (Variable variable : variables.values()) {
            if (variable instanceof ServerVariable<?> variable1) {
                serverVariables.add(variable1);
            }
        }
        return serverVariables;
    }

    public static PlayerVariable getPlayerVariable(Player player, String name) {
        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?> variable1 && variable1.getPlayer().equals(player) && variable1.getName().equals(name)) {
                return variable1;
            }
        }
        return null;
    }

    public static ServerVariable getServerVariable(String name) {
        for (Variable variable : variables.values()) {
            if (variable instanceof ServerVariable<?> variable1 && variable1.getName().equals(name)) {
                return variable1;
            }
        }
        return null;
    }

    public static void deleteVariable(Variable variable) {
        String key = null;
        if (variable instanceof PlayerVariable<?> variable1) {
            key = variable1.getPlayer().getUniqueId() + "." + variable1.getName();
        } else if (variable instanceof ServerVariable<?> variable1) {
            key = "server." + variable1.getName();
        }
        if (key != null) {
            STORAGE_FILE.getFileData().remove(key);
        }
        variables.remove(variable.getName());
    }

    public static void deleteVariable(String name) {
        Variable variable = variables.get(name);
        if (variable == null) return;
        deleteVariable(variable);
    }

    public static void saveServerVariable(ServerVariable<?> variable) {
        String serialized = VariableSerializing.serializeServerVariable(variable);
        STORAGE_FILE.getFileData().insert("server." + variable.getName(), serialized);
    }

    public static void saveAllVariables() {
        List<PlayerVariable<?>> playerVariables = new ArrayList<>();
        List<ServerVariable<?>> serverVariables = new ArrayList<>();

        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?>) {
                playerVariables.add((PlayerVariable<?>) variable);
            } else if (variable instanceof ServerVariable<?>) {
                serverVariables.add((ServerVariable<?>) variable);
            }
        }

        CompletableFuture<Void> savePlayerVariablesFuture = CompletableFuture.runAsync(() -> {
            for (PlayerVariable<?> variable : playerVariables) {
                savePlayerVariable(variable);
            }
        });

        CompletableFuture<Void> saveServerVariablesFuture = CompletableFuture.runAsync(() -> {
            for (ServerVariable<?> variable : serverVariables) {
                saveServerVariable(variable);
            }
        });

        CompletableFuture.allOf(savePlayerVariablesFuture, saveServerVariablesFuture).join();

        STORAGE_FILE.set("reload", true);
    }

    public static void loadPlayerVariables(Player player) {
        Set<String> map = STORAGE_FILE.singleLayerKeySet(player.getUniqueId().toString());
        if (map == null) return;
        for (String key : map) {
            String serialized = STORAGE_FILE.getString(player.getUniqueId() + "." + key);
            VariableSerializing.deserializeVariable(serialized);
        }
    }

    public static void loadServerVariables() {
        Set<String> map = STORAGE_FILE.singleLayerKeySet("server");
        if (map == null) return;
        for (String key : map) {
            String serialized = STORAGE_FILE.getString("server." + key);
            VariableSerializing.deserializeVariable(serialized);
        }
    }

    public static void loadAllVariables() {
        loadServerVariables();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            loadPlayerVariables(player);
        }
    }

    public static void unloadPlayerVariables(Player player) {
        List<Variable> unloadVariables = new ArrayList<>(10);
        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?> variable1 && variable1.getPlayer().equals(player)) {
                unloadVariables.add(variable1);
            }
        }
        variables.values().removeAll(unloadVariables);
    }

    public static void saveAllServerVariables() {
        for (Variable variable : variables.values()) {
            if (variable instanceof ServerVariable<?> variable1) {
                saveServerVariable(variable1);
            }
        }
        STORAGE_FILE.set("reload", true);
    }

    public static void unloadServerVariables() {
        for (Variable variable : variables.values()) {
            if (variable instanceof ServerVariable serverVariable) {
                variables.remove(serverVariable.getName());
            }
        }
    }

    public static void unloadAllVariables() {
        unloadServerVariables();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            unloadPlayerVariables(player);
        }
    }

    public static Map<String, Variable> getVariables() {
        return variables;
    }
}
