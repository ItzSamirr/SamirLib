package it.itzsamirr.samirlib.command.sub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubCommandInfo {
    String name();
    String permission() default "";
    String[] aliases() default {};
    boolean onlyPlayers() default false;
}
