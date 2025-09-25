package net.milkbowl.vault.chat.plugins;

import com.overmc.overpermissions.api.GroupManager;
import com.overmc.overpermissions.api.PermissionGroup;
import com.overmc.overpermissions.api.PermissionUser;
import com.overmc.overpermissions.api.UserManager;
import com.overmc.overpermissions.internal.OverPermissions;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;













public class Chat_OverPermissions
  extends Chat
{
  protected final Plugin plugin;
  private OverPermissions overPerms;
  private UserManager userManager;
  private GroupManager groupManager;
  
  public Chat_OverPermissions(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    
    plugin.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);
    
    if (this.overPerms == null) {
      Plugin p = plugin.getServer().getPluginManager().getPlugin("OverPermissions");
      if (p != null) {
        this.overPerms = (OverPermissions)p;
        this.userManager = this.overPerms.getUserManager();
        this.groupManager = this.overPerms.getGroupManager();
        plugin.getLogger().info(String.format("[%s][Chat] %s hooked.", new Object[] { plugin.getDescription().getName(), "OverPermissions" }));
      } 
    } 
  }

  @Override
  public String getName() {
    return "OverPermissions_Chat";
  }

    @Override
  public boolean isEnabled() {
    return (this.overPerms != null);
  }

    @Override
  public String getPlayerPrefix(String world, String player) {
    return getPlayerInfoString(world, player, "prefix", "");
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    setPlayerInfoString(world, player, "prefix", prefix);
  }

    @Override
  public String getPlayerSuffix(String world, String player) {
    return getPlayerInfoString(world, player, "suffix", "");
  }

    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    setPlayerInfoString(world, player, "suffix", suffix);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    return getGroupInfoString(world, group, "prefix", "");
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    setGroupInfoString(world, group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
    return getGroupInfoString(world, group, "suffix", "");
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    setGroupInfoString(world, group, "prefix", suffix);
  }

    @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Integer.valueOf(s).intValue();
    } catch (NumberFormatException numberFormatException) {
      
      return defaultValue;
    } 
  }

    @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    setPlayerInfoString(world, player, node, String.valueOf(value));
  }

    @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Integer.valueOf(s).intValue();
    } catch (NumberFormatException numberFormatException) {
      
      return defaultValue;
    } 
  }

    @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    setGroupInfoString(world, group, node, String.valueOf(value));
  }

    @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Double.valueOf(s).doubleValue();
    } catch (NumberFormatException numberFormatException) {
      
      return defaultValue;
    } 
  }

    @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    setPlayerInfoString(world, player, node, String.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Double.valueOf(s).doubleValue();
    } catch (NumberFormatException numberFormatException) {
      
      return defaultValue;
    } 
  }

    @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    setGroupInfoString(world, group, node, String.valueOf(value));
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String player, String node, boolean defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    Boolean val = Boolean.valueOf(s);
    return (val != null) ? val.booleanValue() : defaultValue;
  }

    @Override
  public void setPlayerInfoBoolean(String world, String player, String node, boolean value) {
    setPlayerInfoString(world, player, node, String.valueOf(value));
  }

    @Override
  public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    Boolean val = Boolean.valueOf(s);
    return (val != null) ? val.booleanValue() : defaultValue;
  }

    @Override
  public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
    setGroupInfoString(world, group, node, String.valueOf(value));
  }

    @Override
  public String getPlayerInfoString(String world, String playerName, String node, String defaultValue) {
    if (!this.userManager.doesUserExist(playerName)) {
      return defaultValue;
    }
    PermissionUser user = this.userManager.getPermissionUser(playerName);
    if (world == null) {
      if (!user.hasGlobalMeta(node)) {
        return defaultValue;
      }
      return user.getGlobalMeta(node);
    } 
    if (!user.hasMeta(node, world)) {
      return defaultValue;
    }
    return user.getMeta(node, world);
  }


    @Override
  public void setPlayerInfoString(String world, String playerName, String node, String value) {
    if (!this.userManager.canUserExist(playerName)) {
      return;
    }
    PermissionUser user = this.userManager.getPermissionUser(playerName);
    if (world != null) {
      if (value == null) {
        user.removeMeta(node, world);
      } else {
        user.setMeta(node, value, world);
      }
    
    } else if (value == null) {
      user.removeGlobalMeta(node);
    } else {
      user.setGlobalMeta(node, value);
    } 
  }


    @Override
  public String getGroupInfoString(String world, String groupName, String node, String defaultValue) {
    if (!this.groupManager.doesGroupExist(groupName)) {
      return defaultValue;
    }
    PermissionGroup group = this.overPerms.getGroupManager().getGroup(groupName);
    if (world == null) {
      if (!group.hasGlobalMeta(node)) {
        return defaultValue;
      }
      return group.getGlobalMeta(node);
    } 
    if (!group.hasMeta(node, world)) {
      return defaultValue;
    }
    return group.getMeta(node, world);
  }


    @Override
  public void setGroupInfoString(String world, String groupName, String node, String value) {
    if (!this.overPerms.getGroupManager().doesGroupExist(groupName)) {
      return;
    }
    PermissionGroup group = this.overPerms.getGroupManager().getGroup(groupName);
    if (world != null) {
      if (value == null) {
        group.removeMeta(node, world);
      } else {
        group.setMeta(node, value, world);
      }
    
    } else if (value == null) {
      group.removeGlobalMeta(node);
    } else {
      group.setGlobalMeta(node, value);
    } 
  }
  
  public class PermissionServerListener
    implements Listener {
    Chat_OverPermissions chat = null;
    
    public PermissionServerListener(Chat_OverPermissions chat) {
      this.chat = chat;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (this.chat.overPerms == null) {
        Plugin chat = Chat_OverPermissions.this.plugin.getServer().getPluginManager().getPlugin("OverPermissions");
        if (chat != null) {
          this.chat.overPerms = (OverPermissions)chat;
          Chat_OverPermissions.this.plugin.getLogger().info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), plugin.getName()));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.chat.overPerms != null && event
        .getPlugin().getDescription().getName().equals("OverPermissions")) {
        this.chat.overPerms = null;
        Chat_OverPermissions.this.plugin.getLogger().info(String.format("[%s][Chat] %s un-hooked.", plugin.getDescription().getName(), plugin.getName()));
      } 
    }
  }
}