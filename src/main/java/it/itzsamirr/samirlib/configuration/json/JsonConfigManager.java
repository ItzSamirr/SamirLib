package it.itzsamirr.samirlib.configuration.json;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public class JsonConfigManager {
    private HashMap<JavaPlugin, ArrayList<JsonConfig<? extends JavaPlugin>>> registeredConfigs = new HashMap<>();

    public <T extends JavaPlugin> JsonConfig<T> registerConfig(JsonConfig<T> config){
        if(!registeredConfigs.containsKey(config.getPlugin())){
            registeredConfigs.put(config.getPlugin(), new ArrayList<>(Arrays.asList(config)));
            return config;
        }
        ArrayList<JsonConfig<? extends JavaPlugin>> configs = getRegisteredConfigs(config.getPlugin());
        configs.add(config);
        registeredConfigs.replace(config.getPlugin(), configs);
        return config;
    }

    public void saveAll(){
        registeredConfigs.values().forEach(v -> v.forEach(JsonConfig::save));
    }

    public void save(JavaPlugin plugin){
        getRegisteredConfigs(plugin).forEach(JsonConfig::save);
    }

    public void load(JavaPlugin plugin){
        getRegisteredConfigs(plugin).forEach(JsonConfig::load);
    }

    public <T extends JsonConfig<? extends JavaPlugin>> T getJsonConfig(Class<T> clazz){
        return registeredConfigs.values().stream().filter(config -> config.getClass().equals(clazz)).findFirst().map(c -> clazz.cast(c)).orElse(null);
    }

    public void loadAll(){
        registeredConfigs.values().forEach(v -> v.forEach(JsonConfig::load));
    }

    public HashMap<JavaPlugin, ArrayList<JsonConfig<? extends JavaPlugin>>> getRegisteredConfigs() {
        return registeredConfigs;
    }

    public ArrayList<JsonConfig<? extends JavaPlugin>> getRegisteredConfigs(JavaPlugin plugin){
        return registeredConfigs.get(plugin);
    }
}
