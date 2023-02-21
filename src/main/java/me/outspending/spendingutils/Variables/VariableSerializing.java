package me.outspending.spendingutils.Variables;

import me.outspending.spendingutils.Variables.Exceptions.DuplicateSerializationException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VariableSerializing {

    public static Map<String, Serialization> classes = new HashMap<>();

    public static void register(Serialization serialization) throws DuplicateSerializationException {
        if (classes.containsKey(serialization.getClazz().getName())) throw new DuplicateSerializationException(serialization.getClazz());
        classes.put(serialization.getClazz().getName(), serialization);
    }

    public static void registerPackage(String packageName, String subPackage) throws DuplicateSerializationException {
        Package pkg = Package.getPackage(packageName + "." + subPackage);
        if (pkg == null) return;
        Reflections reflections = new Reflections(packageName);
        for (Class<?> clazz : reflections.getSubTypesOf(Serialization.class)) {
            try {
                register((Serialization) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static String serializePlayerVariable(PlayerVariable<?> variable) {
        Serialization serialization = classes.get(variable.getValue().getClass().getName());
        String value = serialization.serialize(variable.getValue());
        StringBuilder builder = new StringBuilder();
        builder.append("playerVariable{")
                .append(variable.getPlayer().getName())
                .append("||")
                .append(variable.getName())
                .append("||")
                .append(variable.getValue().getClass().getName())
                .append("||")
                .append(value)
                .append("}");
        return builder.toString();
    }

    public static String serializeServerVariable(ServerVariable<?> variable) {
        Serialization serialization = classes.get(variable.getValue().getClass().getName());
        String value = serialization.serialize(variable.getValue());
        StringBuilder builder = new StringBuilder();
        builder.append("serverVariable{")
                .append(variable.getName())
                .append("||")
                .append(variable.getValue().getClass().getName())
                .append("||")
                .append(value)
                .append("}");
        return builder.toString();
    }

    public static Variable deserializeVariable(String str) {
        Serialization serialization;
        String name, value;
        int start, end;

        switch (str.charAt(0)) {
            case 'p' -> {
                start = str.indexOf('{') + 1;
                end = str.indexOf("||");
                String playerUUID = str.substring(start, end);
                Player player = Bukkit.getPlayer(playerUUID);
                if (player == null || !player.isOnline()) return null;
                name = str.substring(end + 2, str.indexOf("||", end + 2));
                value = str.substring(str.lastIndexOf("||") + 2, str.length() - 1);
                serialization = classes.get(value);
                if (serialization == null) return null;
                return new PlayerVariable<>(name, player, serialization.deserialize(value));
            }
            case 's' -> {
                start = str.indexOf("{") + 1;
                end = str.indexOf("||");
                name = str.substring(end + 2, str.lastIndexOf("||"));
                value = str.substring(str.lastIndexOf("||") + 2, str.length() - 1);
                serialization = classes.get(value);
                if (serialization == null) return null;
                return new ServerVariable<>(name, serialization.deserialize(value));
            }
            default -> {
                return null;
            }
        }
    }


}
