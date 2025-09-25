package net.milkbowl.vault.permission.plugins;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
















public class Permission_SuperPerms
  extends Permission
{
   private final String name = "SuperPerms";
  
  public Permission_SuperPerms(Plugin plugin) {
     this.plugin = plugin;
  }

    @Override
  public String getName() {
     return "SuperPerms";
  }

    @Override
  public boolean isEnabled() {
     return true;
  }

    @Override
  public boolean playerHas(String world, String player, String permission) {
     Player p = this.plugin.getServer().getPlayer(player);
     return (p != null) ? p.hasPermission(permission) : false;
  }

    @Override
  public boolean playerAdd(String world, String player, String permission) {
     return false;
  }



    @Override
  public boolean playerRemove(String world, String player, String permission) {
     return false;
  }

    @Override
  public boolean groupHas(String world, String group, String permission) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public boolean groupAdd(String world, String group, String permission) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public boolean groupRemove(String world, String group, String permission) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public boolean playerInGroup(String world, String player, String group) {
     return playerHas(world, player, "groups." + group);
  }

    @Override
  public boolean playerAddGroup(String world, String player, String group) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public String[] getPlayerGroups(String world, String player) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public String getPrimaryGroup(String world, String player) {
     throw new UnsupportedOperationException(getName() + " no group permissions.");
  }

    @Override
  public String[] getGroups() {
     return new String[0];
  }

    @Override
  public boolean hasSuperPermsCompat() {
     return true;
  }

    @Override
  public boolean hasGroupSupport() {
     return false;
  }
}