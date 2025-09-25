package net.milkbowl.vault.chat.plugins;

import de.hydrox.bukkit.DroxPerms.DroxPerms;
import de.hydrox.bukkit.DroxPerms.DroxPermsAPI;
import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;



public class Chat_DroxPerms
  extends Chat
{
  private final Logger log;
  private final String name = "DroxPerms";
  private Plugin plugin;
  private DroxPermsAPI API;
  
  public Chat_DroxPerms(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    this.log = plugin.getLogger();

    
    if (this.API == null) {
      DroxPerms p = (DroxPerms)plugin.getServer().getPluginManager().getPlugin("DroxPerms");
      if (p != null) {
        this.API = p.getAPI();
        this.log.info(String.format("[%s][Chat] %s hooked.", new Object[] { plugin.getDescription().getName(), "DroxPerms" }));
      } 
    } 
    Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(), plugin);
  }
  
  public class PermissionServerListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (Chat_DroxPerms.this.API == null) {
        Plugin permPlugin = event.getPlugin();
        if (permPlugin.getDescription().getName().equals("DroxPerms")) {
          Chat_DroxPerms.this.API = ((DroxPerms)permPlugin).getAPI();
          Chat_DroxPerms.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "DroxPerms"));
        } 
      } 
    }
  }

  @Override
  public String getName() {
    getClass(); return "DroxPerms";
  }

    @Override
  public boolean isEnabled() {
    return true;
  }

    @Override
  public String getPlayerPrefix(String world, String player) {
    String prefix = this.API.getPlayerInfo(player, "prefix");
    if (prefix == null) {
      String prigroup = this.API.getPlayerGroup(player);
      prefix = this.API.getGroupInfo(prigroup, "prefix");
    } 
    return prefix;
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    this.API.setPlayerInfo(player, "prefix", prefix);
  }

    @Override
  public String getPlayerSuffix(String world, String player) {
    return this.API.getPlayerInfo(player, "suffix");
  }

    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    this.API.setPlayerInfo(player, "suffix", suffix);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    return this.API.getGroupInfo(group, "prefix");
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    this.API.setGroupInfo(group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
    return this.API.getGroupInfo(group, "suffix");
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    this.API.setGroupInfo(group, "suffix", suffix);
  }

    @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Integer.valueOf(s).intValue();
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

    @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    this.API.setPlayerInfo(player, node, String.valueOf(value));
  }

    @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Integer.valueOf(s).intValue();
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

    @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    this.API.setGroupInfo(group, node, String.valueOf(value));
  }

    @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Double.valueOf(s).doubleValue();
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

    @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    this.API.setPlayerInfo(player, node, String.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    
    try {
      return Double.valueOf(s).doubleValue();
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

    @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    this.API.setGroupInfo(group, node, String.valueOf(value));
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
    this.API.setPlayerInfo(player, node, String.valueOf(value));
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
    this.API.setGroupInfo(group, node, String.valueOf(value));
  }

    @Override
  public String getPlayerInfoString(String world, String player, String node, String defaultValue) {
    String val = this.API.getPlayerInfo(player, node);
    return (val != null) ? val : defaultValue;
  }

    @Override
  public void setPlayerInfoString(String world, String player, String node, String value) {
    this.API.setPlayerInfo(player, node, value);
  }

    @Override
  public String getGroupInfoString(String world, String group, String node, String defaultValue) {
    String val = this.API.getGroupInfo(group, node);
    return (val != null) ? val : defaultValue;
  }

    @Override
  public void setGroupInfoString(String world, String group, String node, String value) {
    this.API.setGroupInfo(group, node, value);
  }
}