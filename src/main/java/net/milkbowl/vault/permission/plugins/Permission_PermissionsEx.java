package net.milkbowl.vault.permission.plugins;

import java.util.List;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.bukkit.commands.PermissionsCommand;

import static ru.tehkode.permissions.bukkit.commands.PermissionsCommand.*;

/*     */
public class Permission_PermissionsEx
  extends Permission
{
   private final String name = "PermissionsEx";
   private PermissionsEx permission = null;
  
  public Permission_PermissionsEx(Plugin plugin) {
     this.plugin = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
     if (this.permission == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
       if (perms != null && 
         perms.isEnabled()) {
        try {
           if (Double.valueOf(perms.getDescription().getVersion()).doubleValue() < 1.16D) {
             log.info(String.format("[%s][Permission] %s below 1.16 is not compatible with Vault! Falling back to SuperPerms only mode. PLEASE UPDATE!", new Object[] { plugin.getDescription().getName(), "PermissionsEx" }));
          }
         } catch (NumberFormatException numberFormatException) {}

        
         this.permission = (PermissionsEx)perms;
         log.info(String.format("[%s][Permission] %s hooked.", plugin.getDescription().getName(), "PermissionsEx"));
      } 
    } 
  }


    @Override
  public boolean isEnabled() {
     if (this.permission == null) {
       return false;
    }
     return this.permission.isEnabled();
  }
  
  public class PermissionServerListener
    implements Listener {
     Permission_PermissionsEx permission = null;
    
    public PermissionServerListener(Permission_PermissionsEx permission) {
       this.permission = permission;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.permission.permission == null) {
         Plugin perms = event.getPlugin();
         if (perms.getDescription().getName().equals("PermissionsEx")) {
          try {
             if (Double.valueOf(perms.getDescription().getVersion()).doubleValue() < 1.16D) {
               Permission_PermissionsEx.log.info(String.format("[%s][Permission] %s below 1.16 is not compatible with Vault! Falling back to SuperPerms only mode. PLEASE UPDATE!", plugin.getDescription().getName(), "PermissionsEx"));
              return;
            } 
           } catch (NumberFormatException numberFormatException) {}

          
           this.permission.permission = (PermissionsEx)perms;
           Permission_PermissionsEx.log.info(String.format("[%s][Permission] %s hooked.", plugin.getDescription().getName(), "PermissionsEx"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.permission.permission != null && 
         event.getPlugin().getDescription().getName().equals("PermissionsEx")) {
         this.permission.permission = null;
         Permission_PermissionsEx.log.info(String.format("[%s][Permission] %s un-hooked.", plugin.getDescription().getName(), "PermissionsEx"));
      } 
    }
  }


    @Override
  public String getName() {
     return "PermissionsEx";
  }

    @Override
  public boolean playerInGroup(String worldName, OfflinePlayer op, String groupName) {
     PermissionUser user = getUser(op);
     if (user == null) {
       return false;
    }
     return user.inGroup(groupName, worldName);
  }

    @Override
  public boolean playerInGroup(String worldName, String playerName, String groupName) {
     return PermissionsEx.getPermissionManager().getUser(playerName).inGroup(groupName, worldName);
  }

    @Override
  public boolean playerAddGroup(String worldName, OfflinePlayer op, String groupName) {
     PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
     PermissionUser user = getUser(op);
     if (group == null || user == null) {
       return false;
    }
     user.addGroup(groupName, worldName);
     return true;
  }


    @Override
  public boolean playerAddGroup(String worldName, String playerName, String groupName) {
     PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
     PermissionUser user = PermissionsEx.getPermissionManager().getUser(playerName);
     if (group == null || user == null) {
       return false;
    }
     user.addGroup(groupName, worldName);
     return true;
  }


    @Override
  public boolean playerRemoveGroup(String worldName, OfflinePlayer op, String groupName) {
     PermissionUser user = getUser(op);
     user.removeGroup(groupName, worldName);
     return true;
  }

    @Override
  public boolean playerRemoveGroup(String worldName, String playerName, String groupName) {
     PermissionsEx.getPermissionManager().getUser(playerName).removeGroup(groupName, worldName);
     return true;
  }

    @Override
  public boolean playerAdd(String worldName, OfflinePlayer op, String permission) {
     PermissionUser user = getUser(op);
     if (user == null) {
       return false;
    }
     user.addPermission(permission, worldName);
     return true;
  }


    @Override
  public boolean playerAdd(String worldName, String playerName, String permission) {
     PermissionUser user = getUser(playerName);
     if (user == null) {
       return false;
    }
     user.addPermission(permission, worldName);
     return true;
  }


    @Override
  public boolean playerRemove(String worldName, OfflinePlayer op, String permission) {
     PermissionUser user = getUser(op);
     if (user == null) {
       return false;
    }
     user.removePermission(permission, worldName);
     return true;
  }


    @Override
  public boolean playerRemove(String worldName, String playerName, String permission) {
     PermissionUser user = getUser(playerName);
     if (user == null) {
       return false;
    }
     user.removePermission(permission, worldName);
     return true;
  }


    @Override
  public boolean groupAdd(String worldName, String groupName, String permission) {
     PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
     if (group == null) {
       return false;
    }
     group.addPermission(permission, worldName);
     return true;
  }


    @Override
  public boolean groupRemove(String worldName, String groupName, String permission) {
     PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
     if (group == null) {
       return false;
    }
     group.removePermission(permission, worldName);
     return true;
  }


    @Override
  public boolean groupHas(String worldName, String groupName, String permission) {
     PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
     if (group == null) {
       return false;
    }
     return group.has(permission, worldName);
  }

  private PermissionUser getUser(OfflinePlayer op) {
     return PermissionsEx.getPermissionManager().getUser(op.getUniqueId());
  }
  
  private PermissionUser getUser(String playerName) {
     return PermissionsEx.getPermissionManager().getUser(playerName);
  }

    @Override
  public String[] getPlayerGroups(String world, OfflinePlayer op) {
     PermissionUser user = getUser(op);
     return (user == null) ? null : (String[])user.getParentIdentifiers(world).toArray((Object[])new String[0]);
  }

    @Override
  public String[] getPlayerGroups(String world, String playerName) {
     PermissionUser user = getUser(playerName);
     return (user == null) ? null : (String[])user.getParentIdentifiers(world).toArray((Object[])new String[0]);
  }

    @Override
  public String getPrimaryGroup(String world, OfflinePlayer op) {
     PermissionUser user = getUser(op);
     if (user == null)
       return null; 
     if (user.getParentIdentifiers(world).size() > 0) {
       return user.getParentIdentifiers(world).get(0);
    }
     return null;
  }


    @Override
  public String getPrimaryGroup(String world, String playerName) {
     PermissionUser user = PermissionsEx.getPermissionManager().getUser(playerName);
     if (user == null)
       return null; 
     if (user.getParentIdentifiers(world).size() > 0) {
       return user.getParentIdentifiers(world).get(0);
    }
     return null;
  }


    @Override
  public boolean playerHas(String worldName, OfflinePlayer op, String permission) {
     PermissionUser user = getUser(op);
     if (user != null) {
       return user.has(permission, worldName);
    }
     return false;
  }


    @Override
  public boolean playerHas(String worldName, String playerName, String permission) {
     PermissionUser user = getUser(playerName);
     if (user != null) {
       return user.has(permission, worldName);
    }
     return false;
  }



    @Override
  public boolean playerAddTransient(String worldName, Player player, String permission) {
     PermissionUser pPlayer = getUser((OfflinePlayer)player);
     if (pPlayer != null) {
       pPlayer.addTimedPermission(permission, worldName, 0);
       return true;
    } 
     return false;
  }


    @Override
  public boolean playerAddTransient(Player player, String permission) {
     return playerAddTransient((String)null, player, permission);
  }


    @Override
  public boolean playerRemoveTransient(Player player, String permission) {
     return playerRemoveTransient((String)null, player, permission);
  }

    @Override
  public boolean playerRemoveTransient(String worldName, Player player, String permission) {
     PermissionUser pPlayer = getUser((OfflinePlayer)player);
     if (pPlayer != null) {
       pPlayer.removeTimedPermission(permission, worldName);
       return true;
    } 
     return false;
  }


    @Override
  public String[] getGroups() {
     List<PermissionGroup> groups = PermissionsEx.getPermissionManager().getGroupList();
     if (groups == null || groups.isEmpty()) {
       return null;
    }
     String[] groupNames = new String[groups.size()];
     for (int i = 0; i < groups.size(); i++) {
       groupNames[i] = ((PermissionGroup)groups.get(i)).getName();
    }
     return groupNames;
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }


  //Extra Methods Implemented in Custom Vault

    @Override
    public boolean playerAddTimedPermission(String world, OfflinePlayer player, String permission, long time) {
        PermissionUser user = getUser(player.getName());
        int lifetime = 0;
        if (user == null)
            return false;
        if (time > 0)
            lifetime = (int) time;
        user.addTimedPermission(permission, world, lifetime);
        return true;
    }

    @Override
    public boolean playerRemoveTimedPermission(String world, OfflinePlayer player, String permission) {
        PermissionUser user = getUser(player.getName());
        if (user == null) return false;
        user.removeTimedPermission(permission, world);
        return true;
    }

    @Override
    public String[] getPlayerAllPermissions(OfflinePlayer player) {
        PermissionUser user = getUser(player.getName());
        if (user == null) return new String[0];
        return user.getPermissions(null).toArray(new String[0]);
    }

    @Override
    public String[] getPlayerOwnPermissions(String world, OfflinePlayer player) {
        PermissionUser user = getUser(player.getName());
        if (user == null) return new String[0];
        return getUserOptionList(user, "permissions");
    }

    @Override
    public String[] getPlayerWorldPermissions(String world, OfflinePlayer player) {
        PermissionUser user = getUser(player.getName());
        if (user == null) return new String[0];
        return getUserOptionList(user, "worlds." + world + ".permissions");
    }

    @Override
    public boolean groupAddTimedPermission(String world, String groupName, String permission, long time) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        int lifetime = 0;
        if (time > 0)
            lifetime = (int) time;
        group.addTimedPermission(permission, world, lifetime);
        return true;
    }

    @Override
    public boolean groupRemoveTimedPermission(String world, String groupName, String permission) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        group.removeTimedPermission(permission, world);
        return true;
    }

    @Override
    public String[] getGroupAllPermissions(String world, String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return new String[0];
        return group.getPermissions(world).toArray(new String[0]);
    }

    @Override
    public String[] getGroupOwnPermissions(String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return new String[0];
        return getGroupOptionList(groupName, "permissions");
    }

    @Override
    public String[] getGroupWorldPermissions(String world, String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return new String[0];
        return getGroupOptionList(groupName, "worlds." + world + ".permissions");
    }

    @Override
    public String[] getGroupParents(String world, String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return new String[0];
        return group.getParentIdentifiers(world).toArray(new String[0]);
    }

    @Override
    public String getGroupPrefix(String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return null;
        return group.getOwnPrefix();
    }

    @Override
    public boolean setGroupPrefix(String groupName, String prefix) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        group.setPrefix(null, prefix);
        return true;
    }

    @Override
    public String getGroupSuffix(String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return null;
        return group.getOwnSuffix();
    }

    @Override
    public boolean setGroupSuffix(String groupName, String suffix) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        group.setSuffix(null, suffix);
        return true;
    }

    @Override
    public boolean groupCreate(String world, String groupName, boolean isDefault) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        else if (!group.isVirtual()) return false;
        group.save();
        return true;

    }

    @Override
    public boolean groupDelete(String world, String groupName) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        if (group == null) return false;
        group.remove();
        permission.getPermissionsManager().resetGroup(group.getIdentifier());
        return true;
    }

    @Override
    public String getDefaultGroup(String world) {
        if (PermissionsEx.getPermissionManager().getDefaultGroups(world) == null) return null;
        return PermissionsEx.getPermissionManager().getDefaultGroups(world).get(0).getName();
    }

    private String[] getUserOptionList(PermissionUser user, String option) {
      return permission.getConfiguration().getBackendConfig("file").getStringList("users." + user.getPlayer().getUniqueId() + "." + option).toArray(new String[0]);
    }

    private String[] getGroupOptionList(String group, String option) {
      return permission.getConfiguration().getBackendConfig("file").getStringList("groups." + group.toLowerCase() + "." + option).toArray(new String[0]);
    }


}