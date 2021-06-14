package me.derpy.core.enchantments;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentHandler {
    private static Map<String, Map<NamespacedKey, Enchantment>> enchantmentMap = new HashMap<>();
    private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };
    private final Plugin PLUGIN;

    public EnchantmentHandler(Plugin plugin){
        this.PLUGIN = plugin;
        if(!enchantmentMap.containsKey(plugin.name())){
            enchantmentMap.put(plugin.name(), new HashMap<>());
        }
    }

    /**
     * Gets the tag to display as the lore for the enchant as enchants are read client side and will not be displayed by themselves
     * Example: Lifesteal IX
     * @param enchantment The enchantment to attach to the tag
     * @param level The leg of the enchantment to convert to roman numerals 1-10
     * @return String
     */
    public static String getNumeralTag(Enchantment enchantment, int level){
        if(level==1&&enchantment.getMaxLevel()==1){
            return enchantment.getName();
        }
        if(level > 10 || level <=0){
            return enchantment.getName()+" "+level;
        }
        return enchantment.getName()+" "+NUMERALS[level-1];
    }

    private static void setAcceptingNew(boolean b){
        try{
            Field accepting = Enchantment.class.getDeclaredField("acceptingNew");
            accepting.setAccessible(b);
            accepting.set(null, b);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a custom enchantment with the enchantment handler and the server
     * @param enchantment The custom enchantment class
     * @param key The key associated with the enchantment
     */
    public void registerEnchantment(Enchantment enchantment, NamespacedKey key){
        setAcceptingNew(true);
        Enchantment.registerEnchantment(enchantment);
        enchantmentMap.get(this.PLUGIN.name()).put(key, enchantment);
        setAcceptingNew(false);
    }

    /**
     * Unregister a custom enchantment with the enchantment handler and the server
     * @param enchantment The enchantment to unregister
     * @param key The Key associated with the enchantment
     * @throws NoSuchFieldException Throws if the bukkit enchantment map fields do not exist
     * @throws IllegalAccessException Throws if plugin cannot access protected fields
     */
    public void unregisterEnchantment(Enchantment enchantment, NamespacedKey key) throws NoSuchFieldException, IllegalAccessException {
        Field keyField = Enchantment.class.getDeclaredField("byKey");
        keyField.setAccessible(true);
        HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
        if(byKey.containsKey(key)){
            byKey.remove(key);
        }
        Field nameField = Enchantment.class.getDeclaredField("byName");
        nameField.setAccessible(true);
        HashMap<String,Enchantment> byName = (HashMap<String, Enchantment>)nameField.get(null);
        if(byName.containsKey(enchantment.getName())){
            byName.remove(enchantment.getName());
        }
        keyField.setAccessible(false);
        nameField.setAccessible(false);
        if(enchantmentMap.get(this.PLUGIN.name()).containsKey(key)){
            enchantmentMap.get(this.PLUGIN.name()).remove(key);
        }
    }

    /**
     * Retrieves a book with the specified enchantment
     * CANNOT be apply custom enchantments with anvils, but manually add checks to apply
     * @param enchantment The enchantment to add to the book
     * @param level The level of the enchantment
     * @param ignoreLevelRestriction If the level can be higher than the enchantments max level
     * @return ItemStack
     */
    public static ItemStack getBook(Enchantment enchantment, int level, boolean ignoreLevelRestriction){
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchantment, level, ignoreLevelRestriction);
        item.setItemMeta(meta);
        return addEnchantCopy(item, enchantment, meta.getStoredEnchantLevel(enchantment), true);
    }

    /**
     * Retrieves a copy of an item with the specified enchantment
     * @param item The item to copy
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @param book Whether or not the item is a enchanted book
     * @return ItemStack
     */
    public static ItemStack addEnchantCopy(ItemStack item, Enchantment enchantment, int level, boolean book){
        ItemStack newItem = item.clone();
        ItemMeta meta = newItem.getItemMeta();
        if(!book){
            meta.addEnchant(enchantment, level, true);
        }else{
            ((EnchantmentStorageMeta)meta).addStoredEnchant(enchantment, level, true);
        }
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(0, ChatColor.GRAY+getNumeralTag(enchantment, level));
        meta.setLore(lore);
        return item;
    }
}
