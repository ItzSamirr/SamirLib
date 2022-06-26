package it.itzsamirr.samirlib.event.database;

import it.itzsamirr.samirlib.database.Database;
import it.itzsamirr.samirlib.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 26.06.2022
 **/
public class DatabaseEvent<T extends JavaPlugin> implements Event {
    private Database<T> database;

    public DatabaseEvent(Database<T> database) {
        this.database = database;
    }

    public Database<T> getDatabase() {
        return database;
    }

    public void setDatabase(Database<T> database) {
        this.database = database;
    }
}
