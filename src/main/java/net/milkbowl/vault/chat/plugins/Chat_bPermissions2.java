package net.milkbowl.vault.chat.plugins;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
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

















public class Chat_bPermissions2
  extends Chat
{
  private final Logger log;
/*  36 */   private final String name = "bInfo";
/*  37 */   private Plugin plugin = null;
  private boolean hooked = false;
  
  public Chat_bPermissions2(Plugin plugin, Permission perms) {
/*  41 */     super(perms);
/*  42 */     this.plugin = plugin;
/*  43 */     this.log = plugin.getLogger();
    
/*  45 */     Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

    
/*  48 */     if (!this.hooked) {
/*  49 */       Plugin p = plugin.getServer().getPluginManager().getPlugin("bPermissions");
/*  50 */       if (p != null) {
/*  51 */         this.hooked = true;
/*  52 */         this.log.info(String.format("[%s][Chat] %s hooked.", new Object[] { plugin.getDescription().getName(), "bPermissions2" }));
      } 
    } 
  }
  
  public class PermissionServerListener implements Listener {
/*  58 */     Chat_bPermissions2 chat = null;
    
    public PermissionServerListener(Chat_bPermissions2 chat) {
/*  61 */       this.chat = chat;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
/*  66 */       if (!Chat_bPermissions2.this.hooked) {
/*  67 */         Plugin chat = event.getPlugin();
/*  68 */         if (chat.getDescription().getName().equals("bPermissions")) {
/*  69 */           Chat_bPermissions2.this.hooked = true;
/*  70 */           Chat_bPermissions2.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "bPermissions2"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
/*  77 */       if (Chat_bPermissions2.this.hooked && 
/*  78 */         event.getPlugin().getDescription().getName().equals("bPermissions")) {
/*  79 */         Chat_bPermissions2.this.hooked = false;
/*  80 */         Chat_bPermissions2.this.log.info(String.format("[%s][Chat] %s un-hooked.", plugin.getDescription().getName(), "bPermissions2"));
      } 
    }
  }


  @Override
  public String getName() {
/*  88 */     return "bInfo";
  }

  @Override
  public boolean isEnabled() {
/*  93 */     return this.hooked;
  }

  @Override
  public String getPlayerPrefix(String world, String player) {
/*  98 */     return ApiLayer.getValue(world, CalculableType.USER, player, "prefix");
  }

  @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    ApiLayer.setValue(world, CalculableType.USER, player, "prefix", prefix);
  }

  @Override
  public String getPlayerSuffix(String world, String player) {
    return ApiLayer.getValue(world, CalculableType.USER, player, "suffix");
  }

  @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    ApiLayer.setValue(world, CalculableType.USER, player, "suffix", suffix);
  }

  @Override
  public String getGroupPrefix(String world, String group) {
    return ApiLayer.getValue(world, CalculableType.GROUP, group, "prefix");
  }

  @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    ApiLayer.setValue(world, CalculableType.GROUP, group, "prefix", prefix);
  }

  @Override
  public String getGroupSuffix(String world, String group) {
    return ApiLayer.getValue(world, CalculableType.GROUP, group, "suffix");
  }

  @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    ApiLayer.setValue(world, CalculableType.GROUP, group, "suffix", suffix);
  }

  @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    try {
      int i = Integer.valueOf(s).intValue();
      return i;
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

  @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    ApiLayer.setValue(world, CalculableType.USER, player, node, String.valueOf(value));
  }

  @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    try {
      int i = Integer.valueOf(s).intValue();
      return i;
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

  @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    ApiLayer.setValue(world, CalculableType.GROUP, group, node, String.valueOf(value));
  }

  @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    String s = getPlayerInfoString(world, player, node, null);
    if (s == null) {
      return defaultValue;
    }
    try {
      double d = Double.valueOf(s).doubleValue();
      return d;
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

  @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    ApiLayer.setValue(world, CalculableType.USER, player, node, String.valueOf(value));
  }

  @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    String s = getGroupInfoString(world, group, node, null);
    if (s == null) {
      return defaultValue;
    }
    try {
      double d = Double.valueOf(s).doubleValue();
      return d;
    } catch (NumberFormatException e) {
      return defaultValue;
    } 
  }

  @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    ApiLayer.setValue(world, CalculableType.GROUP, group, node, String.valueOf(value));
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
    ApiLayer.setValue(world, CalculableType.USER, player, node, String.valueOf(value));
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
    ApiLayer.setValue(world, CalculableType.GROUP, group, node, String.valueOf(value));
  }

  @Override
  public String getPlayerInfoString(String world, String player, String node, String defaultValue) {
    String val = ApiLayer.getValue(world, CalculableType.USER, player, node);
    return (val == null || val == "BLANKWORLD" || val == "") ? defaultValue : val;
  }

  @Override
  public void setPlayerInfoString(String world, String player, String node, String value) {
    ApiLayer.setValue(world, CalculableType.USER, player, node, value);
  }

  @Override
  public String getGroupInfoString(String world, String group, String node, String defaultValue) {
    String val = ApiLayer.getValue(world, CalculableType.GROUP, group, node);
    return (val == null || val == "BLANKWORLD" || val == "") ? defaultValue : val;
  }

  @Override
  public void setGroupInfoString(String world, String group, String node, String value) {
    ApiLayer.setValue(world, CalculableType.GROUP, group, node, value);
  }
}