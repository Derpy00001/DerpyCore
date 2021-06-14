package me.derpy.core.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationFolder extends File {

    public ConfigurationFolder(File folder){
        super(folder.toURI());
    }

    /**
     * Creates a new file and returns it as a ConfigurationFile
     * @param name The name of the new file including its suffix
     * @return ConfigurationFile
     * @throws IOException Throws IOException if the file cannot be created
     */
    public ConfigurationFile createConfigurationFile(String name) throws IOException {
        if(!this.exists()){
            throw new NullPointerException("Parent Folder does not exist!");
        }
        File file = new File(this.getAbsolutePath() + File.separatorChar +name);
        file.createNewFile();
        return new ConfigurationFile(file);
    }

    /**
     * Creates a new directory and returns it as a ConfigurationFolder
     * @param name The name of the new directory
     * @return ConfigurationFolder
     */
    public ConfigurationFolder createConfigurationFolder(String name){
        if(!this.exists()){
            throw new NullPointerException("Parent Folder does not exist!");
        }
        File file = new File(this.getAbsolutePath()+File.separatorChar+name);
        file.mkdirs();
        return new ConfigurationFolder(file);
    }

    /**
     * Returns a list of configuration folders in the parent directory
     * @return List ConfigurationFolder
     */
    public List<ConfigurationFolder> getSubFolders(){
        if(!this.exists()){
            throw new NullPointerException("Parent Folder does not exist!");
        }
        List<ConfigurationFolder> folderList = new ArrayList<>();
        if(this.listFiles()!=null && this.listFiles().length>0) {
            for (File file : this.listFiles()) {
                if (file.isDirectory()) {
                    folderList.add(new ConfigurationFolder(file));
                }
            }
        }
        return folderList;
    }

    /**
     * Returns a list of configuration files in the parent directory
     * @return List ConfigurationFile
     */
    public List<ConfigurationFile> getSubConfigFiles(){
        if(!this.exists()){
            throw new NullPointerException("Parent Folder does not exist!");
        }
        List<ConfigurationFile> fileList = new ArrayList<>();
        if(this.listFiles()!=null && this.listFiles().length>0) {
            for (File file : this.listFiles()) {
                if (!file.isDirectory()) {
                    if (file.getName().toLowerCase().endsWith(".yml")) {
                        fileList.add(new ConfigurationFile(file));
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * Returns a directory in the parent directory from name
     * @param name The name of directory to retrieve
     * @return ConfigurationFolder
     */
    public ConfigurationFolder getSubFolder(String name){
        File file = this.getFile(name);
        if(file==null){
            throw new NullPointerException("Folder does not exist!");
        }else{
            if(!file.isDirectory()){
                throw new NullPointerException("This file is not a directory!");
            }
        }
        return new ConfigurationFolder(file);
    }

    /**
     * Returns a configuration file in the parent directory from name
     * @param name The name of the file including its suffix
     * @return ConfigurationFile
     */
    public ConfigurationFile getSubFile(String name){
        File file = this.getFile(name);
        if(file==null){
            throw new NullPointerException("File does not exist!");
        }else{
            if(file.isDirectory()){
                throw new NullPointerException("This file is a directory!");
            }
        }
        return new ConfigurationFile(file);
    }



    private File getFile(String name){
        if(!this.exists()){
            throw new NullPointerException("Parent Folder does not exist!");
        }
        File file = new File(this.getAbsolutePath()+File.separatorChar+name);
        if(!file.exists()){
            return null;
        }else{
            return file;
        }
    }
}
