package it.itzsamirr.samirlib.event;

/**
 * @author ItzSamirr
 * Created at 23.06.2022
 **/
public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancel);
}
