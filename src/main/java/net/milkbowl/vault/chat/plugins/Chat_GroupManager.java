package net.milkbowl.vault.chat.plugins;

import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

















public class Chat_GroupManager
  extends Chat
{
  private final Logger log;
  private final String name = "GroupManager - Chat";
  private Plugin plugin = null;
  private GroupManager groupManager;
  
  public Chat_GroupManager(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    this.log = plugin.getLogger();
    Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
    if (this.groupManager == null) {
      Plugin chat = plugin.getServer().getPluginManager().getPlugin("GroupManager");
      if (chat != null && 
        chat.isEnabled()) {
        this.groupManager = (GroupManager)chat;
        this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "GroupManager - Chat"));
      } 
    } 
  }
  
  public class PermissionServerListener
    implements Listener
  {
    Chat_GroupManager chat = null;
    
    public PermissionServerListener(Chat_GroupManager chat) {
      this.chat = chat;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (this.chat.groupManager == null) {
        Plugin perms = event.getPlugin();
        
        if (perms.getDescription().getName().equals("GroupManager")) {
          this.chat.groupManager = (GroupManager)perms;
          Chat_GroupManager.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "GroupManager - Chat"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.chat.groupManager != null && 
        event.getPlugin().getDescription().getName().equals("GroupManager")) {
        this.chat.groupManager = null;
        Chat_GroupManager.this.log.info(String.format("[%s][Chat] %s un-hooked.", plugin.getDescription().getName(), "GroupManager - Chat"));
      } 
    }
  }


  @Override
  public String getName() {
    return "GroupManager - Chat";
  }

    @Override
  public boolean isEnabled() {
    if (this.groupManager == null) {
      return false;
    }
    return this.groupManager.isEnabled();
  }


    @Override
  public int getPlayerInfoInteger(String worldName, String playerName, String node, int defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Integer val = Integer.valueOf(handler.getUserPermissionInteger(playerName, node));
    return (val != null) ? val.intValue() : defaultValue;
  }

    @Override
  public void setPlayerInfoInteger(String worldName, String playerName, String node, int value) {
    setPlayerValue(worldName, playerName, node, Integer.valueOf(value));
  }

    @Override
  public int getGroupInfoInteger(String worldName, String groupName, String node, int defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Integer val = Integer.valueOf(handler.getGroupPermissionInteger(groupName, node));
    return (val != null) ? val.intValue() : defaultValue;
  }

    @Override
  public void setGroupInfoInteger(String worldName, String groupName, String node, int value) {
    setGroupValue(worldName, groupName, node, Integer.valueOf(value));
  }

    @Override
  public double getPlayerInfoDouble(String worldName, String playerName, String node, double defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Double val = Double.valueOf(handler.getUserPermissionDouble(playerName, node));
    return (val != null) ? val.doubleValue() : defaultValue;
  }

    @Override
  public void setPlayerInfoDouble(String worldName, String playerName, String node, double value) {
    setPlayerValue(worldName, playerName, node, Double.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String worldName, String groupName, String node, double defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Double val = Double.valueOf(handler.getGroupPermissionDouble(groupName, node));
    return (val != null) ? val.doubleValue() : defaultValue;
  }

    @Override
  public void setGroupInfoDouble(String worldName, String groupName, String node, double value) {
    setGroupValue(worldName, groupName, node, Double.valueOf(value));
  }

    @Override
  public boolean getPlayerInfoBoolean(String worldName, String playerName, String node, boolean defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Boolean val = Boolean.valueOf(handler.getUserPermissionBoolean(playerName, node));
    return (val != null) ? val.booleanValue() : defaultValue;
  }

    @Override
  public void setPlayerInfoBoolean(String worldName, String playerName, String node, boolean value) {
    setPlayerValue(worldName, playerName, node, Boolean.valueOf(value));
  }

    @Override
  public boolean getGroupInfoBoolean(String worldName, String groupName, String node, boolean defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    Boolean val = Boolean.valueOf(handler.getGroupPermissionBoolean(groupName, node));
    return (val != null) ? val.booleanValue() : defaultValue;
  }

    @Override
  public void setGroupInfoBoolean(String worldName, String groupName, String node, boolean value) {
    setGroupValue(worldName, groupName, node, Boolean.valueOf(value));
  }

    @Override
  public String getPlayerInfoString(String worldName, String playerName, String node, String defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    String val = handler.getUserPermissionString(playerName, node);
    return (val != null) ? val : defaultValue;
  }

    @Override
  public void setPlayerInfoString(String worldName, String playerName, String node, String value) {
    setPlayerValue(worldName, playerName, node, value);
  }

    @Override
  public String getGroupInfoString(String worldName, String groupName, String node, String defaultValue) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return defaultValue;
    }
    String val = handler.getGroupPermissionString(groupName, node);
    return (val != null) ? val : defaultValue;
  }

    @Override
  public void setGroupInfoString(String worldName, String groupName, String node, String value) {
    setGroupValue(worldName, groupName, node, value);
  }

    @Override
  public String getPlayerPrefix(String worldName, String playerName) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return "";
    }
    return handler.getUserPrefix(playerName);
  }

    @Override
  public String getPlayerSuffix(String worldName, String playerName) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    if (handler == null) {
      return "";
    }
    return handler.getUserSuffix(playerName);
  }

    @Override
  public void setPlayerSuffix(String worldName, String player, String suffix) {
    setPlayerInfoString(worldName, player, "suffix", suffix);
  }

    @Override
  public void setPlayerPrefix(String worldName, String player, String prefix) {
    setPlayerInfoString(worldName, player, "prefix", prefix);
  }

    @Override
  public String getGroupPrefix(String worldName, String group) {
    return getGroupInfoString(worldName, group, "prefix", "");
  }

    @Override
  public void setGroupPrefix(String worldName, String group, String prefix) {
    setGroupInfoString(worldName, group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String worldName, String group) {
    return getGroupInfoString(worldName, group, "suffix", "");
  }

    @Override
  public void setGroupSuffix(String worldName, String group, String suffix) {
    setGroupInfoString(worldName, group, "suffix", suffix);
  }

    @Override
  public String getPrimaryGroup(String worldName, String playerName) {
    AnjoPermissionsHandler handler;
    if (worldName == null) {
      handler = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(playerName);
    } else {
      handler = this.groupManager.getWorldsHolder().getWorldPermissions(worldName);
    } 
    return handler.getGroup(playerName);
  }

  private void setPlayerValue(String worldName, String playerName, String node, Object value) {
    OverloadedWorldHolder owh;
    if (worldName == null) {
      owh = this.groupManager.getWorldsHolder().getWorldDataByPlayerName(playerName);
    } else {
      owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
    if (owh == null) {
      return;
    }
    User user = owh.getUser(playerName);
    if (user == null) {
      return;
    }
    user.getVariables().addVar(node, value);
  }
  
  private void setGroupValue(String worldName, String groupName, String node, Object value) {
    OverloadedWorldHolder owh;
    if (worldName == null) {
      owh = this.groupManager.getWorldsHolder().getDefaultWorld();
    } else {
      owh = this.groupManager.getWorldsHolder().getWorldData(worldName);
    } 
    if (owh == null) {
      return;
    }
    Group group = owh.getGroup(groupName);
    if (group == null) {
      return;
    }
    group.getVariables().addVar(node, value);
  }
}