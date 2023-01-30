package me.outspending.spendingutils.Threading;

import java.util.function.Consumer;

public class RunThread implements Runnable {

    private Consumer<Object> task;

    public RunThread(Consumer<Object> task) {
        this.task = task;
    }

    @Override
    public void run() {
        this.task.accept(task);
    }
}
