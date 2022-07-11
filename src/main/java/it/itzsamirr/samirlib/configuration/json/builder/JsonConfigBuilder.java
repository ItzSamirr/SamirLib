package it.itzsamirr.samirlib.configuration.json.builder;

import com.google.gson.Gson;
import it.itzsamirr.samirlib.configuration.json.AbstractJsonConfig;
import it.itzsamirr.samirlib.configuration.json.JsonConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public final class JsonConfigBuilder<T extends JavaPlugin> {
    private BiConsumer<Gson, AbstractMap.SimpleEntry<File, T>> onLoad;
    private BiConsumer<Gson, AbstractMap.SimpleEntry<File, T>> onSave;
    private T plugin;
    private Gson gson;
    private File file;

    public JsonConfigBuilder(T plugin, Gson gson, File file) {
        this.onLoad = (gson1, e) -> {};
        this.onSave = (gson1, e) -> {};
        this.plugin = plugin;
        this.gson = gson;
        this.file = file;
    }

    public JsonConfigBuilder gson(Gson gson) {
        this.gson = gson;
        return this;
    }

    public JsonConfigBuilder file(File file) {
        this.file = file;
        return this;
    }

    public JsonConfigBuilder<T> plugin(T plugin){
        this.plugin = plugin;
        return this;
    }

    public JsonConfigBuilder onLoad(BiConsumer<Gson, AbstractMap.SimpleEntry<File, T>> onLoad) {
        this.onLoad = onLoad;
        return this;
    }

    public JsonConfigBuilder onSave(BiConsumer<Gson, AbstractMap.SimpleEntry<File, T>> onSave) {
        this.onSave = onSave;
        return this;
    }

    public JsonConfig build() {
        return new AbstractJsonConfig(plugin, gson, file) {
            @Override
            public void load() {
                onLoad.accept(gson, new AbstractMap.SimpleEntry<>(file, JsonConfigBuilder.this.plugin));
            }

            @Override
            public void save() {
                onSave.accept(gson, new AbstractMap.SimpleEntry<>(file, JsonConfigBuilder.this.plugin));
            }
        };
    }


}
