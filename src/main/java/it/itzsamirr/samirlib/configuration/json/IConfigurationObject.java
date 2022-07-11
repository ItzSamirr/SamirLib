package it.itzsamirr.samirlib.configuration.json;

import it.itzsamirr.samirlib.configuration.json.annotations.JsonPath;
import it.itzsamirr.samirlib.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public interface IConfigurationObject {
    default HashMap<String, Object> save(){
        HashMap<String, Object> map = new HashMap<>();
        for(Field field : ReflectionUtils.getFields(getClass())){
            if(field.isAnnotationPresent(JsonPath.class)){
                JsonPath path = field.getAnnotation(JsonPath.class);
                boolean wasAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    map.put(path.name(), field.getType().cast(field.get(this)));
                    field.setAccessible(wasAccessible);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
