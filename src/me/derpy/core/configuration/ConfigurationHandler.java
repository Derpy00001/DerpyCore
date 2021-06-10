package me.derpy.core.configuration;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationHandler {
    public final JavaPlugin PLUGIN;

    /**
     * Set up configuration handler
     * @param plugin The plugin that is using the configuration handler
     */
    public ConfigurationHandler(JavaPlugin plugin){
        this.PLUGIN = plugin;
        if(!this.PLUGIN.getDataFolder().exists()){
            this.PLUGIN.getDataFolder().mkdir();
        }
    }

}
