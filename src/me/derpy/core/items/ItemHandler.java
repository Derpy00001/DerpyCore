package me.derpy.core.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemHandler {
    private static Map<String, Map<String, ItemStack>> itemMap = new HashMap<>();
    private final JavaPlugin PLUGIN;

    public ItemHandler(JavaPlugin plugin){
        this.PLUGIN = plugin;
        if(!itemMap.containsKey(plugin.getName())){
            itemMap.put(plugin.getName(), new HashMap<>());
        }
    }

    /**
     * Retrieve a list of the registered plugins using the Item Handler
     * @return Set String
     */
    public static Set<String> getRegisteredPlugins(){
        return Collections.unmodifiableSet(itemMap.keySet());
    }

    /**
     * Retrieve a list of the items registered by a plugin
     * @param plugin The name of the plugin to retrieve the items from
     * @return Map String,ItemStack
     */
    public static Map<String, ItemStack> getRegisteredItems(String plugin){
        return Collections.unmodifiableMap(itemMap.get(plugin));
    }

    /**
     * Retrieve a list of the items registered by a plugin
     * @param plugin The plugin to retrieve the items from
     * @return Map String,ItemStack
     */
    public static Map<String, ItemStack> getRegisteredItems(JavaPlugin plugin){
        return Collections.unmodifiableMap(itemMap.get(plugin.getName()));
    }

    /**
     * Retrieve a list of the items registered by your plugin
     * @return Map String,ItemStack
     */
    public Map<String, ItemStack> getRegisteredItems(){
        return itemMap.get(this.PLUGIN.getName());
    }

    /**
     * Register an item in the ItemHandler
     * @param key The unique key identifier for the item
     * @param item The Item to register
     * @throws Exception Throws exception if key is already in use
     */
    public void registerItem(NamespacedKey key, ItemStack item) throws Exception {
        if(itemMap.get(this.PLUGIN.getName()).containsKey(key)){
            throw new Exception("Key is already in use!");
        }
        itemMap.get(this.PLUGIN.getName()).put(key.toString(), item);
    }

    /**
     * Remove a registered item in the ItemHandler
     * @param key The unique identifier for the item
     */
    public void removeItem(NamespacedKey key){
        if(!itemMap.get(this.PLUGIN.getName()).containsKey(key.toString())){
            throw new NullPointerException("Key is not registered!");
        }
        itemMap.get(this.PLUGIN.getName()).remove(key.toString());
    }

    /**
     * Retrieve an item from its unique identifier
     * @param key The unique identifier of the item to be retrieved
     * @return ItemStack
     */
    public ItemStack getItemFromkey(NamespacedKey key){
        if(!itemMap.get(this.PLUGIN.getName()).containsKey(key.toString())){
            throw new NullPointerException("Key is not registered!");
        }
        return itemMap.get(this.PLUGIN.getName()).get(key.toString());
    }

}
