package me.derpy.core.configuration;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    /**
     * Retrieves the plugin's configuration directory as a ConfigurationFolder
     * @return ConfigurationFolder
     */
    public ConfigurationFolder getConfigurationDirectory(){
        return new ConfigurationFolder(this.PLUGIN.getDataFolder());
    }

    /**
     * Copy a file from the jar to a selected destination
     * @param name The name of the file to be copied
     * @param resourceFolderPath The path the resource is located in
     * @param dest The destination the resource will be copied to
     * @return File - The resource that has been copied
     * @throws IOException Throws if file cannot be copied to destination
     */
    public File copyFileFromJar(String name, String resourceFolderPath, File dest)
            throws IOException{
        JarFile jarFile = new JarFile(PLUGIN.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        File file = null;
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            if (entry.getName().contains(name) && entry.getName().contains(resourceFolderPath)) {
                File newFile = new File(dest.getAbsolutePath() + File.separatorChar + name);
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                newFile.createNewFile();
                InputStream inputStream = PLUGIN.getResource(entry.getName());
                FileOutputStream outputStream = new FileOutputStream(newFile);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
                file = newFile;
            }
        }
        jarFile.close();
        if (file == null || !file.exists()) {
            Bukkit.getConsoleSender().sendMessage(
                    ChatColor.GREEN + "[DerpyLib]: " + ChatColor.RED + PLUGIN.getName() + " failed to create " + name);
        }
        return file;
    }
}
