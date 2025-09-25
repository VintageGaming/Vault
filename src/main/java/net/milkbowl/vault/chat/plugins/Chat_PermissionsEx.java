package net.milkbowl.vault.chat.plugins;

import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

















public class Chat_PermissionsEx
  extends Chat
{
  private final Logger log;
  private final String name = "PermissionsEx_Chat";
  
  private Plugin plugin = null;
  private PermissionsEx chat = null;
  
  public Chat_PermissionsEx(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    this.log = plugin.getLogger();
    
    Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
    if (this.chat == null) {
      Plugin p = plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
      if (p != null && 
        p.isEnabled()) {
        this.chat = (PermissionsEx)p;
        this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "PermissionsEx_Chat"));
      } 
    } 
  }
  
  public class PermissionServerListener
    implements Listener {
    Chat_PermissionsEx chat = null;
    
    public PermissionServerListener(Chat_PermissionsEx chat) {
      this.chat = chat;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (this.chat.chat == null) {
        Plugin perms = event.getPlugin();
        
        if (perms.getDescription().getName().equals("PermissionsEx") && 
          perms.isEnabled()) {
          this.chat.chat = (PermissionsEx)perms;
          Chat_PermissionsEx.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "PermissionsEx_Chat"));
        } 
      } 
    }

    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.chat.chat != null && 
        event.getPlugin().getDescription().getName().equals("PermissionsEx")) {
        this.chat.chat = null;
        Chat_PermissionsEx.this.log.info(String.format("[%s][Chat] %s un-hooked.", plugin.getDescription().getName(), "PermissionsEx_Chat"));
      } 
    }
  }


  @Override
  public String getName() {
    return "PermissionsEx_Chat";
  }

    @Override
  public boolean isEnabled() {
    if (this.chat == null) {
      return false;
    }
    return this.chat.isEnabled();
  }

  private PermissionUser getUser(OfflinePlayer op) {
    return PermissionsEx.getPermissionManager().getUser(op.getUniqueId());
  }
  
  private PermissionUser getUser(String playerName) {
    return PermissionsEx.getPermissionManager().getUser(playerName);
  }

    @Override
  public int getPlayerInfoInteger(String world, String playerName, String node, int defaultValue) {
    return getUser(playerName).getOptionInteger(node, world, defaultValue);
  }

    @Override
  public double getPlayerInfoDouble(String world, String playerName, String node, double defaultValue) {
    return getUser(playerName).getOptionDouble(node, world, defaultValue);
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String playerName, String node, boolean defaultValue) {
    return getUser(playerName).getOptionBoolean(node, world, defaultValue);
  }

    @Override
  public String getPlayerInfoString(String world, String playerName, String node, String defaultValue) {
    return getUser(playerName).getOption(node, world, defaultValue);
  }

    @Override
  public int getPlayerInfoInteger(String world, OfflinePlayer op, String node, int defaultValue) {
    return getUser(op).getOptionInteger(node, world, defaultValue);
  }

    @Override
  public double getPlayerInfoDouble(String world, OfflinePlayer op, String node, double defaultValue) {
    return getUser(op).getOptionDouble(node, world, defaultValue);
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, OfflinePlayer op, String node, boolean defaultValue) {
    return getUser(op).getOptionBoolean(node, world, defaultValue);
  }

    @Override
  public String getPlayerInfoString(String world, OfflinePlayer op, String node, String defaultValue) {
    return getUser(op).getOption(node, world, defaultValue);
  }

    @Override
  public void setPlayerInfoInteger(String world, OfflinePlayer op, String node, int value) {
    PermissionUser user = getUser(op);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoDouble(String world, OfflinePlayer op, String node, double value) {
    PermissionUser user = getUser(op);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoBoolean(String world, OfflinePlayer op, String node, boolean value) {
    PermissionUser user = getUser(op);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoString(String world, OfflinePlayer op, String node, String value) {
    PermissionUser user = getUser(op);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoInteger(String world, String playerName, String node, int value) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoDouble(String world, String playerName, String node, double value) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoBoolean(String world, String playerName, String node, boolean value) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public void setPlayerInfoString(String world, String playerName, String node, String value) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      user.setOption(node, String.valueOf(value), world);
    }
  }

    @Override
  public int getGroupInfoInteger(String world, String groupName, String node, int defaultValue) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return defaultValue;
    }
    return group.getOptionInteger(node, world, defaultValue);
  }


    @Override
  public void setGroupInfoInteger(String world, String groupName, String node, int value) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return;
    }
    group.setOption(node, world, String.valueOf(value));
  }


    @Override
  public double getGroupInfoDouble(String world, String groupName, String node, double defaultValue) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return defaultValue;
    }
    return group.getOptionDouble(node, world, defaultValue);
  }


    @Override
  public void setGroupInfoDouble(String world, String groupName, String node, double value) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return;
    }
    group.setOption(node, world, String.valueOf(value));
  }


    @Override
  public boolean getGroupInfoBoolean(String world, String groupName, String node, boolean defaultValue) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return defaultValue;
    }
    return group.getOptionBoolean(node, world, defaultValue);
  }


    @Override
  public void setGroupInfoBoolean(String world, String groupName, String node, boolean value) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return;
    }
    group.setOption(node, world, String.valueOf(value));
  }


    @Override
  public String getGroupInfoString(String world, String groupName, String node, String defaultValue) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return defaultValue;
    }
    return group.getOption(node, world, defaultValue);
  }


    @Override
  public void setGroupInfoString(String world, String groupName, String node, String value) {
    PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
    if (group == null) {
      return;
    }
    group.setOption(node, world, value);
  }

    @Override
  public String getPlayerPrefix(String world, OfflinePlayer op) {
    PermissionUser user = getUser(op);
    if (user != null) {
      return user.getPrefix(world);
    }
    return null;
  }

    @Override
  public String getPlayerSuffix(String world, OfflinePlayer op) {
    PermissionUser user = getUser(op);
    if (user != null) {
      return user.getSuffix(world);
    }
    return null;
  }

    @Override
  public void setPlayerSuffix(String world, OfflinePlayer player, String suffix) {
    PermissionUser user = getUser(player);
    if (user != null) {
      user.setSuffix(suffix, world);
    }
  }

    @Override
  public void setPlayerPrefix(String world, OfflinePlayer player, String prefix) {
    PermissionUser user = getUser(player);
    if (user != null) {
      user.setPrefix(prefix, world);
    }
  }

    @Override
  public String getPlayerPrefix(String world, String playerName) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      return user.getPrefix(world);
    }
    return null;
  }


    @Override
  public String getPlayerSuffix(String world, String playerName) {
    PermissionUser user = getUser(playerName);
    if (user != null) {
      return user.getSuffix(world);
    }
    return null;
  }


    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    PermissionUser user = getUser(player);
    if (user != null) {
      user.setSuffix(suffix, world);
    }
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    PermissionUser user = getUser(player);
    if (user != null) {
      user.setPrefix(prefix, world);
    }
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    PermissionGroup pGroup = PermissionsEx.getPermissionManager().getGroup(group);
    if (group != null) {
      return pGroup.getPrefix(world);
    }
    return null;
  }


    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    PermissionGroup pGroup = PermissionsEx.getPermissionManager().getGroup(group);
    if (group != null) {
      pGroup.setPrefix(prefix, world);
    }
  }


    @Override
  public String getGroupSuffix(String world, String group) {
    PermissionGroup pGroup = PermissionsEx.getPermissionManager().getGroup(group);
    if (group != null) {
      return pGroup.getSuffix(world);
    }
    return null;
  }


    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    PermissionGroup pGroup = PermissionsEx.getPermissionManager().getGroup(group);
    if (group != null)
      pGroup.setSuffix(suffix, world); 
  }
}