package me.outspending.spendingutils.GUI.Events;

import me.outspending.spendingutils.GUI.SpendingGUI;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpendingGUIClickEvent extends Event implements Cancellable {

    private SpendingGUI gui;
    private HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled = false;

    public SpendingGUIClickEvent(SpendingGUI gui) {
        this.gui = gui;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public SpendingGUI getGUI() {
        return gui;
    }
}
