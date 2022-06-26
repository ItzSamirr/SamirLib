package it.itzsamirr.samirlib.event;

/**
 * @author ItzSamirr
 * Created at 22.06.2022
 **/
public final class TickEvent implements Event, Cancellable{
    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
