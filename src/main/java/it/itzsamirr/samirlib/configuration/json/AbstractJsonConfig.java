package it.itzsamirr.samirlib.configuration.json;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public abstract class AbstractJsonConfig<T extends JavaPlugin> implements JsonConfig<T>{
    protected Gson gson;
    protected File file;
    protected T plugin;

    public AbstractJsonConfig(T plugin, Gson gson, File file){
        this.plugin = plugin;
        this.file = file;
        this.gson = gson;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    public T getPlugin() {
        return plugin;
    }
}
