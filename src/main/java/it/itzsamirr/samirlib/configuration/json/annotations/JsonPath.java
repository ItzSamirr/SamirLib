package it.itzsamirr.samirlib.configuration.json.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface JsonPath {
    String name();
}
