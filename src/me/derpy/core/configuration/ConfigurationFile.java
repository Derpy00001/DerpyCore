package me.derpy.core.configuration;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationFile extends File {

    public ConfigurationFile(File configurationFile){
        super(configurationFile.toURI());
    }

    /**
     * Opens the configuration of a yaml file
     * @return YamlConfiguration
     */
    public YamlConfiguration openConfiguration(){
        if(this.exists()){
            return YamlConfiguration.loadConfiguration(this);
        }else{
            throw new NullPointerException("Configuration File does not exist!");
        }
    }

    /**
     * Saves the configuration to a yaml file
     * @param configuration The configuration to be saved
     * @throws IOException Throws if file cannot be saved
     */
    public void saveConfiguration(YamlConfiguration configuration) throws IOException {
        if(this.exists()){
            configuration.save(this);
        }else{
            throw new NullPointerException("Configuration File does not exist!");
        }
    }
}
