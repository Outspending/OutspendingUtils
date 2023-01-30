package me.outspending.spendingutils.Variables;

import org.bukkit.entity.Player;

public class PlayerVariable<T> implements Variable {

    private final String name;
    private final Player player;
    private T value;

    public PlayerVariable(String name, Player player, T value) {
        this.name = name;
        this.player = player;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public T getValue() {
        return value;
    }
}
