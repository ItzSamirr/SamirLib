package it.itzsamirr.samirlib.event.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ItzSamirr
 * Created at 22.06.2022
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventTarget {
    EventPriority priority() default EventPriority.NORMAL;
}
