package it.itzsamirr.samirlib.event;

/**
 * @author ItzSamirr
 *
 * <p> Created at 22.06.2022<p/>
 *
 * <p>Called when every tick asynchronously from the main trhread<p/>
 *
 * <p><STRONG>Is preferred to not use Bukkit nor Spigot api when listening for this event</STRONG></p>
 *
 **/
public final class AsyncTickEvent implements Event, Cancellable{
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
