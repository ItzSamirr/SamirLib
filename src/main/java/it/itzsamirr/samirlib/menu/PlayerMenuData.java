package it.itzsamirr.samirlib.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
public class PlayerMenuData {
    private Player owner;
    private Map<String, Object> data = new HashMap<>();
    private Stack<Menu> menuHistory = new Stack<>();

    public PlayerMenuData(Player owner) {
        this.owner = owner;
    }

    public Menu getLastMenu(){
        menuHistory.pop();
        return menuHistory.pop();
    }

    public void push(Menu<?> menu){
        menuHistory.push(menu);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Player getOwner() {
        return owner;
    }

    public void setData(String key, Object data){
        this.data.put(key.toLowerCase(), data);
    }

    public void setData(Enum<?> key, Object data){
        setData(key.toString().toLowerCase(), data);
    }

    public Object getData(String key){
        return data.get(key.toLowerCase());
    }

    public Object getDat(Enum<?> key){
        return getData(key.toString());
    }

    public <T> T getData(String key, Class<T> clazz){
        Object value = getData(key);
        if(value == null) return null;
        return clazz.cast(value);
    }

    public <T> T getData(Enum<?> key, Class<T> clazz){
        return this.getData(key.toString(), clazz);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
