package net.milkbowl.vault.chat.plugins;

import net.ae97.totalpermissions.PermissionManager;
import net.ae97.totalpermissions.TotalPermissions;
import net.ae97.totalpermissions.permission.PermissionGroup;
import net.ae97.totalpermissions.permission.PermissionUser;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;




















public class Chat_TotalPermissions
  extends Chat
{
  private final Plugin plugin;
  private TotalPermissions totalPermissions;
  private final String name = "TotalPermissions-Chat";
  
  public Chat_TotalPermissions(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);
    
    if (this.totalPermissions == null) {
      Plugin chat = plugin.getServer().getPluginManager().getPlugin("TotalPermissions");
      if (chat != null && 
        chat.isEnabled()) {
        this.totalPermissions = (TotalPermissions)chat;
        plugin.getLogger().info(String.format("[Chat] %s hooked.", "TotalPermissions-Chat"));
      } 
    } 
  }
  
  public class PermissionServerListener
    implements Listener
  {
    Chat_TotalPermissions chat = null;
    
    public PermissionServerListener(Chat_TotalPermissions chat) {
      this.chat = chat;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (this.chat.totalPermissions == null) {
        Plugin perms = event.getPlugin();
        
        if (perms != null && 
          perms.getDescription().getName().equals("TotalPermissions") && 
          perms.isEnabled()) {
          this.chat.totalPermissions = (TotalPermissions)perms;
          Chat_TotalPermissions.this.plugin.getLogger().info(String.format("[Chat] %s hooked.", this.chat.getName()));
        } 
      } 
    }


    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.chat.totalPermissions != null && 
        event.getPlugin().getDescription().getName().equals("TotalPermissions")) {
        this.chat.totalPermissions = null;
        Chat_TotalPermissions.this.plugin.getLogger().info(String.format("[Chat] %s un-hooked.", "TotalPermissions-Chat"));
      } 
    }
  }


  @Override
  public String getName() {
    return "TotalPermissions-Chat";
  }

    @Override
  public boolean isEnabled() {
    return (this.totalPermissions == null) ? false : this.totalPermissions.isEnabled();
  }

    @Override
  public String getPlayerPrefix(String world, String player) {
    return getPlayerInfoString(world, player, "prefix", null);
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    setPlayerInfoString(world, player, "prefix", prefix);
  }

    @Override
  public String getPlayerSuffix(String world, String player) {
    return getPlayerInfoString(world, player, "suffix", null);
  }

    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    setPlayerInfoString(world, player, "suffix", suffix);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    return getGroupInfoString(world, group, "prefix", null);
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    setGroupInfoString(world, group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
    return getGroupInfoString(world, group, "suffix", null);
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    setGroupInfoString(world, group, "suffix", suffix);
  }

    @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    Object pre = getPlayerInfo(world, player, node);
    if (pre instanceof Integer) {
      return ((Integer)pre).intValue();
    }
    return defaultValue;
  }

    @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    setPlayerInfo(world, player, node, Integer.valueOf(value));
  }

    @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    Object pre = getGroupInfo(world, group, node);
    if (pre instanceof Integer) {
      return ((Integer)pre).intValue();
    }
    return defaultValue;
  }

    @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    setGroupInfo(world, group, node, Integer.valueOf(value));
  }

    @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    Object pre = getPlayerInfo(world, player, node);
    if (pre instanceof Double) {
      return ((Double)pre).doubleValue();
    }
    return defaultValue;
  }

    @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    setPlayerInfo(world, player, node, Double.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    Object pre = getGroupInfo(world, group, node);
    if (pre instanceof Double) {
      return ((Double)pre).doubleValue();
    }
    return defaultValue;
  }

    @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    setGroupInfo(world, group, node, Double.valueOf(value));
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String player, String node, boolean defaultValue) {
    Object pre = getPlayerInfo(world, player, node);
    if (pre instanceof Boolean) {
      return ((Boolean)pre).booleanValue();
    }
    return defaultValue;
  }

    @Override
  public void setPlayerInfoBoolean(String world, String player, String node, boolean value) {
    setPlayerInfo(world, player, node, Boolean.valueOf(value));
  }

    @Override
  public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
    Object pre = getGroupInfo(world, group, node);
    if (pre instanceof Boolean) {
      return ((Boolean)pre).booleanValue();
    }
    return defaultValue;
  }

    @Override
  public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
    setGroupInfo(world, group, node, Boolean.valueOf(value));
  }

    @Override
  public String getPlayerInfoString(String world, String player, String node, String defaultValue) {
    Object pre = getPlayerInfo(world, player, node);
    if (pre instanceof String) {
      return (String)pre;
    }
    return defaultValue;
  }

    @Override
  public void setPlayerInfoString(String world, String player, String node, String value) {
    setPlayerInfo(world, player, node, value);
  }

    @Override
  public String getGroupInfoString(String world, String group, String node, String defaultValue) {
    Object pre = getGroupInfo(world, group, node);
    if (pre instanceof String) {
      return (String)pre;
    }
    return defaultValue;
  }

    @Override
  public void setGroupInfoString(String world, String group, String node, String value) {
    setGroupInfo(world, group, node, value);
  }


  private PermissionUser getUser(String name) {
    PermissionManager manager = this.totalPermissions.getManager();
    PermissionUser user = manager.getUser(name);
    return user;
  }
  
  private PermissionGroup getGroup(String name) {
    PermissionManager manager = this.totalPermissions.getManager();
    PermissionGroup group = manager.getGroup(name);
    return group;
  }
  
  private void setPlayerInfo(String world, String player, String node, Object value) {
    PermissionUser permissionUser = getUser(player);
    permissionUser.setOption(node, value, world);
  }
  
  private void setGroupInfo(String world, String group, String node, Object value) {
    PermissionGroup permissionGroup = getGroup(group);
    permissionGroup.setOption(node, value, world);
  }
  
  private Object getPlayerInfo(String world, String player, String node) {
    PermissionUser permissionUser = getUser(player);
    return permissionUser.getOption(node);
  }
  
  private Object getGroupInfo(String world, String group, String node) {
    PermissionUser permissionUser = getUser(group);
    return permissionUser.getOption(node);
  }
}