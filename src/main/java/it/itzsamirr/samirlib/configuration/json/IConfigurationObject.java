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
        return convert(this);
    }//

    default HashMap<String, Object> convert(IConfigurationObject object){
        HashMap<String, Object> map = new HashMap<>();
        for(Field field : ReflectionUtils.getFields(object.getClass())){
            if(field.isAnnotationPresent(JsonPath.class)){
                JsonPath path = field.getAnnotation(JsonPath.class);
                boolean wasAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if(field.getType().isAssignableFrom(IConfigurationObject.class)){
                        map.put(path.name(), convert((IConfigurationObject) field.get(object)));
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
