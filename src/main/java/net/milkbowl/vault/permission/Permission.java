package net.milkbowl.vault.permission;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;


public abstract class Permission
{
   protected static final Logger log = Logger.getLogger("Minecraft");
   protected Plugin plugin = null;


  public abstract String getName();

  public abstract boolean isEnabled();

  public abstract boolean hasSuperPermsCompat();

  @Deprecated
  public boolean has(String world, @Nonnull String player, @Nonnull String permission) {
     if (world == null) {
       return playerHas((String)null, player, permission);
    }
     return playerHas(world, player, permission);
  }

  @Deprecated
  public boolean has(World world, @Nonnull String player, @Nonnull String permission) {
     if (world == null) {
       return playerHas((String)null, player, permission);
    }
     return playerHas(world.getName(), player, permission);
  }
  
  public boolean has(@Nonnull CommandSender sender, @Nonnull String permission) {
     return sender.hasPermission(permission);
  }

  public boolean has(@Nonnull Player player, @Nonnull String permission) {
     return player.hasPermission(permission);
  }

  public abstract boolean playerHas(String worldName, @Nonnull String player, @Nonnull String permission);
  
  @Deprecated
  public boolean playerHas(World world, @Nonnull String player, @Nonnull String permission) {
     if (world == null) {
       return playerHas((String)null, player, permission);
    }
     return playerHas(world.getName(), player, permission);
  }

  
  public boolean playerHas(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
     if (world == null) {
       return has((String)null, player.getName(), permission);
    }
     return has(world, player.getName(), permission);
  }

  public boolean playerHas(@Nonnull Player player, @Nonnull String permission) {
     return has(player, permission);
  }

  public abstract boolean playerAdd(String worldName, @Nonnull String player, @Nonnull String permission);

  @Deprecated
  public boolean playerAdd(World world, @Nonnull String player, @Nonnull String permission) {
     if (world == null) {
       return playerAdd((String)null, player, permission);
    }
     return playerAdd(world.getName(), player, permission);
  }

  public boolean playerAdd(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
     if (world == null) {
       return playerAdd((String)null, player.getName(), permission);
    }
     return playerAdd(world, player.getName(), permission);
  }

  public boolean playerAdd(@Nonnull Player player, @Nonnull String permission) {
     return playerAdd(player.getWorld().getName(), (OfflinePlayer)player, permission);
  }

  public boolean playerAddTransient(@Nonnull OfflinePlayer player, @Nonnull String permission) throws UnsupportedOperationException {
     if (player.isOnline()) {
       return playerAddTransient((Player)player, permission);
    }
     throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
  }

  public boolean playerAddTransient(@Nonnull Player player, @Nonnull String permission) {
     for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
       if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(this.plugin)) {
         paInfo.getAttachment().setPermission(permission, true);
         return true;
      } 
    } 
    
     PermissionAttachment attach = player.addAttachment(this.plugin);
     attach.setPermission(permission, true);
    
     return true;
  }

  public boolean playerAddTransient(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
     return playerAddTransient(player, permission);
  }

  public boolean playerAddTransient(String worldName, @Nonnull Player player, @Nonnull String permission) {
     return playerAddTransient(player, permission);
  }

  public boolean playerRemoveTransient(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
     return playerRemoveTransient(player, permission);
  }

  public boolean playerRemoveTransient(String worldName, @Nonnull Player player, @Nonnull String permission) {
     return playerRemoveTransient((OfflinePlayer)player, permission);
  }

  @Deprecated
  public abstract boolean playerRemove(String worldName, @Nonnull String player, @Nonnull String permission);

  public boolean playerRemove(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
     if (world == null) {
       return playerRemove((String)null, player.getName(), permission);
    }
     return playerRemove(world, player.getName(), permission);
  }

  @Deprecated
  public boolean playerRemove(World world, @Nonnull String player, @Nonnull String permission) {
     if (world == null) {
       return playerRemove((String)null, player, permission);
    }
     return playerRemove(world.getName(), player, permission);
  }

  public boolean playerRemove(@Nonnull Player player, @Nonnull String permission) {
     return playerRemove(player.getWorld().getName(), (OfflinePlayer)player, permission);
  }

  public boolean playerRemoveTransient(@Nonnull OfflinePlayer player, @Nonnull String permission) {
     if (player.isOnline()) {
       return playerRemoveTransient((Player)player, permission);
    }
     return false;
  }

  public boolean playerRemoveTransient(@Nonnull Player player, @Nonnull String permission) {
     for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
       if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(this.plugin)) {
         paInfo.getAttachment().unsetPermission(permission);
         return true;
      } 
    } 
     return false;
  }

  public abstract boolean groupHas(String worldName, @Nonnull String group, @Nonnull String permission);

  public boolean groupHas(World world, @Nonnull String group, @Nonnull String permission) {
     if (world == null) {
       return groupHas((String)null, group, permission);
    }
     return groupHas(world.getName(), group, permission);
  }

  public abstract boolean groupAdd(String worldName, @Nonnull String group, @Nonnull String permission);

  public boolean groupAdd(World world, String group, String permission) {
     if (world == null) {
       return groupAdd((String)null, group, permission);
    }
     return groupAdd(world.getName(), group, permission);
  }

  public abstract boolean groupRemove(String worldName, @Nonnull String group, @Nonnull String permission);

  public boolean groupRemove(World world, String group, String permission) {
     if (world == null) {
       return groupRemove((String)null, group, permission);
    }
     return groupRemove(world.getName(), group, permission);
  }

  @Deprecated
  public abstract boolean playerInGroup(String worldName, @Nonnull String player, @Nonnull String group);

  @Deprecated
  public boolean playerInGroup(World world, @Nonnull String player, @Nonnull String group) {
     if (world == null) {
       return playerInGroup((String)null, player, group);
    }
     return playerInGroup(world.getName(), player, group);
  }

  public boolean playerInGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
     if (world == null) {
       return playerInGroup((String)null, player.getName(), group);
    }
     return playerInGroup(world, player.getName(), group);
  }

  public boolean playerInGroup(@Nonnull Player player, @Nonnull String group) {
     return playerInGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
  }

  @Deprecated
  public abstract boolean playerAddGroup(String worldName, @Nonnull String player, @Nonnull String group);

  @Deprecated
  public boolean playerAddGroup(World world, @Nonnull String player, @Nonnull String group) {
     if (world == null) {
       return playerAddGroup((String)null, player, group);
    }
     return playerAddGroup(world.getName(), player, group);
  }

  public boolean playerAddGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
     if (world == null) {
       return playerAddGroup((String)null, player.getName(), group);
    }
     return playerAddGroup(world, player.getName(), group);
  }

  public boolean playerAddGroup(@Nonnull Player player, @Nonnull String group) {
     return playerAddGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
  }

  @Deprecated
  public abstract boolean playerRemoveGroup(String worldName, @Nonnull String player, @Nonnull String group);

  @Deprecated
  public boolean playerRemoveGroup(World world, @Nonnull String player, @Nonnull String group) {
     if (world == null) {
       return playerRemoveGroup((String)null, player, group);
    }
     return playerRemoveGroup(world.getName(), player, group);
  }

  public boolean playerRemoveGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
     if (world == null) {
       return playerRemoveGroup((String)null, player.getName(), group);
    }
     return playerRemoveGroup(world, player.getName(), group);
  }

  public boolean playerRemoveGroup(@Nonnull Player player, @Nonnull String group) {
     return playerRemoveGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
  }

  @Deprecated
  public abstract String[] getPlayerGroups(String worldName, @Nonnull String player);

  @Deprecated
  public String[] getPlayerGroups(World world, @Nonnull String player) {
     if (world == null) {
       return getPlayerGroups((String)null, player);
    }
     return getPlayerGroups(world.getName(), player);
  }

  public String[] getPlayerGroups(String world, @Nonnull OfflinePlayer player) {
     return getPlayerGroups(world, player.getName());
  }

  public String[] getPlayerGroups(@Nonnull Player player) {
     return getPlayerGroups(player.getWorld().getName(), (OfflinePlayer)player);
  }

  @Deprecated
  public abstract String getPrimaryGroup(String worldName, @Nonnull String player);

  @Deprecated
  public String getPrimaryGroup(World world, @Nonnull String player) {
     if (world == null) {
       return getPrimaryGroup((String)null, player);
    }
     return getPrimaryGroup(world.getName(), player);
  }

  public String getPrimaryGroup(String world, @Nonnull OfflinePlayer player) {
     return getPrimaryGroup(world, player.getName());
  }

  public String getPrimaryGroup(@Nonnull Player player) {
     return getPrimaryGroup(player.getWorld().getName(), (OfflinePlayer)player);
  }
  
  public abstract String[] getGroups();
  
  public abstract boolean hasGroupSupport();


  //EXTRA METHODS IMPLEMENTED AFTER VAULT 1.7.3

  public boolean playerAddTimedPermission(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission, long time) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented Player Timed Permissions feature with Vault!");
      return false;
  }

  @Deprecated
  public boolean playerAddTimedPermission(@Nonnull OfflinePlayer player, @Nonnull String permission, long time) {
      if (player.isOnline())
          return playerAddTimedPermission(((Player) player).getWorld().getName(), (OfflinePlayer)player, permission, time);
      return playerAddTimedPermission((String) null, (OfflinePlayer)player, permission, time);
  }

  @Deprecated
  public boolean playerAddTimedPermission(@Nonnull Player player, @Nonnull String permission, long time) {
      return playerAddTimedPermission(player.getWorld().getName(), (OfflinePlayer)player, permission, time);
  }

  @Deprecated
  public boolean playerAddTimedPermission(World world, @Nonnull Player player, @Nonnull String permission, long time) {
      if (world == null)
          return playerAddTimedPermission((String) null, (OfflinePlayer)player, permission, time);
      return playerAddTimedPermission(world.getName(), (OfflinePlayer)player, permission, time);
  }

  public boolean playerRemoveTimedPermission(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented Player Timed Permissions feature with Vault!");
      return false;
  }

  @Deprecated
  public boolean playerRemoveTimedPermission(@Nonnull OfflinePlayer player, @Nonnull String permission) {
      if (player.isOnline())
          return playerRemoveTimedPermission(((Player) player).getWorld().getName(), player, permission);
      return playerRemoveTimedPermission((String) null, player, permission);
  }

  @Deprecated
  public boolean playerRemoveTimedPermission(@Nonnull Player player, @Nonnull String permission) {
      return playerRemoveTimedPermission(player.getWorld().getName(), (OfflinePlayer)player, permission);
  }


  //Added Support for GroupManager
  public String[] getPlayerPermissions(String worldName, @Nonnull OfflinePlayer player) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getPlayerPermissions feature!");
      return new String[1];
  }

  @Deprecated
  public String[] getPlayerPermissions(@Nonnull Player player) {
      return getPlayerPermissions(player.getWorld().getName(), (OfflinePlayer) player);
  }

  @Deprecated
  public String[] getPlayerPermissions(World world, @Nonnull Player player) {
      if (world == null)
          return getPlayerPermissions((String) null, (OfflinePlayer) player);
      return getPlayerPermissions(world.getName(), (OfflinePlayer) player);
  }

  @Deprecated
  public String[] getPlayerPermissions(@Nonnull OfflinePlayer player) {
      return getPlayerPermissions((String) null, player);
  }

  @Deprecated
  public String[] getPlayerPermissions(World world, @Nonnull OfflinePlayer player) {
      if (world == null)
          return getPlayerPermissions((String) null, player);
      return getPlayerPermissions(world.getName(), player);
  }

  //Added Support for GroupManager
  public String[] getGroupPermissions(String worldName, @Nonnull String group) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupWorldPermissions feature!");
      return new String[1];
  }

  @Deprecated
  public String[] getGroupPermissions(@Nonnull String group) {
      return getGroupPermissions((String) null, group);
  }

  @Deprecated
  public String[] getGroupPermissions(World world, @Nonnull String group) {
      if (world == null)
          return getGroupPermissions((String) null, group);
      return getGroupPermissions(world.getName(), group);
  }

  public boolean groupAddTimedPermission(String worldName, @Nonnull String group, @Nonnull String permission, long time) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented Timed Permissions feature with Vault!");
      return false;
  }

  @Deprecated
  public boolean groupAddTimedPermission(World world, @Nonnull String group, @Nonnull String permission, long time) {
      if (world == null)
          return groupAddTimedPermission((String) null, group, permission, time);
      return groupAddTimedPermission(world.getName(), group, permission, time);
  }

  @Deprecated
  public boolean groupAddTimedPermission(@Nonnull String group, @Nonnull String permission, long time) {
      return groupAddTimedPermission((String) null, group, permission, time);
  }

  public boolean groupRemoveTimedPermission(String worldName, @Nonnull String group, @Nonnull String permission) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented Timed Permissions feature with Vault!");
      return false;
  }

  @Deprecated
  public boolean groupRemoveTimedPermission(World world, @Nonnull String group, @Nonnull String permission) {
      if (world == null)
          return groupRemoveTimedPermission((String) null, group, permission);
      return groupRemoveTimedPermission(world.getName(), group, permission);
  }

  //Added Support for GroupManager
  public String[] getGroupParents(String worldName, @Nonnull String group) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupParents feature!");
      return new String[1];
  }

  @Deprecated
  public String[] getGroupParents(World world, @Nonnull String group) {
      if (world == null)
          return getGroupParents((String) null, group);
      return getGroupParents(world.getName(), group);
  }

  public String[] getGroupParents(@Nonnull String group) {
      return getGroupParents((String) null, group);
  }

  public String getGroupPrefix(@Nonnull String group) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupPrefix feature!");
      return null;
  }

  public boolean setGroupPrefix(@Nonnull String group, @Nonnull String prefix) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented setGroupPrefix feature!");
      return false;
  }

  public String getGroupSuffix(@Nonnull String group) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupSuffix feature!");
      return null;
  }

  public boolean setGroupSuffix(@Nonnull String group, @Nonnull String suffix) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented setGroupSuffix feature");
      return false;
  }

  //Added Support for GroupManager
  public boolean groupCreate(String worldName, @Nonnull String group, boolean isDefault) {
      log.severe("Plugin " + plugin.getName() + ", Doesn't allow Vault to Create Groups!");
      return false;
  }

  @Deprecated
  public boolean groupCreate(World world, @Nonnull String group, boolean isDefault) {
      return groupCreate(world.getName(), group, isDefault);
  }

  @Deprecated
  public boolean groupCreate(@Nonnull String group, boolean isDefault) {
      return groupCreate(Bukkit.getWorlds().getFirst().getName(), group, isDefault);
  }

  @Deprecated
  public boolean groupCreate(@Nonnull String group) {
      return groupCreate(Bukkit.getWorlds().getFirst().getName(), group, false);
  }

  //Added Support for GroupManager
  public boolean groupDelete(String worldName, @Nonnull String group) {
      log.severe("Plugin " + plugin.getName() + ", Doesn't allow Vault to Delete Groups!");
      return false;
  }

  @Deprecated
  public boolean groupDelete(World world, @Nonnull String group) {
      if (world == null)
          return groupDelete((String) null, group);
      return groupDelete(world.getName(), group);
  }

  @Deprecated
  public boolean groupDelete(@Nonnull String group) {
      return groupDelete((String) null, group);
  }

  public String getDefaultGroup(@Nonnull String world) {
      log.severe("Plugin " + plugin.getName() + ", has not Implemented getDefaultGroup feature!");
      return null;
  }

  @Deprecated
  public String getDefaultGroup(World world) {
      if (world == null)
          return getDefaultGroup((String) null);
      return getDefaultGroup(world.getName());
  }

}