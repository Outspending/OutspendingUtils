package me.outspending.spendingutils.API.Storage;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.outspending.spendingutils.API.Message.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VariableStorage implements VariableData {

    public static Plugin plugin;
    public static Json storage;

    public VariableStorage(Plugin plugin, long saveInterval) {
        this.plugin = plugin;
        this.storage = LightningBuilder
                .fromFile(new File(plugin.getDataFolder(), "variables.json"))
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .createJson();

        new MiniMessage(plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                saveAllVariables(true);
            }
        }.runTaskTimerAsynchronously(plugin, saveInterval, saveInterval);
    }

    // Saving
    public static void saveAllVariables(boolean async) {
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Variable variable : variables.values()) {
                        if (variable instanceof PlayerVariable<?> playerVariable) {
                            storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
                            continue;
                        }
                        ServerVariable<?> variable1 = (ServerVariable<?>) variable;
                        storage.set("storage.global." + variable1.getName(), variable1.getValue());
                    }
                }
            }.runTaskAsynchronously(plugin);
            return;
        }
        for (Variable variable : variables.values()) {
            if (variable instanceof PlayerVariable<?> playerVariable) {
                storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
                continue;
            }
            ServerVariable<?> variable1 = (ServerVariable<?>) variable;
            storage.set("storage.global." + variable1.getName(), variable1.getValue());
        }
    }

    public static void savePlayerVariables(Player player, boolean async) {
        List<Variable> variables1 = variables.values().stream().filter(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId())).toList();
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Variable variable : variables1) {
                        PlayerVariable<?> playerVariable = (PlayerVariable<?>) variable;
                        storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
                    }
                }
            }.runTaskAsynchronously(plugin);
            return;
        }
        for (Variable variable : variables1) {
            PlayerVariable<?> playerVariable = (PlayerVariable<?>) variable;
            storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
        }
    }

    public static void saveServerVariables(boolean async) {
        List<Variable> variables1 = variables.values().stream().filter(variable -> variable instanceof ServerVariable<?>).toList();
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Variable variable : variables1) {
                        ServerVariable<?> variable1 = (ServerVariable<?>) variable;
                        storage.set("storage.global." + variable1.getName(), variable1.getValue());
                    }
                }
            }.runTaskAsynchronously(plugin);
            return;
        }
        for (Variable variable : variables1) {
            ServerVariable<?> variable1 = (ServerVariable<?>) variable;
            storage.set("storage.global." + variable1.getName(), variable1.getValue());
        }
    }

    public static void saveVariable(String variableName) {
        Variable variable = variables.get(variableName);
        if (variable instanceof PlayerVariable<?> playerVariable) {
            storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
            return;
        }
        ServerVariable<?> variable1 = (ServerVariable<?>) variable;
        storage.set("storage.global." + variable1.getName(), variable1.getValue());
    }

    public static void saveVariable(Variable variable) {
        if (variable instanceof PlayerVariable<?> playerVariable) {
            storage.set("storage.players." + playerVariable.getPlayer() + "." + playerVariable.getName(), playerVariable.getValue());
            return;
        }
        ServerVariable<?> variable1 = (ServerVariable<?>) variable;
        storage.set("storage.global." + variable1.getName(), variable1.getValue());
    }

    // Loading
    public static void loadAllServerVariables(boolean async) {
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (String variable : storage.singleLayerKeySet("storage.global")) {
                        new ServerVariable<>(variable, storage.get("storage.global." + variable));
                    }
                }
            }.runTaskAsynchronously(plugin);
            return;
        }
        for (String variable : storage.singleLayerKeySet("storage.global")) {
            new ServerVariable<>(variable, storage.get("storage.global." + variable));
        }
    }

    public static void loadAllPlayerVariables(Player player, boolean async) {
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (String variable : storage.singleLayerKeySet("storage.players." + player.getUniqueId())) {
                        new PlayerVariable<>(player, variable, storage.get("storage.players." + player.getUniqueId() + "." + variable));
                    }
                }
            }.runTaskAsynchronously(plugin);
            return;
        }
        for (String variable : storage.singleLayerKeySet("storage.players." + player.getUniqueId())) {
            new PlayerVariable<>(player, variable, storage.get("storage.players." + player.getUniqueId() + "." + variable));
        }
    }

    public static ServerVariable<?> loadServerVariable(String variableName) {
        return new ServerVariable<>(variableName, storage.get("storage.global." + variableName));
    }

    public static PlayerVariable<?> loadPlayerVariable(Player player, String variableName) {
        return new PlayerVariable<>(player, variableName, storage.get("storage.players." + player.getUniqueId() + "." + variableName));
    }

    // Unloading
    public static void unloadAllVariables() {
        variables.clear();
    }

    public static void unloadAllServerVariables() {
        variables.values().removeIf(variable -> variable instanceof ServerVariable<?>);
    }

    public static void unloadAllPlayerVariables(Player player) {
        variables.values().removeIf(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()));
    }

    public static void unloadServerVariable(String variableName) {
        variables.values().removeIf(variable -> variable instanceof ServerVariable<?> variable1 && variable1.getName().equals(variableName));
    }

    public static void unloadPlayerVariable(Player player, String variableName) {
        variables.values().removeIf(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()) && playerVariable.getName().equals(variableName));
    }

    // Getting
    public static Variable getVariable(String variableName) {
        return variables.get(variableName);
    }

    public static ServerVariable<?> getServerVariable(String variableName) {
        return (ServerVariable<?>) variables.get(variableName);
    }

    public static PlayerVariable<?> getPlayerVariable(Player player, String variableName) {
        return (PlayerVariable<?>) variables.values().stream().filter(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()) && playerVariable.getName().equals(variableName)).findFirst().orElse(null);
    }

    // Creating
    public static ServerVariable<?> createServerVariable(String variableName, Object value) {
        return new ServerVariable<>(variableName, value);
    }

    public static PlayerVariable<?> createPlayerVariable(Player player, String variableName, Object value) {
        return new PlayerVariable<>(player, variableName, value);
    }

    // Deleting
    public static void deleteVariable(String variableName) {
        variables.remove(variableName);
    }

    public static void deleteServerVariable(String variableName) {
        variables.values().removeIf(variable -> variable instanceof ServerVariable<?> variable1 && variable1.getName().equals(variableName));
    }

    public static void deletePlayerVariable(Player player, String variableName) {
        variables.values().removeIf(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()) && playerVariable.getName().equals(variableName));
    }

    // Checking
    public static boolean hasVariable(String variableName) {
        return variables.containsKey(variableName);
    }

    public static boolean hasServerVariable(String variableName) {
        return variables.values().stream().anyMatch(variable -> variable instanceof ServerVariable<?> variable1 && variable1.getName().equals(variableName));
    }

    public static boolean hasPlayerVariable(Player player, String variableName) {
        return variables.values().stream().anyMatch(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()) && playerVariable.getName().equals(variableName));
    }

    // Getting all
    public static List<Variable> getAllVariables() {
        return new ArrayList<>(variables.values());
    }

    public static List<? extends ServerVariable<?>> getAllServerVariables() {
        return variables.values().stream().filter(variable -> variable instanceof ServerVariable<?>).map(variable -> (ServerVariable<?>) variable).toList();
    }

    public static List<? extends PlayerVariable<?>> getAllPlayerVariables(Player player) {
        return variables.values().stream().filter(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId())).map(variable -> (PlayerVariable<?>) variable).toList();
    }

    // Misc
    public static void clear() {
        variables.clear();
        storage.clear();
    }

    public static int amountOfPlayerVariables(Player plr) {
        return (int) variables.values().stream().filter(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(plr.getUniqueId())).count();
    }

    public static int amountOfServerVariables() {
        return (int) variables.values().stream().filter(variable -> variable instanceof ServerVariable<?>).count();
    }

    public static int amountOfVariables() {
        return variables.size();
    }

    public static List<PlayerVariable<Object>> playerVariableContains(Player player, String contains) {
        return variables.values().stream().filter(variable -> variable instanceof PlayerVariable<?> playerVariable && playerVariable.getPlayer().equals(player.getUniqueId()) && playerVariable.getName().contains(contains)).map(variable -> (PlayerVariable<Object>) variable).toList();
    }

    public static List<ServerVariable<Object>> serverVariableContains(String contains) {
        return variables.values().stream().filter(variable -> variable instanceof ServerVariable<?> variable1 && variable1.getName().contains(contains)).map(variable -> (ServerVariable<Object>) variable).toList();
    }
}
