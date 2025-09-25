package net.milkbowl.vault.permission.plugins;

import com.lightniinja.kperms.KGroup;
import com.lightniinja.kperms.KPermsPlugin;
import com.lightniinja.kperms.KPlayer;
import com.lightniinja.kperms.Utilities;
import java.util.List;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

















public class Permission_KPerms
  extends Permission
{
  private final Plugin vault;
   private KPermsPlugin kperms = null;

  
  public Permission_KPerms(Plugin plugin) {
     this.vault = plugin;
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), this.vault);
     if (this.kperms == null) {
       Plugin perms = plugin.getServer().getPluginManager().getPlugin("KPerms");
       if (perms != null && perms.isEnabled()) {
         this.kperms = (KPermsPlugin)perms;
         plugin.getLogger().info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), "KPerms" }));
      } 
    } 
  }
  
  private class PermissionServerListener implements Listener {
    private final Permission_KPerms bridge;
    
    public PermissionServerListener(Permission_KPerms bridge) {
       this.bridge = bridge;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.bridge.kperms == null) {
         Plugin plugin = event.getPlugin();
         if (plugin.getDescription().getName().equals("KPerms")) {
           this.bridge.kperms = (KPermsPlugin)plugin;
           Permission_KPerms.log.info(String.format("[%s][Permission] %s hooked.", new Object[] { plugin.getDescription().getName(), "KPerms" }));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.bridge.kperms != null && 
         event.getPlugin().getDescription().getName().equals(this.bridge.kperms.getName())) {
         this.bridge.kperms = null;
         Permission_KPerms.log.info(String.format("[%s][Permission] %s un-hooked.", event.getPlugin().getDescription().getName(), "KPerms"));
      } 
    }
  }


    @Override
  public String getName() {
     return "KPerms";
  }

    @Override
  public boolean isEnabled() {
     return this.kperms.isEnabled();
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
     return (new KPlayer(player, this.kperms)).hasPermission(permission);
  }

    @Override
  public boolean playerAdd(String world, String player, String permission) {
     return (new KPlayer(player, this.kperms)).addPermission(permission);
  }

    @Override
  public boolean playerRemove(String world, String player, String permission) {
     return (new KPlayer(player, this.kperms)).removePermission(permission);
  }

    @Override
  public boolean groupHas(String world, String group, String permission) {
     return (new KGroup(group, this.kperms)).hasPermission(permission);
  }

    @Override
  public boolean groupAdd(String world, String group, String permission) {
     return (new KGroup(group, this.kperms)).addPermission(permission);
  }

    @Override
  public boolean groupRemove(String world, String group, String permission) {
     return (new KGroup(group, this.kperms)).removePermission(permission);
  }

    @Override
  public boolean playerInGroup(String world, String player, String group) {
     return (new KPlayer(player, this.kperms)).isMemberOfGroup(group);
  }

    @Override
  public boolean playerAddGroup(String world, String player, String group) {
     return (new KPlayer(player, this.kperms)).addGroup(group);
  }

    @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
     return (new KPlayer(player, this.kperms)).removeGroup(group);
  }

    @Override
  public String[] getPlayerGroups(String world, String player) {
     List<String> groups = (new KPlayer(player, this.kperms)).getGroups();
     String[] gr = new String[groups.size()];
     gr = groups.<String>toArray(gr);
     return gr;
  }

    @Override
  public String getPrimaryGroup(String world, String player) {
     return (new KPlayer(player, this.kperms)).getPrimaryGroup();
  }

    @Override
  public String[] getGroups() {
     return (new Utilities(this.kperms)).getGroups();
  }
}