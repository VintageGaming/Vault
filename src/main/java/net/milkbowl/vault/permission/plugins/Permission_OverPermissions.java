package net.milkbowl.vault.permission.plugins;

import com.overmc.overpermissions.api.GroupManager;
import com.overmc.overpermissions.api.PermissionGroup;
import com.overmc.overpermissions.api.PermissionUser;
import com.overmc.overpermissions.api.UserManager;
import com.overmc.overpermissions.internal.OverPermissions;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;













public class Permission_OverPermissions
  extends Permission
{
  private OverPermissions overPerms;
  private UserManager userManager;
  private GroupManager groupManager;
  
  public Permission_OverPermissions(Plugin plugin) {
     this.plugin = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);
    
     if (this.overPerms == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("OverPermissions");
       if (perms != null && perms.isEnabled()) {
         this.overPerms = (OverPermissions)perms;
         this.userManager = this.overPerms.getUserManager();
         this.groupManager = this.overPerms.getGroupManager();
         log.info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), "OverPermissions" }));
      } 
    } 
  }

    @Override
  public String getName() {
     return "OverPermissions";
  }

    @Override
  public boolean isEnabled() {
     return (this.overPerms != null && this.overPerms.isEnabled());
  }

    @Override
  public boolean playerHas(String worldName, String playerName, String permission) {
     if (!this.userManager.doesUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).getPermission(permission, worldName);
  }

    @Override
  public boolean playerAdd(String worldName, String playerName, String permission) {
     if (!this.userManager.canUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).addPermissionNode(permission, worldName);
  }

    @Override
  public boolean playerRemove(String worldName, String playerName, String permission) {
     if (!this.userManager.canUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).removePermissionNode(permission, worldName);
  }

    @Override
  public boolean groupHas(String worldName, String groupName, String permission) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     return this.groupManager.getGroup(groupName).getPermission(permission, worldName);
  }

    @Override
  public boolean groupAdd(String worldName, String groupName, String permission) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     if (worldName == null) {
       return this.groupManager.getGroup(groupName).addGlobalPermissionNode(permission);
    }
     return this.groupManager.getGroup(groupName).addPermissionNode(permission, worldName);
  }


    @Override
  public boolean groupRemove(String worldName, String groupName, String permission) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     if (worldName == null) {
       return this.groupManager.getGroup(groupName).removeGlobalPermissionNode(permission);
    }
     return this.groupManager.getGroup(groupName).removePermissionNode(permission, worldName);
  }


    @Override
  public boolean playerInGroup(String worldName, String playerName, String groupName) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     if (!this.userManager.doesUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).getAllParents().contains(this.groupManager.getGroup(groupName));
  }

    @Override
  public boolean playerAddGroup(String worldName, String playerName, String groupName) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     if (!this.userManager.canUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).addParent(this.groupManager.getGroup(groupName));
  }

    @Override
  public boolean playerRemoveGroup(String worldName, String playerName, String groupName) {
     if (!this.groupManager.doesGroupExist(groupName)) {
       return false;
    }
     if (!this.userManager.canUserExist(playerName)) {
       return false;
    }
     return this.userManager.getPermissionUser(playerName).removeParent(this.groupManager.getGroup(groupName));
  }

    @Override
  public String[] getPlayerGroups(String worldName, String playerName) {
     ArrayList<String> ret = new ArrayList<>();
     if (!this.userManager.doesUserExist(playerName)) {
       return new String[0];
    }
     PermissionUser user = this.userManager.getPermissionUser(playerName);
     for (PermissionGroup parent : user.getAllParents()) {
       ret.add(parent.getName());
    }
     return ret.<String>toArray(new String[ret.size()]);
  }

    @Override
  public String getPrimaryGroup(String worldName, String playerName) {
     String[] playerGroups = getPlayerGroups(worldName, playerName);
     if (playerGroups.length == 0) {
       return null;
    }
     return playerGroups[0];
  }

    @Override
  public String[] getGroups() {
     ArrayList<String> groupNames = new ArrayList<>();
     for (PermissionGroup s : this.groupManager.getGroups()) {
       groupNames.add(s.getName());
    }
     return groupNames.<String>toArray(new String[groupNames.size()]);
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }

  public class PermissionServerListener
    implements Listener {
     Permission_OverPermissions permission = null;
    
    public PermissionServerListener(Permission_OverPermissions permission) {
       this.permission = permission;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.permission.overPerms == null) {
         Plugin perms = Permission_OverPermissions.this.plugin.getServer().getPluginManager().getPlugin("OverPermissions");
         if (perms != null) {
           this.permission.overPerms = (OverPermissions)perms;
           Permission_OverPermissions.log.info(
               String.format("[%s][Permission] %s hooked.", perms.getDescription().getName(), "OverPermissions"));
        } 
      } 
    }

    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.permission.overPerms != null && event
         .getPlugin().getDescription().getName().equals("OverPermissions")) {
         this.permission.overPerms = null;
         Permission_OverPermissions.log.info(
             String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), "OverPermissions"));
      } 
    }
  }
}