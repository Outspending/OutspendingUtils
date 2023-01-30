package me.outspending.spendingutils.Threading;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Thread {

    private Consumer<Runnable> task;
    private long startTime;
    private long endTime;
    private long duration;

    public Thread(Consumer<Runnable> runnable) {
        this.task = runnable;
        this.startTime = System.currentTimeMillis();
    }

    public void end() {
        this.endTime = System.currentTimeMillis();
        this.duration = this.endTime - this.startTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public Consumer<Runnable> getTask() {
        return this.task;
    }
}
