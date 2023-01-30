package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SERPlayer implements Serialization<Player> {

    private Class<?> player = Player.class;

    @Override
    public Class<?> getClazz() {
        return player;
    }

    @Override
    public String serialize(Player value) {
        return value.getName();
    }

    @Override
    public Player deserialize(String string) {
        return Bukkit.getPlayer(string);
    }
}
