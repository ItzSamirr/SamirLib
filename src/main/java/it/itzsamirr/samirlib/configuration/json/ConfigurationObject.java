package it.itzsamirr.samirlib.configuration.json;

import it.itzsamirr.samirlib.configuration.json.annotations.JsonPath;
import it.itzsamirr.samirlib.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public abstract class ConfigurationObject {
    private ConfigurationObject(HashMap<String, Object> map){
        for(Field field : ReflectionUtils.getFields(getClass())){
            if(!isFieldValid(field))continue;
            JsonPath path = field.getDeclaredAnnotation(JsonPath.class);
            String name = path.name();
            if(!map.containsKey(name))continue;
            try {
                field.set(this, field.getType().cast(map.get(name)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFieldValid(Field field){
        return field.isAnnotationPresent(JsonPath.class);
    }

    public HashMap<String, Object> save(){
        return convert(this);
    }

    public static <T extends ConfigurationObject> T deserialize(HashMap<String, Object> map, Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(map.getClass());
            constructor.setAccessible(true);
            T obj = constructor.newInstance(map);
            constructor.setAccessible(false);
            return obj;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, Object> convert(ConfigurationObject object){
        HashMap<String, Object> map = new HashMap<>();
        for(Field field : ReflectionUtils.getFields(object.getClass())){
            if(isFieldValid(field)){
                JsonPath path = field.getAnnotation(JsonPath.class);
                boolean wasAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if(field.getType().isAssignableFrom(ConfigurationObject.class)){
                        map.put(path.name(), convert((ConfigurationObject) field.get(object)));
                    } else {
                        map.put(path.name(), field.getType().cast(field.get(object)));
                    }
                    field.setAccessible(wasAccessible);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
