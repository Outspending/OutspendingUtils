package me.outspending.spendingutils.Threading;

import me.outspending.spendingutils.Threading.Exceptions.UnstableThreadException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.Future;

public class BetterThreading implements ThreadManager {

    public Deque<Thread> lastThreadReads = new ArrayDeque<>();
    public Thread lastThread = null;

    public static void addLastThread(Thread thread) {
        BetterThreading betterThreading = new BetterThreading();
        betterThreading.lastThread = thread;
        if (betterThreading.lastThreadReads.size() > 10) {
            betterThreading.lastThreadReads.removeLast();
        }
        betterThreading.lastThreadReads.addFirst(thread);
    }

    public static List<Thread> getLastThreads() {
        BetterThreading betterThreading = new BetterThreading();
        return new ArrayList<>(betterThreading.lastThreadReads);
    }

    @Override
    public Future<Void> runTask(ThreadOptions options, Plugin plugin, RunThread thread) throws UnstableThreadException {

        switch (options) {
            case MEASURED -> {
                Thread thread1 = new Thread((Runnable run) -> {
                    Bukkit.getScheduler().runTask(plugin, run);
                });
                thread1.getTask().accept(thread);
                thread1.end();
                addLastThread(thread1);
                return (Future<Void>) thread1;
            }
            case ASYNC_MEASURED -> {
                Thread thread1 = new Thread((Runnable run) -> {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, run);
                });
                thread1.getTask().accept(thread);
                thread1.end();
                addLastThread(thread1);
                return (Future<Void>) thread1;
            }
            case ASYNC -> {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, thread);
                return (Future<Void>) thread;
            }
            case NORMAL -> {
                Bukkit.getScheduler().runTask(plugin, thread);
                return (Future<Void>) thread;
            }
        }
        return null;
    }

    @Override
    public Future<Void> runTaskLater(ThreadOptions options, Plugin plugin, RunThread thread, long delay) throws UnstableThreadException {
        return null;
    }

    @Override
    public Future<Void> runTaskTimer(ThreadOptions options, Plugin plugin, RunThread thread, long delay, long period) throws UnstableThreadException {
        return null;
    }

    @Override
    public Future<?> startRepeatingTask(ThreadOptions options, int intervalMills, Plugin plugin, RunThread thread) throws UnstableThreadException {
        return null;
    }

    @Override
    public Future<?> runTaskLater(ThreadOptions options, int intervalMills, Plugin plugin, RunThread thread) throws UnstableThreadException {
        return null;
    }

    public Deque<Thread> getLastThreadReads() {
        return lastThreadReads;
    }

    public Thread getLastThread() {
        return lastThread;
    }
}
