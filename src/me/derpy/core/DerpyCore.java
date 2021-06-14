package me.derpy.core;

import me.derpy.core.Logger.LogHandler;
import me.derpy.core.commands.CommandCreateItem;
import me.derpy.core.configuration.ConfigurationHandler;
import me.derpy.core.gui.visual.GuiHandler;
import me.derpy.core.items.ItemHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DerpyCore extends JavaPlugin {
    private static Map<String, Map<Integer, Object>> handlerMap = new HashMap<>();
    private static GuiHandler guiHandler;
    @Override
    public void onEnable(){
        // Commands
        new CommandCreateItem();
        // static handlers
        guiHandler = new GuiHandler();
        getLogHandler(this).log(new LogRecord(Level.INFO,"DerpyCore Loaded!"));
        getLogHandler(this).logLargeText("DerpyCore!", LogHandler.LogColor.AQUA);
    }

    private static Object processHandler(JavaPlugin plugin, int key){
        Map<Integer, Object> map;
        Object obj = null;
        if(!handlerMap.containsKey(plugin.getName())){
            handlerMap.put(plugin.getName(), new HashMap<>());
        }
        map = handlerMap.get(plugin.getName());
        if(!map.containsKey(key)){
            switch(key) {
                case 0:
                    obj = new LogHandler(plugin);
                    break;
                case 1:
                    obj = new ConfigurationHandler(plugin);
                    break;
                case 2:
                    obj = new ItemHandler(plugin);
                    break;
            }
            map.put(key, obj);
        }else{
            obj = map.get(key);
        }
        return obj;
    }

    /**
     * Retrieve the Item Handler module for your plugin
     * @param plugin The plugin using the item handler
     * @return ItemHandler
     */
    public static ItemHandler getItemHandler(JavaPlugin plugin) {
        return (ItemHandler) processHandler(plugin, 2);
    }

    /**
     * Retrieve the Configuration Handler module for your plugin
     * @param plugin The plugin using the configuration handler
     * @return ConfigurationHandler
     */
    public static ConfigurationHandler getConfigurationHandler(JavaPlugin plugin) {
        return (ConfigurationHandler) processHandler(plugin, 1);
    }

    public static LogHandler getLogHandler(JavaPlugin plugin){
        return (LogHandler) processHandler(plugin, 0);
    }

    /**
     * Retrieve the Gui Handler for easy gui creation
     * @return GuiHandler
     */
    public static GuiHandler getGuiHandler(){
        return guiHandler;
    }
}
