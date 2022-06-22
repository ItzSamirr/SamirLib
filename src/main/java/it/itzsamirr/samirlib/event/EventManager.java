package it.itzsamirr.samirlib.event;

import it.itzsamirr.samirlib.event.listener.EventTarget;
import it.itzsamirr.samirlib.event.listener.Listener;
import it.itzsamirr.samirlib.utils.ReflectionUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ItzSamirr
 * Created at 22.06.2022
 **/
public class EventManager {
    private List<Listener<?>> registeredListeners;

    public void register(Listener<?> listener){
        registeredListeners.add(listener);
    }

    public void unregister(Listener<?> listener){
        registeredListeners.remove(listener);
    }

    public <T extends JavaPlugin> void unregister(Class<T> pluginClass){
        registeredListeners.removeIf(l -> pluginClass.isAssignableFrom(l.getPlugin().getClass()));
    }

    public void unregisterAll(){
        registeredListeners.clear();
    }

    public void call(Event e){
        for(Listener<?> listener : registeredListeners){
            List<Method> methods = ReflectionUtils.getMethods(listener.getClass()).stream()
                    .filter(m -> m.isAnnotationPresent(EventTarget.class))
                    .filter(m -> m.getParameterTypes().length == 1)
                    .filter(m -> e.getClass().isAssignableFrom(m.getParameterTypes()[0]))
                    .sorted((m1, m2) -> m2.getDeclaredAnnotation(EventTarget.class).priority().ordinal() - m1.getDeclaredAnnotation(EventTarget.class).priority().ordinal())
                    .collect(Collectors.toList());
            methods.forEach(m -> {
                try {
                    m.invoke(listener, e);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public List<Listener<?>> getRegisteredListeners() {
        return registeredListeners;
    }
}
