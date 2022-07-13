package it.itzsamirr.samirlib.configuration.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public interface JsonConfig<T extends JavaPlugin, E extends ConfigurationObject> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    default Gson getGson(){
        return gson;
    }
    File getFile();
    List<E> getList();
    T getPlugin();
    default void save(){
        List<HashMap<String, Object>> list = getList().stream().map(ConfigurationObject::save).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        try {
            FileWriter writer = new FileWriter(getFile(), false);
            HashMap<String, Object>[] array = new HashMap[list.size()];
            list.toArray(array);
            writer.write(getGson().toJson(array,  HashMap[].class));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void load();
}
