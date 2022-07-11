package it.itzsamirr.samirlib.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/

@UtilityClass
public class ReflectionUtils {
    private HashMap<Class<?>, List<Method>> methodsCache = new HashMap<>();
    private HashMap<Class<?>, List<Field>> fieldsCache = new HashMap<>();
    private HashMap<String, Class<?>> classesCache = new HashMap<>();
    private String minecraftVersion;
    private int releaseVersion;

    static{
        try {
            minecraftVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch(Exception e){
            minecraftVersion = "";
        }
        try {
            releaseVersion = Integer.parseInt(Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.")[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getReleaseVersion() {
        return releaseVersion;
    }

    @SneakyThrows
    public Class<?> getClass(String name){
        return classesCache.computeIfAbsent(name, n -> {
            try {
                return Class.forName(n
                        .replace("{nms}", "net.minecraft.server." + minecraftVersion + ".")
                        .replace("{cb}", "org.bukkit.craftbukkit." + minecraftVersion + "."));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    @SneakyThrows
    public Method getMethod(Class<?> clazz, String name, Class<?>... parameters){
        if(!methodsCache.containsKey(clazz)){
            methodsCache.put(clazz, Arrays.asList(clazz.getDeclaredMethod(name, parameters)));
        }
        List<Method> methods = methodsCache.get(clazz);
        if(methods.stream().noneMatch(m -> m.getName().equals(name) && Arrays.equals(m.getParameterTypes(), parameters))){
            methods.add(clazz.getDeclaredMethod(name, parameters));
            methodsCache.replace(clazz, methods);
        }
        return methodsCache.get(clazz)
                .stream()
                .filter(m -> m.getName().equals(name))
                .filter(m -> Arrays.equals(m.getParameterTypes(), parameters))
                .findFirst().orElse(null);
    }

    public List<Method> getMethods(Class<?> clazz){
        if(!methodsCache.containsKey(clazz)){
            return methodsCache.put(clazz, Arrays.asList(clazz.getDeclaredMethods()));
        }
        List<Method> methods = methodsCache.get(clazz);
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if(!methods.contains(declaredMethod)) methods.add(declaredMethod);
        }
        methodsCache.replace(clazz, methods);
        return methodsCache.get(clazz);
    }

    public List<Field> getFields(Class<?> clazz){
        if(!fieldsCache.containsKey(clazz)){
            return fieldsCache.put(clazz, Arrays.asList(clazz.getDeclaredFields()));
        }
        List<Field> fields = fieldsCache.get(clazz);
        List<Field> clazzFields = Arrays.asList(clazz.getDeclaredFields());
        if(clazzFields.isEmpty()) {
            fields.clear();
            fieldsCache.replace(clazz, fields);
            return fieldsCache.get(clazz);
        }
        fields.removeIf(field -> !clazzFields.contains(field));
        for(Field field : clazzFields){
            if(!fields.contains(field)) fields.add(field);
        }
        fieldsCache.replace(clazz, fields);
        return fieldsCache.get(clazz);
    }

    public Method getMethod(String className, String name, Class<?>... parameterTypes){
        return getMethod(getClass(className), name, parameterTypes);
    }

    public Field getField(String className, String name){
        return getField(getClass(className), name);
    }

    @SneakyThrows
    public Field getField(Class<?> clazz, String name){
        if(!fieldsCache.containsKey(clazz)){
            fieldsCache.put(clazz, Arrays.asList(clazz.getDeclaredField(name)));
        }
        List<Field> fields = fieldsCache.get(clazz);
        if(fields.stream().noneMatch(f -> f.getName().equals(name))){
            fields.add(clazz.getDeclaredField(name));
            fieldsCache.replace(clazz, fields);
        }
        return fieldsCache.get(clazz).stream()
                .filter(f -> f.getName().equals(name))
                .findFirst().orElse(null);
    }
}
