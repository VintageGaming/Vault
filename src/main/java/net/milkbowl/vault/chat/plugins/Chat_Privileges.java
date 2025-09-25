package net.milkbowl.vault.chat.plugins;

import net.krinsoft.privileges.Privileges;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;















public class Chat_Privileges
  extends Chat
{
  private static final String FRIENDLY_NAME = "Privileges - Chat";
  private static final String PLUGIN_NAME = "Privileges";
  private static final String CHAT_PREFIX_KEY = "prefix";
  private static final String CHAT_SUFFIX_KEY = "suffix";
  private Privileges privs;
  private final Plugin plugin;
  
  public Chat_Privileges(Plugin plugin, Permission perms) {
    super(perms);
    this.plugin = plugin;
    Bukkit.getServer().getPluginManager().registerEvents(new PermissionServerListener(), plugin);
    
    if (this.privs == null) {
      Plugin privsPlugin = plugin.getServer().getPluginManager().getPlugin("Privileges");
      if (privsPlugin != null && privsPlugin.isEnabled()) {
        this.privs = (Privileges)privsPlugin;
        plugin.getLogger().info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "Privileges - Chat"));
      } 
    } 
  }

  @Override
  public String getName() {
    return "Privileges - Chat";
  }

    @Override
  public boolean isEnabled() {
    return (this.privs != null && this.privs.isEnabled());
  }


  private String getPlayerOrGroupInfoString(String world, String player, String key, String defaultValue) {
    String value = getPlayerInfoString(world, player, key, null);
    if (value != null) return value;
    
    value = getGroupInfoString(world, getPrimaryGroup(world, player), key, null);
    if (value != null) return value;
    
    return defaultValue;
  }
  
  private void worldCheck(String world) {
    if (world != null && !world.isEmpty()) {
      throw new UnsupportedOperationException("Privileges does not support multiple worlds for player/group metadata.");
    }
  }

    @Override
  public String getPlayerPrefix(String world, String player) {
    return getPlayerOrGroupInfoString(world, player, "prefix", (String)null);
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    setPlayerInfoString(world, player, "prefix", prefix);
  }

    @Override
  public String getPlayerSuffix(String world, String player) {
    return getPlayerOrGroupInfoString(world, player, "suffix", (String)null);
  }

    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    setPlayerInfoString(world, player, "suffix", suffix);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    return getGroupInfoString(world, group, "prefix", (String)null);
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    setGroupInfoString(world, group, "prefix", prefix);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
    return getGroupInfoString(world, group, "suffix", (String)null);
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    setGroupInfoString(world, group, "suffix", suffix);
  }

    @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    return this.privs.getUserNode(player).getInt(node, defaultValue);
  }

    @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    worldCheck(world);
    this.privs.getUserNode(player).set(node, Integer.valueOf(value));
  }

    @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    return this.privs.getGroupNode(group).getInt(node, defaultValue);
  }

    @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    worldCheck(world);
    this.privs.getGroupNode(group).set(node, Integer.valueOf(value));
  }

    @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    return this.privs.getUserNode(player).getDouble(node, defaultValue);
  }

    @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    worldCheck(world);
    this.privs.getUserNode(player).set(node, Double.valueOf(value));
  }

    @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    return this.privs.getGroupNode(group).getDouble(node, defaultValue);
  }

    @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    worldCheck(world);
    this.privs.getGroupNode(group).set(node, Double.valueOf(value));
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String player, String node, boolean defaultValue) {
    return this.privs.getUserNode(player).getBoolean(node, defaultValue);
  }

    @Override
  public void setPlayerInfoBoolean(String world, String player, String node, boolean value) {
    worldCheck(world);
    this.privs.getUserNode(player).set(node, Boolean.valueOf(value));
  }

    @Override
  public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
    return this.privs.getGroupNode(group).getBoolean(node, defaultValue);
  }

    @Override
  public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
    worldCheck(world);
    this.privs.getGroupNode(group).set(node, Boolean.valueOf(value));
  }

    @Override
  public String getPlayerInfoString(String world, String player, String node, String defaultValue) {
    return this.privs.getUserNode(player).getString(node, defaultValue);
  }

    @Override
  public void setPlayerInfoString(String world, String player, String node, String value) {
    worldCheck(world);
    this.privs.getUserNode(player).set(node, value);
  }

    @Override
  public String getGroupInfoString(String world, String group, String node, String defaultValue) {
    return this.privs.getGroupNode(group).getString(node, defaultValue);
  }

    @Override
  public void setGroupInfoString(String world, String group, String node, String value) {
    worldCheck(world);
    this.privs.getGroupNode(group).set(node, value);
  }
  
  public class PermissionServerListener
    implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (Chat_Privileges.this.privs == null) {
        Plugin permChat = event.getPlugin();
        if ("Privileges".equals(permChat.getDescription().getName()) && 
          permChat.isEnabled()) {
          Chat_Privileges.this.privs = (Privileges)permChat;
          Chat_Privileges.this.plugin.getLogger().info(String.format("[Chat] %s hooked.", "Privileges - Chat"));
        } 
      } 
    }

    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (Chat_Privileges.this.privs != null && 
        "Privileges".equals(event.getPlugin().getDescription().getName())) {
        Chat_Privileges.this.privs = null;
        Chat_Privileges.this.plugin.getLogger().info(String.format("[Chat] %s un-hooked.", "Privileges - Chat"));
      } 
    }
  }
}