package me.derpy.core.commands;

import me.derpy.core.DerpyCore;
import me.derpy.core.items.ItemHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CommandCreateItem implements CommandExecutor, TabCompleter {

    public CommandCreateItem(){
        DerpyCore.getPlugin(DerpyCore.class).getCommand("createitem").setExecutor(this);
        DerpyCore.getPlugin(DerpyCore.class).getCommand("createitem").setPermission("derpycore.createitem");
    }

    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3){
        List<String> culled = new ArrayList<>();
        if(arg3.length==1){
            ItemHandler.getRegisteredPlugins().forEach((String plugin)->{
                if(plugin.toLowerCase().contains(arg3[0].toLowerCase(Locale.ROOT))){
                    culled.add(plugin);
                }
            });
        }else if(arg3.length==2){
            ItemHandler.getRegisteredItems(arg3[0]).keySet().forEach((String key)->{
                if(key.toLowerCase(Locale.ROOT).contains(arg3[1].toLowerCase(Locale.ROOT))){
                    culled.add(key);
                }
            });
        }
        Collections.sort(culled);
        return culled;
    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3){
        if(!(arg0 instanceof Player)){
            arg0.sendMessage("Only players can execute this command!");
        }else{
            Player player = (Player) arg0;
            if(player.hasPermission("derpycore.createitem")){
                if(arg3.length==2){
                    if(ItemHandler.getRegisteredPlugins().contains(arg3[0])){
                        if(ItemHandler.getRegisteredItems(arg3[0]).containsKey(arg3[1])){
                            ItemStack item = ItemHandler.getRegisteredItems(arg3[0]).get(arg3[1]);
                            if(player.getInventory().firstEmpty()==-1){
                                player.getWorld().dropItemNaturally(player.getLocation(), item);
                                player.sendMessage(ChatColor.GREEN+"Inventory Full!, Dropped Item");
                            }else{
                                player.getInventory().addItem(item);
                                player.sendMessage(ChatColor.GREEN+"Created Item!");
                            }
                        }else{
                            player.sendMessage(ChatColor.RED+"This item has not been registered by the plugin!");
                        }
                    }else{
                        player.sendMessage(ChatColor.RED+"This plugin has not been registered!");
                    }
                }else if(arg3.length==1){
                    player.sendMessage(ChatColor.RED+"Please include the item you wish to spawn!");
                }else{
                    player.sendMessage(ChatColor.RED+"Please include which plugin you'd like to select from and the item key!");
                }
            }else{
                player.sendMessage(ChatColor.RED+"Invalid Permissions!");
            }
        }
        return true;
    }
}
