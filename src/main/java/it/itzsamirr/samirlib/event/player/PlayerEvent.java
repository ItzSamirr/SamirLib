package it.itzsamirr.samirlib.event.player;

import it.itzsamirr.samirlib.event.Event;
import it.itzsamirr.samirlib.player.PlayerWrapper;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 23.06.2022
 **/
public abstract class PlayerEvent<T extends JavaPlugin> extends Event {
    private PlayerWrapper<T> playerWrapper;
    private T plugin;

    public PlayerEvent(PlayerWrapper<T> playerWrapper) {
        this.playerWrapper = playerWrapper;
        this.plugin = playerWrapper.getPlugin();
    }

    public PlayerWrapper<T> getPlayerWrapper() {
        return playerWrapper;
    }

    public void setPlayerWrapper(PlayerWrapper<T> playerWrapper) {
        this.playerWrapper = playerWrapper;
    }

    public T getPlugin() {
        return plugin;
    }

    public void setPlugin(T plugin) {
        this.plugin = plugin;
    }
}
