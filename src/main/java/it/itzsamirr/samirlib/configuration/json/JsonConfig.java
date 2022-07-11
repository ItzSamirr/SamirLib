package it.itzsamirr.samirlib.configuration.json;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public interface JsonConfig<T extends JavaPlugin> {
    Gson getGson();
    File getFile();
    T getPlugin();
    void save();
    void load();
}
