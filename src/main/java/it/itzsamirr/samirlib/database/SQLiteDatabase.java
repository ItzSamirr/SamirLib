package it.itzsamirr.samirlib.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author ItzSamirr
 * Created at 25.06.2022
 **/
public class SQLiteDatabase<T extends JavaPlugin> extends Database<T>{
    public SQLiteDatabase(T plugin, String database, String[] defaultTables, DefaultValue<?>[]... defaultValues) {
        super(plugin, "jdbc:sqlite:" + new File(plugin.getDataFolder(), database + ".db"), "org.sqlite.JDBC", null, null, defaultTables, defaultValues);
    }
}
