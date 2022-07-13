package it.itzsamirr.samirlib.configuration.json.builder;

import com.google.gson.Gson;
import it.itzsamirr.samirlib.configuration.json.ConfigurationObject;
import it.itzsamirr.samirlib.configuration.json.JsonConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public final class JsonConfigBuilder<T extends JavaPlugin, E extends ConfigurationObject> {
    private BiConsumer<File, AbstractMap.SimpleEntry<Gson, T>> onLoad;
    private BiConsumer<File, AbstractMap.SimpleEntry<Gson, T>> onSave;
    private List<E> list;
    private T plugin;
    private File file;

    public JsonConfigBuilder(T plugin, File file, List<E> list) {
        this.list = list;
        this.onLoad = (file1, e) -> {};
        this.onSave = (file1, e) -> {
            List<HashMap<String, Object>> list1 = list.stream().map(ConfigurationObject::save).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            try {
                FileWriter writer = new FileWriter(file1, false);
                HashMap<String, Object>[] array = new HashMap[list.size()];
                list.toArray(array);
                writer.write(e.getKey().toJson(array,  HashMap[].class));
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        this.plugin = plugin;
        this.file = file;
    }

    public JsonConfigBuilder<T, E> list(List<E> list) {
        this.list = list;
        return this;
    }

    public JsonConfigBuilder<T, E> file(File file) {
        this.file = file;
        return this;
    }

    public JsonConfigBuilder<T, E> plugin(T plugin){
        this.plugin = plugin;
        return this;
    }

    public JsonConfigBuilder<T, E> onLoad(BiConsumer<File, AbstractMap.SimpleEntry<Gson, T>> onLoad) {
        this.onLoad = onLoad;
        return this;
    }

    public JsonConfigBuilder<T, E> onSave(BiConsumer<File, AbstractMap.SimpleEntry<Gson, T>> onSave) {
        this.onSave = onSave;
        return this;
    }

    public JsonConfig<T, E> build() {
        return new JsonConfig<T, E>() {
            @Override
            public void load() {
                onLoad.accept(file, new AbstractMap.SimpleEntry<>(gson, plugin));
            }

            @Override
            public File getFile() {
                return file;
            }

            @Override
            public List<E> getList() {
                return list;
            }

            @Override
            public T getPlugin() {
                return plugin;
            }

            @Override
            public void save() {
                onSave.accept(file, new AbstractMap.SimpleEntry<>(gson, plugin));
            }
        };
    }


}
