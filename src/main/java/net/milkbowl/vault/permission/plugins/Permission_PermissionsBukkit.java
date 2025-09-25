package net.milkbowl.vault.permission.plugins;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionInfo;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
















public class Permission_PermissionsBukkit
  extends Permission
{
   private final String name = "PermissionsBukkit";
   private PermissionsPlugin perms = null;
  
  public Permission_PermissionsBukkit(Plugin plugin) {
     this.plugin = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
     if (this.perms == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("PermissionsBukkit");
       if (perms != null) {
         this.perms = (PermissionsPlugin)perms;
         log.info(String.format("[%s][Permission] %s hooked.", plugin.getDescription().getName(), "PermissionsBukkit"));
      } 
    } 
  }
  
  public class PermissionServerListener implements Listener {
     Permission_PermissionsBukkit permission = null;
    
    public PermissionServerListener(Permission_PermissionsBukkit permission) {
       this.permission = permission;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.permission.perms == null) {
         Plugin perms = event.getPlugin();
         if (perms.getDescription().getName().equals("PermissionsBukkit")) {
           this.permission.perms = (PermissionsPlugin)perms;
           Permission_PermissionsBukkit.log.info(String.format("[%s][Permission] %s hooked.", perms.getDescription().getName(), "PermissionsBukkit"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.permission.perms != null && 
         event.getPlugin().getDescription().getName().equals("PermissionsBukkit")) {
         this.permission.perms = null;
         Permission_PermissionsBukkit.log.info(String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), "PermissionsBukkit"));
      } 
    }
  }


    @Override
  public String getName() {
     return "PermissionsBukkit";
  }

    @Override
  public boolean isEnabled() {
     if (this.perms == null) {
       return false;
    }
     return this.perms.isEnabled();
  }


    @Override
  public boolean playerHas(String world, String player, String permission) {
     if (Bukkit.getPlayer(player) != null) {
       return Bukkit.getPlayer(player).hasPermission(permission);
    }
     return false;
  }


    @Override
  public boolean playerAdd(String world, String player, String permission) {
     if (world != null) {
       permission = world + ":" + permission;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions player setperm " + player + " " + permission + " true");
  }

    @Override
  public boolean playerRemove(String world, String player, String permission) {
     if (world != null) {
       permission = world + ":" + permission;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions player unsetperm " + player + " " + permission);
  }



    @Override
  public boolean groupHas(String world, String group, String permission) {
     if (world != null && !world.isEmpty()) {
       return (this.perms.getGroup(group).getInfo().getWorldPermissions(world).get(permission) == null) ? false : ((Boolean)this.perms.getGroup(group).getInfo().getWorldPermissions(world).get(permission)).booleanValue();
    }
     if (this.perms.getGroup(group) == null)
       return false; 
     if (this.perms.getGroup(group).getInfo() == null)
       return false; 
     if (this.perms.getGroup(group).getInfo().getPermissions() == null) {
       return false;
    }
     return ((Boolean)this.perms.getGroup(group).getInfo().getPermissions().get(permission)).booleanValue();
  }

    @Override
  public boolean groupAdd(String world, String group, String permission) {
     if (world != null) {
       permission = world + ":" + permission;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions group setperm " + group + " " + permission + " true");
  }

    @Override
  public boolean groupRemove(String world, String group, String permission) {
     if (world != null) {
       permission = world + ":" + permission;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions group unsetperm " + group + " " + permission);
  }

    @Override
  public boolean playerInGroup(String world, String player, String group) {
     if (world != null) {
       for (Group group1 : this.perms.getPlayerInfo(player).getGroups()) {
         if (group1.getName().equals(group)) {
           return group1.getInfo().getWorlds().contains(world);
        }
      } 
       return false;
    } 
     Group g = this.perms.getGroup(group);
     if (g == null) {
       return false;
    }
     return g.getPlayers().contains(player);
  }

    @Override
  public boolean playerAddGroup(String world, String player, String group) {
     if (world != null) {
       return false;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions player addgroup " + player + " " + group);
  }

    @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
     if (world != null) {
       return false;
    }
     return this.plugin.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "permissions player removegroup " + player + " " + group);
  }

    @Override
  public String[] getPlayerGroups(String world, String player) {
     List<String> groupList = new ArrayList<>();
     PermissionInfo info = this.perms.getPlayerInfo(player);
     if (world != null && info != null) {
       for (Group group : this.perms.getPlayerInfo(player).getGroups()) {
         if (group.getInfo().getWorlds().contains(world)) {
           groupList.add(group.getName());
        }
      } 
       return groupList.<String>toArray(new String[0]);
    } 
     if (info != null) {
       for (Group group : info.getGroups()) {
         groupList.add(group.getName());
      }
    }
     return groupList.<String>toArray(new String[0]);
  }

    @Override
  public String getPrimaryGroup(String world, String player) {
     if (this.perms.getPlayerInfo(player) == null)
       return null; 
     if (this.perms.getPlayerInfo(player).getGroups() != null && !this.perms.getPlayerInfo(player).getGroups().isEmpty()) {
       return ((Group)this.perms.getPlayerInfo(player).getGroups().get(0)).getName();
    }
     return null;
  }

    @Override
  public String[] getGroups() {
     List<String> groupNames = new ArrayList<>();
     for (Group group : this.perms.getAllGroups()) {
       groupNames.add(group.getName());
    }
    
     return groupNames.<String>toArray(new String[0]);
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }
}