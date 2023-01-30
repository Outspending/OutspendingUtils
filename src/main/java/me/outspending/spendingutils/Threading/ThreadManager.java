package me.outspending.spendingutils.Threading;

import me.outspending.spendingutils.Threading.Exceptions.UnstableThreadException;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Future;

public interface ThreadManager {

    Future<Void> runTask(ThreadOptions options, Plugin plugin, RunThread thread) throws UnstableThreadException;

    Future<Void> runTaskLater(ThreadOptions options, Plugin plugin, RunThread thread, long delay) throws UnstableThreadException;

    Future<Void> runTaskTimer(ThreadOptions options, Plugin plugin, RunThread thread, long delay, long period) throws UnstableThreadException;

    Future<?> startRepeatingTask(ThreadOptions options, int intervalMills, Plugin plugin, RunThread thread) throws UnstableThreadException;

    Future<?> runTaskLater(ThreadOptions options, int intervalMills, Plugin plugin, RunThread thread) throws UnstableThreadException;
}
