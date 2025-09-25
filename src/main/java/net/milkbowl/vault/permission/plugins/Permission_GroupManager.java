package net.milkbowl.vault.permission.plugins;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.dataholder.WorldDataHolder;
import org.anjocaido.groupmanager.localization.Messages;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;


public class Permission_GroupManager
  extends Permission
{
   private final String name = "GroupManager";
  private GroupManager groupManager;
  
  public Permission_GroupManager(Plugin plugin) {
     this.plugin = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
     if (this.groupManager == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("GroupManager");
       if (perms != null && perms.isEnabled()) {
         this.groupManager = (GroupManager)perms;
         log.info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), "GroupManager" }));
      } 
    } 
  }
  
  public class PermissionServerListener implements Listener {
     Permission_GroupManager permission = null;
    
    public PermissionServerListener(Permission_GroupManager permission) {
       this.permission = permission;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.permission.groupManager == null) {
         Plugin p = event.getPlugin();
         if (p.getDescription().getName().equals("GroupManager")) {
           this.permission.groupManager = (GroupManager)p;
           Permission_GroupManager.log.info(String.format("[%s][Permission] %s hooked.", p.getDescription().getName(), "GroupManager"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.permission.groupManager != null && 
         event.getPlugin().getDescription().getName().equals("GroupManager")) {
         this.permission.groupManager = null;
         Permission_GroupManager.log.info(String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), "GroupManager"));
      } 
    }
  }


    @Override
  public String getName() {
      return "GroupManager";
  }

    @Override
  public boolean isEnabled() {
     return (this.groupManager != null && this.groupManager.isEnabled());
  }

    @Override
  public boolean playerHas(String worldName, String playerName, String permission) {
    AnjoPermissionsHandler handler;
     if (worldName == null) {
       handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      
       handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
     if (handler == null) {
       return false;
    }
     return handler.permission(playerName, permission);
  }

    @Override
  public boolean playerAdd(String worldName, String playerName, String permission) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getWorldDataByPlayerName(playerName);
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
    
     User user = owh.getUser(playerName);
     if (user == null) {
       return false;
    }
    
     user.addPermission(permission);
     Player p = Bukkit.getPlayer(playerName);
     if (p != null) {
       GroupManager.getBukkitPermissions().updatePermissions(p);
    }
     return true;
  }

    @Override
  public boolean playerRemove(String worldName, String playerName, String permission) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getWorldDataByPlayerName(playerName);
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
    
     User user = owh.getUser(playerName);
     if (user == null) {
       return false;
    }
    
     user.removePermission(permission);
     Player p = Bukkit.getPlayer(playerName);
     if (p != null) {
       GroupManager.getBukkitPermissions().updatePermissions(p);
    }
     return true;
  }

    @Override
  public boolean groupHas(String worldName, String groupName, String permission) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getDefaultWorld();
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
    
     Group group = owh.getGroup(groupName);
     if (group == null) {
       return false;
    }
    
     return group.hasSamePermissionNode(permission);
  }

    @Override
  public boolean groupAdd(String worldName, String groupName, String permission) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getDefaultWorld();
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
    
     Group group = owh.getGroup(groupName);
     if (group == null) {
       return false;
    }
    
     group.addPermission(permission);
     return true;
  }

    @Override
  public boolean groupRemove(String worldName, String groupName, String permission) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getDefaultWorld();
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
    
     Group group = owh.getGroup(groupName);
     if (group == null) {
       return false;
    }
    
     group.removePermission(permission);
     return true;
  }

    @Override
  public boolean playerInGroup(String worldName, String playerName, String groupName) {
    AnjoPermissionsHandler handler;
     if (worldName == null) {
       handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
       handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
     if (handler == null) {
       return false;
    }
     return handler.inGroup(playerName, groupName);
  }

    @Override
  public boolean playerAddGroup(String worldName, String playerName, String groupName) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getWorldDataByPlayerName(playerName);
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
     User user = owh.getUser(playerName);
     if (user == null) {
       return false;
    }
     Group group = owh.getGroup(groupName);
     if (group == null) {
       return false;
    }
     if (user.getGroup().equals(owh.getDefaultGroup())) {
       user.setGroup(group);
     } else if (group.getInherits().contains(user.getGroup().getName().toLowerCase())) {
       user.setGroup(group);
    } else {
       user.addSubGroup(group);
    } 
     Player p = Bukkit.getPlayer(playerName);
     if (p != null) {
       GroupManager.getBukkitPermissions().updatePermissions(p);
    }
     return true;
  }

    @Override
  public boolean playerRemoveGroup(String worldName, String playerName, String groupName) {
    OverloadedWorldHolder owh;
     if (worldName == null) {
       owh = this.groupManager.getWorldsHolder().getWorldDataByPlayerName(playerName);
    } else {
       owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
     if (owh == null) {
       return false;
    }
     User user = owh.getUser(playerName);
     if (user == null) {
       return false;
    }
     boolean success = false;
     if (user.getGroup().getName().equalsIgnoreCase(groupName)) {
       user.setGroup(owh.getDefaultGroup());
       success = true;
    } else {
       Group group = owh.getGroup(groupName);
       if (group != null) {
         success = user.removeSubGroup(group);
      }
    } 
     if (success) {
       Player p = Bukkit.getPlayer(playerName);
       if (p != null) {
         GroupManager.getBukkitPermissions().updatePermissions(p);
      }
    } 
     return success;
  }

    @Override
  public String[] getPlayerGroups(String worldName, String playerName) {
    AnjoPermissionsHandler handler;
     if (worldName == null) {
       handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
       handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
     if (handler == null) {
       return null;
    }
     return handler.getGroups(playerName);
  }

    @Override
  public String getPrimaryGroup(String worldName, String playerName) {
    AnjoPermissionsHandler handler;
     if (worldName == null) {
       handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
       handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
     if (handler == null) {
       return null;
    }
     return handler.getGroup(playerName);
  }

    @Override
  public String[] getGroups() {
     Set<String> groupNames = new HashSet<>();
     for (World world : Bukkit.getServer().getWorlds()) {
       OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getWorldData(world.getName());
       if (owh == null) {
        continue;
      }
       Collection<Group> groups = owh.getGroupList();
       if (groups == null) {
        continue;
      }
       for (Group group : groups) {
         groupNames.add(group.getName());
      }
    } 
     return groupNames.toArray(new String[0]);
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }

  //Methods added for CustomVault Update

    @Override
  public String[] getPlayerPermissions(String world, OfflinePlayer player) {
      OverloadedWorldHolder owh = groupManager.getWorldsHolder().getWorldData(world);

      if (owh == null) {
          owh = groupManager.getWorldsHolder().getDefaultWorld();
      }

      User user = owh.getUser(player.getUniqueId().toString());

      if (user == null) {
          return new String[0];
      }

      return user.getPermissions().keySet().toArray(new String[0]);
  }

    @Override
  public String[] getPlayerPermissions(World world, OfflinePlayer player) {
      return getPlayerPermissions(world.getName(), player);
  }

    @Override
  public String[] getPlayerPermissions(OfflinePlayer player) {
      if (player.isOnline()) {
          return getPlayerPermissions(((Player) player).getWorld().getName(), player);
      }
      return getPlayerPermissions(groupManager.getWorldsHolder().getDefaultWorld().getName(), player);
  }

    @Override
  public String[] getPlayerPermissions(World world, Player player) {
      return getPlayerPermissions(world.getName(), (OfflinePlayer)player);
  }

    @Override
  public String[] getGroupPermissions(String world, String group) {
      OverloadedWorldHolder owh = groupManager.getWorldsHolder().getWorldData(world);

      if (owh == null) {
          owh = groupManager.getWorldsHolder().getDefaultWorld();
      }

      Group g = owh.getGroup(group);

      if (g == null) {
          return new String[0];
      }

      return g.getPermissions().keySet().toArray(new String[0]);
  }

    @Override
  public String[] getGroupPermissions(World world, String groupName) {
      return getGroupPermissions(world.getName(), groupName);
  }

    @Override
  public String[] getGroupPermissions(String groupName) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getDefaultWorld();
      Group group = owh.getGroup(groupName);

      if (group == null) {
          return new String[0];
      }

      return group.getPermissions().keySet().toArray(new String[0]);
  }

    @Override
  public String[] getGroupParents(String world, String group) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getWorldData(world);
      if (owh == null) {
          owh = this.groupManager.getWorldsHolder().getDefaultWorld();
      }
      Group g = owh.getGroup(group);
      if (g == null) {
          return new String[0];
          }
      return g.getInherits().toArray(new String[0]);
  }

    @Override
  public String[] getGroupParents(World world, String group) {
      return getGroupParents(world.getName(), group);
  }

    @Override
  public String[] getGroupParents(String group) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getDefaultWorld();
      Group g = owh.getGroup(group);
      if (g == null) {
          return new String[0];
          }
      return g.getInherits().toArray(new String[0]);
  }

    @Override
  public boolean groupCreate(String worldName, String group, boolean isDefault) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
      if (owh == null) {
        return false;
      }

      return (owh.createGroup(group) != null);
  }

    @Override
  public boolean groupCreate(String group, boolean isDefault) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getDefaultWorld();
      if (owh == null) {
          return false;
      }

      return (owh.createGroup(group) != null);
  }

    @Override
  public boolean groupCreate(String group) {
      return groupCreate(Bukkit.getWorlds().getFirst().getName(), group, false);
  }

    @Override
  public boolean groupDelete(String worldName, String group) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
      if (owh == null) {
          return false;
      }
      Group g = owh.getGroup(group);
      if (g == null) {
          return false;
      }
      return owh.removeGroup(g.getName());
  }

    @Override
  public boolean groupDelete(String group) {
      OverloadedWorldHolder owh = this.groupManager.getWorldsHolder().getDefaultWorld();
      if (owh == null) {
          return false;
      }

      Group g = owh.getGroup(group);
      if (g == null) {
          return false;
      }

      return owh.removeGroup(g.getName());
  }
}