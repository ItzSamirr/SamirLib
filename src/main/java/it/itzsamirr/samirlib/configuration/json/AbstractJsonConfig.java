package it.itzsamirr.samirlib.configuration.json;

import com.google.gson.Gson;
import it.itzsamirr.samirlib.configuration.IConfigurationObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public abstract class AbstractJsonConfig<T extends JavaPlugin, E extends IConfigurationObject> implements JsonConfig<T, E>{
    protected File file;
    protected List<E> list;
    protected T plugin;

    public AbstractJsonConfig(T plugin, File file, List<E> list){
        this.plugin = plugin;
        this.list = list;
        this.file = file;
    }

    @Override
    public List<E> getList() {
        return list;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public T getPlugin() {
        return plugin;
    }
}
