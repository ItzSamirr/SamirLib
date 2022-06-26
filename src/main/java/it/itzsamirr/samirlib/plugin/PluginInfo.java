package it.itzsamirr.samirlib.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author ItzSamirr
 * Created at 26.06.2022
 **/
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface PluginInfo {
    String prefix();
}
