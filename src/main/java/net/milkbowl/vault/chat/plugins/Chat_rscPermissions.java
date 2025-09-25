package net.milkbowl.vault.chat.plugins;

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
import ru.simsonic.rscPermissions.MainPluginClass;
import ru.simsonic.rscPermissions.rscpAPI;

















public class Chat_rscPermissions
  extends Chat
{
  private final Logger log;
  private final Plugin vault;
  private MainPluginClass rscp;
  private rscpAPI rscpAPI;
  
  public Chat_rscPermissions(Plugin plugin, Permission perm) {
    super(perm);
    this.vault = plugin;
    this.log = plugin.getLogger();
    Bukkit.getServer().getPluginManager().registerEvents(new ChatServerListener(this), this.vault);
    if (this.rscp == null) {
      Plugin perms = plugin.getServer().getPluginManager().getPlugin("rscPermissions");
      if (perms != null && perms.isEnabled()) {
        this.rscp = (MainPluginClass)perms;
        this.rscpAPI = this.rscp.API;
        plugin.getLogger().info(String.format("[%s][Chat] %s hooked.", new Object[] { plugin.getDescription().getName(), "rscPermissions" }));
      } 
    } 
  }
  
  private class ChatServerListener implements Listener {
    private final Chat_rscPermissions bridge;
    
    public ChatServerListener(Chat_rscPermissions bridge) {
      this.bridge = bridge;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    private void onPluginEnable(PluginEnableEvent event) {
      if (this.bridge.rscp == null) {
        Plugin plugin = event.getPlugin();
        if (plugin.getDescription().getName().equals("rscPermissions")) {
          this.bridge.rscp = (MainPluginClass)plugin;
          this.bridge.rscpAPI = this.bridge.rscp.API;
          Chat_rscPermissions.this.log.info(String.format("[%s][Chat] %s hooked.", plugin.getDescription().getName(), "rscPermissions"));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.bridge.rscpAPI != null && 
        event.getPlugin().getDescription().getName().equals(this.bridge.rscpAPI.getName())) {
        this.bridge.rscpAPI = null;
        this.bridge.rscp = null;
        Chat_rscPermissions.this.log.info(String.format("[%s][Chat] %s un-hooked.", event.getPlugin().getDescription().getName(), "rscPermissions"));
      } 
    }
  }


  @Override
  public String getName() {
    return "rscPermissions";
  }

    @Override
  public boolean isEnabled() {
    return (this.rscpAPI != null && this.rscpAPI.isEnabled());
  }

    @Override
  public String getPlayerPrefix(String world, String player) {
    return this.rscpAPI.getPlayerPrefix(world, player);
  }

    @Override
  public String getPlayerSuffix(String world, String player) {
    return this.rscpAPI.getPlayerSuffix(world, player);
  }

    @Override
  public String getGroupPrefix(String world, String group) {
    return this.rscpAPI.getGroupPrefix(world, group);
  }

    @Override
  public String getGroupSuffix(String world, String group) {
    return this.rscpAPI.getGroupSuffix(world, group);
  }

    @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    this.rscpAPI.setPlayerPrefix(world, player, prefix);
  }

    @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    this.rscpAPI.setPlayerSuffix(world, player, suffix);
  }

    @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    this.rscpAPI.setGroupPrefix(world, group, prefix);
  }

    @Override
  public void setGroupSuffix(String world, String group, String suffix) {
    this.rscpAPI.setGroupSuffix(world, group, suffix);
  }

    @Override
  public int getPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setPlayerInfoInteger(String world, String player, String node, int defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setGroupInfoInteger(String world, String group, String node, int defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public double getPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setPlayerInfoDouble(String world, String player, String node, double defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setGroupInfoDouble(String world, String group, String node, double defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public boolean getPlayerInfoBoolean(String world, String player, String node, boolean defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setPlayerInfoBoolean(String world, String player, String node, boolean defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public String getPlayerInfoString(String world, String player, String node, String defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setPlayerInfoString(String world, String player, String node, String defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public String getGroupInfoString(String world, String group, String node, String defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }

    @Override
  public void setGroupInfoString(String world, String group, String node, String defaultValue) {
    throw new UnsupportedOperationException("rscPermissions does not support info nodes");
  }
}