package it.itzsamirr.samirlib.command;


import it.itzsamirr.samirlib.SamirLib;
import it.itzsamirr.samirlib.command.sub.AbstractSubCommand;
import it.itzsamirr.samirlib.utils.Color;
import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ItzSamirr
 **/
public abstract class AbstractCommand<T extends JavaPlugin> implements TabExecutor {
    protected final SamirLib lib;
    protected final CommandInfo info;
    protected T plugin;
    protected HashMap<Class<? extends AbstractSubCommand<?>>, AbstractSubCommand<?>> subCommandsMap = new HashMap<>();

    @SafeVarargs
    protected AbstractCommand(T plugin, Class<? extends AbstractSubCommand<?>>... sub) {
        this.plugin = plugin;
        this.lib = SamirLib.getInstance();
        Class<? extends Plugin> clazz = plugin.getClass();
        for (Class<? extends AbstractSubCommand<?>> sb : sub) {
            try {
                subCommandsMap.put(sb, sb.getDeclaredConstructor(clazz).newInstance(plugin));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        this.info = getClass().getDeclaredAnnotation(CommandInfo.class);
        Validate.notNull(info, "Annotation CommandInfo not found for " + this.getClass().getName());
    }

    public T getPlugin() {
        return plugin;
    }

    public abstract String getNoPermissionMessage();
    public abstract String getUsageMessage();
    public abstract String getConsoleNotAllowedMessage();

    public AbstractSubCommand getSubCommand(Class<? extends AbstractSubCommand> clazz){
        return subCommandsMap.get(clazz);
    }

    public AbstractSubCommand getSubCommand(String name){
        return subCommandsMap.values().stream()
                .filter(abstractSubCommand -> abstractSubCommand.getInfo().name().equalsIgnoreCase(name)
                        || Arrays.asList(abstractSubCommand.getInfo().aliases()).contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public HashMap<Class<? extends AbstractSubCommand<?>>, AbstractSubCommand<?>> getSubCommandsMap() {
        return subCommandsMap;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0 && !subCommandsMap.isEmpty()){
            AbstractSubCommand<?> sbcmd = getSubCommand(args[0]);
            if(sbcmd != null){
                if(sbcmd.getInfo().onlyPlayers()){
                    if(!(sender instanceof Player)){
                        sender.sendMessage(Color.translate(sbcmd.getConsoleNotAllowedMessage()));
                        return false;
                    }
                }
                if(!sbcmd.getInfo().permission().isEmpty()){
                    if(!sender.hasPermission(sbcmd.getInfo().permission())){
                        sender.sendMessage(Color.translate(sbcmd.getNoPermissionMessage()));
                        return false;
                    }
                }
                sbcmd.run(sender, Arrays.copyOfRange(args, 1, args.length-1));
                return false;
            }
            if(getUsageMessage() != null && !getUsageMessage().isEmpty()){
                sender.sendMessage(Color.translate(getUsageMessage()));
                return false;
            }
        }
        if(info.onlyPlayers()){
            if(!(sender instanceof Player)){
                sender.sendMessage(Color.translate(getConsoleNotAllowedMessage()));
                return false;
            }
        }
        if(!info.permission().isEmpty()){
            if(!sender.hasPermission(info.permission())){
                sender.sendMessage(Color.translate(getNoPermissionMessage()));
                return false;
            }
        }
        run(sender, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            List<String> completions = subCommandsMap.values().stream().map(sb -> sb.getInfo().name()).collect(Collectors.toList());
            List<String> aliasesCompletions = new ArrayList<>();
            subCommandsMap.values().forEach(sb -> aliasesCompletions.addAll(Arrays.asList(sb.getInfo().aliases())));
            completions.addAll(aliasesCompletions);
            return completions
                    .stream()
                    .filter(c -> c.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }else{
            if(!subCommandsMap.isEmpty()) {
                AbstractSubCommand subcmd = getSubCommand(args[0]);
                if (subcmd != null) {
                    String[] array = Arrays.copyOfRange(args, 1, args.length - 1);
                    return subcmd.tab(sender, array);
                }
            }
            return this.tab(sender, args);
        }
    }

    public abstract void run(CommandSender sender, String[] args);
    public List<String> tab(CommandSender sender, String[] args){
        return new ArrayList<>();
    }

    public CommandInfo getInfo() {
        return info;
    }
}
