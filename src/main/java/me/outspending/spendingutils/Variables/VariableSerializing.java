package me.outspending.spendingutils.Variables;

import me.outspending.spendingutils.Variables.Exceptions.DuplicateSerializationException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class VariableSerializing {

    public static Map<String, Serialization> classes = new HashMap<>();

    public static void register(Serialization serialization) throws DuplicateSerializationException {
        if (classes.containsKey(serialization.getClazz().getName())) throw new DuplicateSerializationException(serialization.getClazz());
        classes.put(serialization.getClazz().getName(), serialization);
    }

    public static String serializePlayerVariable(PlayerVariable<?> variable) {
        Serialization serialization = classes.get(variable.getValue().getClass().getName());
        String value = serialization.serialize(variable.getValue());
        StringBuilder builder = new StringBuilder();
        builder.append("playerVariable{").append(variable.getPlayer().getName()).append("||").append(variable.getName()).append("||").append(variable.getValue().getClass().getName()).append("||").append(value).append("}");
        return builder.toString();
    }

    public static String serializeServerVariable(ServerVariable<?> variable) {
        Serialization serialization = classes.get(variable.getValue().getClass().getName());
        String value = serialization.serialize(variable.getValue());
        StringBuilder builder = new StringBuilder();
        builder.append("serverVariable{").append(variable.getName()).append("||").append(variable.getValue().getClass().getName()).append("||").append(value).append("}");
        return builder.toString();
    }

    public static Variable deserializeVariable(String str) {
        if (str.startsWith("playerVariable{")) {
            String replace = str.substring(15, str.length() - 1).replace("||", "@@");
            String[] split = replace.split("@@");
            Player plr = Bukkit.getPlayer(split[0]);
            Serialization serialization = classes.get(split[2]);
            if (!plr.isOnline() || serialization == null) return null;
            var thing = serialization.deserialize(split[3]);
            return new PlayerVariable<>(split[1], plr, thing);
        } else if (str.startsWith("serverVariable{")) {
            String replace = str.substring(15, str.length() - 1).replace("||", "@@");
            String[] split = replace.split("@@");
            Serialization serialization = classes.get(split[1]);
            if (serialization == null) return null;
            var thing = serialization.deserialize(split[2]);
            return new ServerVariable<>(split[0], thing);
        }

        return null;
    }


}
