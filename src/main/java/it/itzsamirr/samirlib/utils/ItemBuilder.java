package it.itzsamirr.samirlib.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
public class ItemBuilder {
    private ItemStack is;

    public ItemBuilder(Material material){
        is = new ItemStack(material);
    }

    public ItemBuilder name(String name){
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(Color.translate(name));
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder amount(int amount){
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder flag(ItemFlag flag){
        is.addItemFlags(flag);
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        ItemMeta meta = is.getItemMeta();
        meta.setLore(lore);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder durability(int durability){
        ItemMeta meta = is.getItemMeta();
        if(meta instanceof Damageable){
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(durability);
            is.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, AttributeModifier modifier){
        ItemMeta meta = is.getItemMeta();
        meta.addAttributeModifier(attribute, modifier);
        is.setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        return is.clone();
    }
}
