package net.milkbowl.vault.chat.plugins;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;


















public class Chat_Permissions3
  extends Chat
{
  private final Logger log;
   private String name = "Permissions 3 (Yeti) - Chat";
  private PermissionHandler perms;
   private Plugin plugin = null;
   private Permissions chat = null;
  
  public Chat_Permissions3(Plugin plugin, Permission perms) {
     super(perms);
     this.plugin = plugin;
     this.log = plugin.getLogger();
    
     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(), plugin);

    
     if (this.chat == null) {
       Plugin p = plugin.getServer().getPluginManager().getPlugin("Permissions");
       if (p == null) {
         plugin.getServer().getPluginManager().getPlugin("vPerms");
         this.name = "vPerms - Chat";
      } 
       if (p != null && 
         p.isEnabled() && p.getDescription().getVersion().startsWith("3")) {
         this.chat = (Permissions)p;
         this.perms = this.chat.getHandler();
         this.log.info(String.format("[%s][Chat] %s hooked.", new Object[] { plugin.getDescription().getName(), this.name }));
      } 
    } 
  }
  
  public class PermissionServerListener
    implements Listener
  {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (Chat_Permissions3.this.chat == null) {
         Plugin permChat = event.getPlugin();
         if ((permChat.getDescription().getName().equals("Permissions") || permChat.getDescription().getName().equals("vPerms")) && permChat.getDescription().getVersion().startsWith("3") && 
           permChat.isEnabled()) {
           Chat_Permissions3.this.chat = (Permissions)permChat;
           Chat_Permissions3.this.perms = Chat_Permissions3.this.chat.getHandler();
           Chat_Permissions3.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), plugin.getDescription().getName()));
        } 
      } 
    }

    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (Chat_Permissions3.this.chat != null && (
         event.getPlugin().getDescription().getName().equals("Permissions") || event.getPlugin().getDescription().getName().equals("vPerms"))) {
         Chat_Permissions3.this.chat = null;
         Chat_Permissions3.this.perms = null;
         Chat_Permissions3.this.log.info(String.format("[%s][Chat] %s un-hooked.", new Object[] { plugin.getDescription().getName(), plugin.getDescription().getName() }));
      } 
    }
  }


  @Override
  public String getName() {
     return this.name;
  }

    @Override
  public boolean isEnabled() {
     if (this.chat == null) {
       return false;
    }
     return this.chat.isEnabled();
  }

    @Override
  public int getPlayerInfoInteger(String world, String playerName, String node, int defaultValue) {
     Integer i = Integer.valueOf(this.perms.getPermissionInteger(world, playerName, node));
     return (i == null) ? defaultValue : i.intValue();
  }

    @Override
  public double getPlayerInfoDouble(String world, String playerName, String node, double defaultValue) {
     Double d = Double.valueOf(this.perms.getPermissionDouble(world, playerName, node));
     return (d == null) ? defaultValue : d.doubleValue();
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String playerName, String node, boolean defaultValue) {
     Boolean b = Boolean.valueOf(this.perms.getPermissionBoolean(world, playerName, node));
     return (b == null) ? defaultValue : b.booleanValue();
  }

    @Override
  public String getPlayerInfoString(String world, String playerName, String node, String defaultValue) {
     String s = this.perms.getPermissionString(world, playerName, node);
     return (s == null) ? defaultValue : s;
  }

    @Override
  public String getPlayerPrefix(String world, String playerName) {
     return getPlayerInfoString(world, playerName, "prefix", null);
  }

    @Override
  public String getPlayerSuffix(String world, String playerName) {
     return getPlayerInfoString(world, playerName, "suffix", null);
  }


    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {}


    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {}


  public void setPlayerInfo(String world, String playerName, String node, Object value) {}


    @Override
  public void setPlayerInfoInteger(String world, String playerName, String node, int value) {
     setPlayerInfo(world, playerName, node, Integer.valueOf(value));
  }

    @Override
  public void setPlayerInfoDouble(String world, String playerName, String node, double value) {
     setPlayerInfo(world, playerName, node, Double.valueOf(value));
  }

    @Override
  public void setPlayerInfoBoolean(String world, String playerName, String node, boolean value) {
     setPlayerInfo(world, playerName, node, Boolean.valueOf(value));
  }

    @Override
  public void setPlayerInfoString(String world, String playerName, String node, String value) {
     setPlayerInfo(world, playerName, node, value);
  }

    @Override
  public int getGroupInfoInteger(String world, String groupName, String node, int defaultValue) {
     int i = this.perms.getGroupPermissionInteger(world, groupName, node);
     return (i != -1) ? i : defaultValue;
  }

  public void setGroupInfo(String world, String groupName, String node, Object value) {
     this.perms.addGroupInfo(world, groupName, node, value);
  }

    @Override
  public void setGroupInfoInteger(String world, String groupName, String node, int value) {
     setGroupInfo(world, groupName, node, Integer.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String world, String groupName, String node, double defaultValue) {
     double d = this.perms.getGroupPermissionDouble(world, groupName, node);
     return (d != -1.0D) ? d : defaultValue;
  }

    @Override
  public void setGroupInfoDouble(String world, String groupName, String node, double value) {
     setGroupInfo(world, groupName, node, Double.valueOf(value));
  }

    @Override
  public boolean getGroupInfoBoolean(String world, String groupName, String node, boolean defaultValue) {
     return this.perms.getGroupPermissionBoolean(world, groupName, node);
  }

    @Override
  public void setGroupInfoBoolean(String world, String groupName, String node, boolean value) {
     setGroupInfo(world, groupName, node, Boolean.valueOf(value));
  }

    @Override
  public String getGroupInfoString(String world, String groupName, String node, String defaultValue) {
     String s = this.perms.getGroupPermissionString(world, groupName, node);
     return (s != null) ? s : defaultValue;
  }

    @Override
  public void setGroupInfoString(String world, String groupName, String node, String value) {
     setGroupInfo(world, groupName, node, value);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
     return this.perms.getGroupPrefix(world, group);
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
     this.perms.addGroupInfo(world, group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
     return this.perms.getGroupSuffix(world, group);
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
     this.perms.addGroupInfo(world, group, "suffix", suffix);
  }
}