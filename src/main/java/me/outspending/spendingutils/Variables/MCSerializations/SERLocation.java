package me.outspending.spendingutils.Variables.MCSerializations;

import me.outspending.spendingutils.Variables.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SERLocation implements Serialization<Location> {

    private Class<?> location = Location.class;

    @Override
    public Class<?> getClazz() {
        return location;
    }

    @Override
    public String serialize(Location value) {
        return value.getWorld().getName() + "," + value.getX() + "," + value.getY() + "," + value.getZ();
    }

    @Override
    public Location deserialize(String string) {
        String[] split = string.split(",");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }
}
