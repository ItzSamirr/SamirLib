package it.itzsamirr.samirlib.command.sub;

import it.itzsamirr.samirlib.SamirLib;
import jdk.tools.jlink.plugin.Plugin;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSubCommand<T extends JavaPlugin> {
    protected final SamirLib lib;
    protected final SubCommandInfo info;
    protected final T plugin;

    public AbstractSubCommand(T plugin) {
        this.lib = SamirLib.getInstance();
        this.plugin = plugin;
        this.info = getClass().getDeclaredAnnotation(SubCommandInfo.class);
        Validate.notNull(this.info, "Annotation SubCommandInfo not found for " +getClass().getName());
    }

    public abstract void run(CommandSender sender, String[] args);
    public List<String> tab(CommandSender sender, String[] args){
        return Collections.emptyList();
    }

    public abstract String getNoPermissionMessage();
    public abstract String getConsoleNotAllowedMessage();

    public SubCommandInfo getInfo() {
        return info;
    }
}
