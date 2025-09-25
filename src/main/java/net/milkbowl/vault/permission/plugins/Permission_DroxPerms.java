package net.milkbowl.vault.permission.plugins;

import de.hydrox.bukkit.DroxPerms.DroxPerms;
import de.hydrox.bukkit.DroxPerms.DroxPermsAPI;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;


public class Permission_DroxPerms
  extends Permission
{
   private final String name = "DroxPerms";
  private DroxPermsAPI API;
  private boolean useOnlySubgroups;
  
  public Permission_DroxPerms(Plugin plugin) {
     this.plugin = plugin;

    
     if (this.API == null) {
       DroxPerms p = (DroxPerms)plugin.getServer().getPluginManager().getPlugin("DroxPerms");
       if (p != null) {
         this.API = p.getAPI();
         log.info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), "DroxPerms" }));
         this.useOnlySubgroups = p.getConfig().getBoolean("Vault.useOnlySubgroups", true);
         log.info(String.format("[%s][Permission] Vault.useOnlySubgroups: %s", new Object[] { plugin.getDescription().getName(), Boolean.valueOf(this.useOnlySubgroups) }));
      } 
    } 
    
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(), plugin);
  }
  
  public class PermissionServerListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (Permission_DroxPerms.this.API == null) {
         Plugin permPlugin = event.getPlugin();
         if (permPlugin.getDescription().getName().equals("DroxPerms")) {
           Permission_DroxPerms.this.API = ((DroxPerms)permPlugin).getAPI();
           Permission_DroxPerms.log.info(String.format("[%s][Permission] %s hooked.", permPlugin.getDescription().getName(), "DroxPerms"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (Permission_DroxPerms.this.API != null && 
         event.getPlugin().getDescription().getName().equals("DroxPerms")) {
         Permission_DroxPerms.this.API = null;
         Permission_DroxPerms.log.info(String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), "DroxPerms"));
      } 
    }
  }


    @Override
  public String getName() {
     getClass(); return "DroxPerms";
  }

    @Override
  public boolean isEnabled() {
     return true;
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean playerHas(String world, String player, String permission) {
     Player p = this.plugin.getServer().getPlayer(player);
     return (p != null) ? p.hasPermission(permission) : false;
  }

    @Override
  public boolean playerAdd(String world, String player, String permission) {
     return this.API.addPlayerPermission(player, world, permission);
  }

    @Override
  public boolean playerRemove(String world, String player, String permission) {
     return this.API.removePlayerPermission(player, world, permission);
  }

    @Override
  public boolean groupHas(String world, String group, String permission) {
     return false;
  }

    @Override
  public boolean groupAdd(String world, String group, String permission) {
     return this.API.addGroupPermission(group, world, permission);
  }

    @Override
  public boolean groupRemove(String world, String group, String permission) {
     return this.API.removeGroupPermission(group, world, permission);
  }

    @Override
  public boolean playerInGroup(String world, String player, String group) {
     return (this.API.getPlayerGroup(player).equalsIgnoreCase(group) || this.API.getPlayerSubgroups(player).contains(group));
  }

    @Override
  public boolean playerAddGroup(String world, String player, String group) {
     if (this.useOnlySubgroups) {
       return this.API.addPlayerSubgroup(player, group);
    }
     if ("default".equalsIgnoreCase(this.API.getPlayerGroup(player))) {
       return this.API.setPlayerGroup(player, group);
    }
     return this.API.addPlayerSubgroup(player, group);
  }



    @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
     if (this.useOnlySubgroups) {
       return this.API.removePlayerSubgroup(player, group);
    }
     if (group.equalsIgnoreCase(this.API.getPlayerGroup(player))) {
       return this.API.setPlayerGroup(player, "default");
    }
     return this.API.removePlayerSubgroup(player, group);
  }

    @Override
  public String[] getPlayerGroups(String world, String player) {
     ArrayList<String> array = this.API.getPlayerSubgroups(player);
     array.add(this.API.getPlayerGroup(player));
     return array.<String>toArray(new String[0]);
  }

    @Override
  public String getPrimaryGroup(String world, String player) {
     return this.API.getPlayerGroup(player);
  }

    @Override
  public String[] getGroups() {
     return this.API.getGroupNames();
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }
}