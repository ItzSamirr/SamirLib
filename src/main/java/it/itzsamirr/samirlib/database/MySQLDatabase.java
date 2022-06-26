package it.itzsamirr.samirlib.database;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 24.06.2022
 **/
public final class MySQLDatabase<T extends JavaPlugin> extends Database<T>{
    public MySQLDatabase(T plugin, String user, String database, String host, int port, String password, String[] defaultTables, DefaultValue<?>[]... defaultValues) {
        super(plugin, "jdbc:mysql://"+ host +":" + port + "/" + database, "com.mysql.jdbc.Driver", user, password, defaultTables, defaultValues);
    }
}
