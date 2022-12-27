package me.outspending.spendingutils.API.Storage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerVariable<T> implements Variable, VariableData {

    private T value;
    private String name;
    private UUID player;

    public PlayerVariable(Player player, String name, T value) {
        this.player = player.getUniqueId();
        this.name = name;
        this.value = value;
        variables.put(name, this);
    }

    public PlayerVariable(UUID uuid, String name, T value) {
        this.player = uuid;
        this.name = name;
        this.value = value;
        variables.put(name, this);
    }

    public void setValue(T value) {
        ((PlayerVariable<T>) variables.get(name)).value = value;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public double getValueAsDouble() {
        return Double.parseDouble(value.toString());
    }

    public int getValueAsInt() {
        return Integer.parseInt(value.toString());
    }

    public String getValueAsString() {
        return value.toString();
    }

    public boolean getValueAsBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    public float getValueAsFloat() {
        return Float.parseFloat(value.toString());
    }

    public long getValueAsLong() {
        return Long.parseLong(value.toString());
    }

    public short getValueAsShort() {
        return Short.parseShort(value.toString());
    }

    public byte getValueAsByte() {
        return Byte.parseByte(value.toString());
    }

    public char getValueAsChar() {
        return value.toString().charAt(0);
    }

    public String getName() {
        return name;
    }

    public UUID getPlayer() {
        return player;
    }

    public Player getPlayerAsPlayer() {
        return Bukkit.getPlayer(player);
    }


    // Public methods

    public static PlayerVariable<Object> createVariable(Player player, String name, Object value) {
        return new PlayerVariable<>(player, name, value);
    }

    public static PlayerVariable<Object> createOrGetVariable(Player player, String name, Object value) {
        if (variables.containsKey(name)) {
            return (PlayerVariable<Object>) variables.get(name);
        }
        return new PlayerVariable<>(player, name, value);
    }

    public static PlayerVariable<Object> createOrGetVariable(UUID uuid, String name, Object value) {
        if (variables.containsKey(name)) {
            return (PlayerVariable<Object>) variables.get(name);
        }
        return new PlayerVariable<>(uuid, name, value);

    }

    public static PlayerVariable<Object> createVariable(UUID uuid, String name, Object value) {
        return new PlayerVariable<>(uuid, name, value);
    }

    public static PlayerVariable<?> getVariable(String name) {
        return (PlayerVariable<?>) variables.get(name);
    }
}
