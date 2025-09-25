package net.milkbowl.vault.permission.plugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.totalpermissions.PermissionManager;
import net.ae97.totalpermissions.TotalPermissions;
import net.ae97.totalpermissions.permission.PermissionGroup;
import net.ae97.totalpermissions.permission.PermissionUser;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;














public class Permission_TotalPermissions
  extends Permission
{
   private final String name = "TotalPermissions";
  private PermissionManager manager;
  private TotalPermissions totalperms;
  
  public Permission_TotalPermissions(Plugin pl) {
     this.plugin = pl;
  }
  
  public class PermissionServerListener
    implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (Permission_TotalPermissions.this.manager == null || Permission_TotalPermissions.this.totalperms == null) {
         Plugin permPlugin = event.getPlugin();
         if (permPlugin.getDescription().getName().equals("TotalPermissions")) {
           Permission_TotalPermissions.this.totalperms = (TotalPermissions)permPlugin;
           Permission_TotalPermissions.this.manager = Permission_TotalPermissions.this.totalperms.getManager();
           Permission_TotalPermissions.log.info(String.format("[%s][Permission] %s hooked.", plugin.getDescription().getName(), "TotalPermissions"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (Permission_TotalPermissions.this.manager != null && 
         event.getPlugin().getDescription().getName().equals("TotalPermissions")) {
         Permission_TotalPermissions.this.totalperms = null;
         Permission_TotalPermissions.this.manager = null;
         Permission_TotalPermissions.log.info(String.format("[%s][Permission] %s un-hooked.", plugin.getDescription().getName(), "TotalPermissions"));
      } 
    }
  }


    @Override
  public String getName() {
     return "TotalPermissions";
  }

    @Override
  public boolean isEnabled() {
     return (this.plugin != null && this.plugin.isEnabled() && this.totalperms != null && this.totalperms.isEnabled());
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }

    @Override
  public boolean playerHas(String world, String player, String permission) {
     PermissionUser permissionUser = this.manager.getUser(player);
     return permissionUser.has(permission, world);
  }

    @Override
  public boolean playerAdd(String world, String player, String permission) {
    try {
       PermissionUser permissionUser = this.manager.getUser(player);
       permissionUser.addPerm(permission, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public boolean playerRemove(String world, String player, String permission) {
    try {
       PermissionUser permissionUser = this.manager.getUser(player);
       permissionUser.remPerm(permission, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public boolean groupHas(String world, String group, String permission) {
     PermissionGroup permissionGroup = this.manager.getGroup(group);
     return permissionGroup.has(permission, world);
  }

    @Override
  public boolean groupAdd(String world, String group, String permission) {
    try {
       PermissionGroup permissionGroup = this.manager.getGroup(group);
       permissionGroup.addPerm(permission, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public boolean groupRemove(String world, String group, String permission) {
    try {
       PermissionGroup permissionGroup = this.manager.getGroup(group);
       permissionGroup.remPerm(permission, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public boolean playerInGroup(String world, String player, String group) {
     PermissionUser user = this.manager.getUser(player);
     List<String> groups = user.getGroups(world);
     return groups.contains(group);
  }

    @Override
  public boolean playerAddGroup(String world, String player, String group) {
    try {
       PermissionUser user = this.manager.getUser(player);
       user.addGroup(group, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
    try {
       PermissionUser user = this.manager.getUser(player);
       user.remGroup(group, world);
       return true;
     } catch (IOException ex) {
       this.plugin.getLogger().log(Level.SEVERE, 
           String.format("[%s] An error occured while saving perms", new Object[] { this.totalperms.getDescription().getName() }), ex);
       return false;
    } 
  }

    @Override
  public String[] getPlayerGroups(String world, String player) {
     PermissionUser user = this.manager.getUser(player);
     List<String> groups = user.getGroups(world);
     if (groups == null) {
       groups = new ArrayList<>();
    }
     return groups.<String>toArray(new String[groups.size()]);
  }

    @Override
  public String getPrimaryGroup(String world, String player) {
     String[] groups = getPlayerGroups(world, player);
     if (groups.length == 0) {
       return "";
    }
     return groups[0];
  }


    @Override
  public String[] getGroups() {
     return this.manager.getGroups();
  }
}