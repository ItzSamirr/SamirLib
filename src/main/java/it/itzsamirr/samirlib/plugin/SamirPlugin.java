package it.itzsamirr.samirlib.plugin;

import it.itzsamirr.samirlib.utils.SamirLogger;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 26.06.2022
 **/
public abstract class SamirPlugin extends JavaPlugin {
    protected SamirLogger<SamirPlugin> customLogger;
    protected PluginInfo info = null;

    public void enable(){}
    public void load(){}
    public void disable(){}

    @Override
    public void onEnable() {
        info = getClass().getDeclaredAnnotation(PluginInfo.class);
        Validate.notNull(info, "PluginInfo annotation is missing");
        customLogger = new SamirLogger<>(this, info.prefix());
        enable();
    }

    public PluginInfo getInfo() {
        return info;
    }

    @Override
    public void onDisable() {
        disable();
    }

    @Override
    public void onLoad() {
        load();
    }

    public SamirLogger<SamirPlugin> getCustomLogger() {
        return customLogger;
    }

    public void setCustomLogger(SamirLogger<SamirPlugin> customLogger) {
        this.customLogger = customLogger;
    }
}
