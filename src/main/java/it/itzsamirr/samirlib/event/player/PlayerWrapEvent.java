package it.itzsamirr.samirlib.event.player;

import it.itzsamirr.samirlib.event.Cancellable;
import it.itzsamirr.samirlib.player.PlayerWrapper;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 23.06.2022
 **/
public class PlayerWrapEvent<T extends JavaPlugin> extends PlayerEvent<T> implements Cancellable {
    private boolean cancelled = false;
    public PlayerWrapEvent(PlayerWrapper<T> playerWrapper) {
        super(playerWrapper);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
