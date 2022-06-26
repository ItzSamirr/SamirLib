package it.itzsamirr.samirlib.event.database;

import it.itzsamirr.samirlib.database.Database;
import it.itzsamirr.samirlib.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 26.06.2022
 **/
public class DatabaseSetupEvent<T extends JavaPlugin> extends DatabaseEvent<T> implements Cancellable {
    private boolean cancelled = false;

    public DatabaseSetupEvent(Database<T> database) {
        super(database);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
