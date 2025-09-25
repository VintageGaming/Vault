package net.milkbowl.vault.permission.plugins;

import com.nijiko.permissions.Group;
import com.nijiko.permissions.ModularControl;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

















public class Permission_Permissions3
  extends Permission
{
   private String name = "Permissions3";
  private ModularControl perms;
   private Permissions permission = null;
  
  public Permission_Permissions3(Plugin plugin) {
     this.plugin = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(), plugin);

    
     if (this.permission == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("Permissions");
       if (perms == null) {
         plugin.getServer().getPluginManager().getPlugin("vPerms");
         this.name = "vPerms";
      } 
       if (perms != null && 
         perms.isEnabled() && perms.getDescription().getVersion().startsWith("3")) {
         this.permission = (Permissions)perms;
         this.perms = (ModularControl)this.permission.getHandler();
         log.severe("Your permission system is outdated and no longer fully supported! It is highly advised to update!");
         log.info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), this.name }));
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


    @Override
  public boolean playerInGroup(String worldName, String playerName, String groupName) {
     return this.permission.getHandler().inGroup(worldName, playerName, groupName);
  }

  public class PermissionServerListener
    implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (Permission_Permissions3.this.permission == null) {
         Plugin permi = event.getPlugin();
         if ((permi.getDescription().getName().equals("Permissions") || permi.getDescription().getName().equals("vPerms")) && permi.getDescription().getVersion().startsWith("3") && 
           permi.isEnabled()) {
           Permission_Permissions3.this.permission = (Permissions)permi;
           Permission_Permissions3.this.perms = (ModularControl)Permission_Permissions3.this.permission.getHandler();
           Permission_Permissions3.log.info(String.format("[%s][Permission] %s hooked.", permi.getDescription().getName(), permi));
        } 
      } 
    }

    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (Permission_Permissions3.this.permission != null && (
         event.getPlugin().getDescription().getName().equals("Permissions") || event.getPlugin().getDescription().getName().equals("vPerms"))) {
         Permission_Permissions3.this.permission = null;
         Permission_Permissions3.this.perms = null;
         Permission_Permissions3.log.info(String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), event.getPlugin()));
      } 
    }
  }


    @Override
  public String getName() {
     return this.name;
  }

    @Override
  public boolean has(CommandSender sender, String permission) {
     if (sender.isOp() || !(sender instanceof Player)) {
       return true;
    }
     return has(((Player)sender).getWorld().getName(), sender.getName(), permission);
  }


    @Override
  public boolean has(Player player, String permission) {
     return has(player.getWorld().getName(), player.getName(), permission);
  }

    @Override
  public boolean playerAddGroup(String worldName, String playerName, String groupName) {
     if (worldName == null) {
       worldName = "*";
    }
    
     Group g = this.perms.getGroupObject(worldName, groupName);
     if (g == null) {
       return false;
    }
    try {
       this.perms.safeGetUser(worldName, playerName).addParent(g);
     } catch (Exception e) {
       e.printStackTrace();
       return false;
    } 
     return true;
  }

    @Override
  public boolean playerRemoveGroup(String worldName, String playerName, String groupName) {
     if (worldName == null) {
       worldName = "*";
    }
    
     Group g = this.perms.getGroupObject(worldName, groupName);
     if (g == null) {
       return false;
    }
    try {
       this.perms.safeGetUser(worldName, playerName).removeParent(g);
     } catch (Exception e) {
       e.printStackTrace();
       return false;
    } 
     return true;
  }

    @Override
  public boolean playerAdd(String worldName, String playerName, String permission) {
     this.perms.addUserPermission(worldName, playerName, permission);
     return true;
  }

    @Override
  public boolean playerRemove(String worldName, String playerName, String permission) {
     this.perms.removeUserPermission(worldName, playerName, permission);
     return true;
  }

    @Override
  public boolean groupAdd(String worldName, String groupName, String permission) {
     if (worldName == null) {
       worldName = "*";
    }
    
     this.perms.addGroupPermission(worldName, groupName, permission);
     return true;
  }

    @Override
  public boolean groupRemove(String worldName, String groupName, String permission) {
     if (worldName == null) {
       worldName = "*";
    }
     this.perms.removeGroupPermission(worldName, groupName, permission);
     return true;
  }

    @Override
  public boolean groupHas(String worldName, String groupName, String permission) {
     if (worldName == null) {
       worldName = "*";
    }
    try {
       return this.perms.safeGetGroup(worldName, groupName).hasPermission(permission);
     } catch (Exception e) {
       e.printStackTrace();
       return false;
    } 
  }

    @Override
  public String[] getPlayerGroups(String world, String playerName) {
     return this.perms.getGroups(world, playerName);
  }

    @Override
  public String getPrimaryGroup(String world, String playerName) {
     return getPlayerGroups(world, playerName)[0];
  }

    @Override
  public boolean playerHas(String worldName, String playerName, String permission) {
     Player p = this.plugin.getServer().getPlayer(playerName);
     if (p != null && 
       p.hasPermission(permission)) {
       return true;
    }
     return this.perms.has(worldName, playerName, permission);
  }

    @Override
  public boolean playerAddTransient(Player player, String permission) {
     return playerAddTransient((String)null, player.getName(), permission);
  }

    @Override
  public boolean playerAddTransient(String worldName, Player player, String permission) {
     return playerAddTransient(worldName, player.getName(), permission);
  }


  private boolean playerAddTransient(String worldName, String player, String permission) {
     if (worldName == null) {
       worldName = "*";
    }
    try {
       this.perms.safeGetUser(worldName, player).addTransientPermission(permission);
       return true;
     } catch (Exception e) {
       return false;
    } 
  }


    @Override
  public boolean playerRemoveTransient(Player player, String permission) {
     return pRemoveTransient((String)null, player.getName(), permission);
  }

    @Override
  public boolean playerRemoveTransient(String worldName, Player player, String permission) {
     return pRemoveTransient(worldName, player.getName(), permission);
  }


    @Override
  public String[] getGroups() {
     Set<String> groupNames = new HashSet<>();
     for (World world : Bukkit.getServer().getWorlds()) {
       for (Group group : this.perms.getGroups(world.getName())) {
         groupNames.add(group.getName());
      }
    } 
     return groupNames.<String>toArray(new String[0]);
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return false;
  }

    @Override
  public boolean hasGroupSupport() {
     return true;
  }


  private boolean pRemoveTransient(String worldName, String player, String permission) {
     if (worldName == null) {
       worldName = "*";
    }
    
    try {
       this.perms.safeGetUser(worldName, player).removeTransientPermission(permission);
       return true;
     } catch (Exception e) {
       return false;
    } 
  }
}