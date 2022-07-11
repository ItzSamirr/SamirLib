package it.itzsamirr.samirlib.configuration.yml;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/

@Data
public class YamlConfig<T extends JavaPlugin> {
    protected File file;
    protected FileConfiguration configuration;
    protected T plugin;

    public YamlConfig(T plugin, String name) {
        this.plugin = plugin;
        if(!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        StringBuilder bname = new StringBuilder();
        bname.append(!name.substring(name.length()-".yml".length()-1).equals(".yml") ? name + ".yml" : name);
        this.file = new File(plugin.getDataFolder(), bname.toString());
    }

    public synchronized void load(boolean fromDefault) throws IOException {
        if(!file.exists()){
            if(!fromDefault)
                file.createNewFile();
            else
                plugin.saveResource(file.getName(), true);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public synchronized void load() throws IOException {
        load(false);
    }

    public synchronized void save() throws IOException {
        configuration.save(file);
    }

    public void set(String path, Object o, boolean save){
        configuration.set(path, o);
        if(save) {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void set(String path, Object o){
        set(path, o, false);
    }

    public <V> V get(String path, Class<V> clazz){
        return clazz.cast(get(path));
    }

    public Object get(String path){
        return configuration.get(path);
    }



}
