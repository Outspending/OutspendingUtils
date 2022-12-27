package me.outspending.spendingutils.API.Message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MiniMessage {

    private static BukkitAudiences adventure;

    public static @NotNull BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access adventure when the plugin is disabled!");
        }
        return adventure;
    }

    public MiniMessage(Plugin plugin) {
        adventure = BukkitAudiences.create(plugin);
    }

    public static void sendMessage(Player player, String message) {
        Audience audience = adventure().player(player);
        var mm = net.kyori.adventure.text.minimessage.MiniMessage.miniMessage();
        audience.sendMessage(mm.deserialize(message));
    }

    public static void broadcastMessage(String message) {
        Audience audience = adventure().all();
        var mm = net.kyori.adventure.text.minimessage.MiniMessage.miniMessage();
        audience.sendMessage(mm.deserialize(message));
    }

    public static void sendActionBar(Player player, String message) {
        Audience audience = adventure().player(player);
        var mm = net.kyori.adventure.text.minimessage.MiniMessage.miniMessage();
        audience.sendActionBar(mm.deserialize(message));
    }

    public static void broadcastActionBar(String message) {
        Audience audience = adventure().all();
        var mm = net.kyori.adventure.text.minimessage.MiniMessage.miniMessage();
        audience.sendActionBar(mm.deserialize(message));
    }
}
