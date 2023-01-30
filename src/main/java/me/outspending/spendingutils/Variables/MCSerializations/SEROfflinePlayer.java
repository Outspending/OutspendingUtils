package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SEROfflinePlayer implements Serialization<OfflinePlayer> {

    private Class<?> offlinePlayer = OfflinePlayer.class;

    @Override
    public Class<?> getClazz() {
        return offlinePlayer;
    }

    @Override
    public String serialize(OfflinePlayer value) {
        return value.getName();
    }

    @Override
    public OfflinePlayer deserialize(String string) {
        return Bukkit.getOfflinePlayer(string);
    }
}
