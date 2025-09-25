/*     */ package net.milkbowl.vault.chat;
/*     */ 
/*     */ import net.milkbowl.vault.permission.Permission;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Chat
/*     */ {
/*     */   private Permission perms;
/*     */   
/*     */   public Chat(Permission perms) {
/*  33 */     this.perms = perms;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract String getPlayerPrefix(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerPrefix(String world, OfflinePlayer player) {
/*  67 */     return getPlayerPrefix(world, player.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPlayerPrefix(World world, String player) {
/*  80 */     return getPlayerPrefix(world.getName(), player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerPrefix(Player player) {
/*  91 */     return getPlayerPrefix(player.getWorld().getName(), (OfflinePlayer)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerPrefix(String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerPrefix(String world, OfflinePlayer player, String prefix) {
/* 114 */     setPlayerPrefix(world, player.getName(), prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerPrefix(World world, String player, String prefix) {
/* 128 */     setPlayerPrefix(world.getName(), player, prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerPrefix(Player player, String prefix) {
/* 138 */     setPlayerPrefix(player.getWorld().getName(), (OfflinePlayer)player, prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract String getPlayerSuffix(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerSuffix(String world, OfflinePlayer player) {
/* 160 */     return getPlayerSuffix(world, player.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPlayerSuffix(World world, String player) {
/* 173 */     return getPlayerSuffix(world.getName(), player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerSuffix(Player player) {
/* 183 */     return getPlayerSuffix(player.getWorld().getName(), (OfflinePlayer)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerSuffix(String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerSuffix(String world, OfflinePlayer player, String suffix) {
/* 205 */     setPlayerSuffix(world, player.getName(), suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerSuffix(World world, String player, String suffix) {
/* 218 */     setPlayerSuffix(world.getName(), player, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerSuffix(Player player, String suffix) {
/* 228 */     setPlayerSuffix(player.getWorld().getName(), (OfflinePlayer)player, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getGroupPrefix(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroupPrefix(World world, String group) {
/* 246 */     return getGroupPrefix(world.getName(), group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupPrefix(String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupPrefix(World world, String group, String prefix) {
/* 264 */     setGroupPrefix(world.getName(), group, prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getGroupSuffix(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroupSuffix(World world, String group) {
/* 282 */     return getGroupSuffix(world.getName(), group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupSuffix(String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupSuffix(World world, String group, String suffix) {
/* 300 */     setGroupSuffix(world.getName(), group, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlayerInfoInteger(String world, OfflinePlayer player, String node, int defaultValue) {
/* 312 */     return getPlayerInfoInteger(world, player.getName(), node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract int getPlayerInfoInteger(String paramString1, String paramString2, String paramString3, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getPlayerInfoInteger(World world, String player, String node, int defaultValue) {
/* 339 */     return getPlayerInfoInteger(world.getName(), player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlayerInfoInteger(Player player, String node, int defaultValue) {
/* 350 */     return getPlayerInfoInteger(player.getWorld().getName(), (OfflinePlayer)player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoInteger(String world, OfflinePlayer player, String node, int value) {
/* 361 */     setPlayerInfoInteger(world, player.getName(), node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerInfoInteger(String paramString1, String paramString2, String paramString3, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerInfoInteger(World world, String player, String node, int value) {
/* 387 */     setPlayerInfoInteger(world.getName(), player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoInteger(Player player, String node, int value) {
/* 397 */     setPlayerInfoInteger(player.getWorld().getName(), (OfflinePlayer)player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getGroupInfoInteger(String paramString1, String paramString2, String paramString3, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGroupInfoInteger(World world, String group, String node, int defaultValue) {
/* 419 */     return getGroupInfoInteger(world.getName(), group, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupInfoInteger(String paramString1, String paramString2, String paramString3, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupInfoInteger(World world, String group, String node, int value) {
/* 439 */     setGroupInfoInteger(world.getName(), group, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPlayerInfoDouble(String world, OfflinePlayer player, String node, double defaultValue) {
/* 451 */     return getPlayerInfoDouble(world, player.getName(), node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract double getPlayerInfoDouble(String paramString1, String paramString2, String paramString3, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getPlayerInfoDouble(World world, String player, String node, double defaultValue) {
/* 479 */     return getPlayerInfoDouble(world.getName(), player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPlayerInfoDouble(Player player, String node, double defaultValue) {
/* 490 */     return getPlayerInfoDouble(player.getWorld().getName(), (OfflinePlayer)player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoDouble(String world, OfflinePlayer player, String node, double value) {
/* 501 */     setPlayerInfoDouble(world, player.getName(), node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerInfoDouble(String paramString1, String paramString2, String paramString3, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerInfoDouble(World world, String player, String node, double value) {
/* 525 */     setPlayerInfoDouble(world.getName(), player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoDouble(Player player, String node, double value) {
/* 535 */     setPlayerInfoDouble(player.getWorld().getName(), (OfflinePlayer)player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double getGroupInfoDouble(String paramString1, String paramString2, String paramString3, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGroupInfoDouble(World world, String group, String node, double defaultValue) {
/* 557 */     return getGroupInfoDouble(world.getName(), group, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupInfoDouble(String paramString1, String paramString2, String paramString3, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupInfoDouble(World world, String group, String node, double value) {
/* 577 */     setGroupInfoDouble(world.getName(), group, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlayerInfoBoolean(String world, OfflinePlayer player, String node, boolean defaultValue) {
/* 589 */     return getPlayerInfoBoolean(world, player.getName(), node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract boolean getPlayerInfoBoolean(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean getPlayerInfoBoolean(World world, String player, String node, boolean defaultValue) {
/* 617 */     return getPlayerInfoBoolean(world.getName(), player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlayerInfoBoolean(Player player, String node, boolean defaultValue) {
/* 628 */     return getPlayerInfoBoolean(player.getWorld().getName(), (OfflinePlayer)player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoBoolean(String world, OfflinePlayer player, String node, boolean value) {
/* 639 */     setPlayerInfoBoolean(world, player.getName(), node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerInfoBoolean(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerInfoBoolean(World world, String player, String node, boolean value) {
/* 663 */     setPlayerInfoBoolean(world.getName(), player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoBoolean(Player player, String node, boolean value) {
/* 673 */     setPlayerInfoBoolean(player.getWorld().getName(), (OfflinePlayer)player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean getGroupInfoBoolean(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getGroupInfoBoolean(World world, String group, String node, boolean defaultValue) {
/* 695 */     return getGroupInfoBoolean(world.getName(), group, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupInfoBoolean(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupInfoBoolean(World world, String group, String node, boolean value) {
/* 715 */     setGroupInfoBoolean(world.getName(), group, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerInfoString(String world, OfflinePlayer player, String node, String defaultValue) {
/* 727 */     return getPlayerInfoString(world, player.getName(), node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract String getPlayerInfoString(String paramString1, String paramString2, String paramString3, String paramString4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPlayerInfoString(World world, String player, String node, String defaultValue) {
/* 754 */     return getPlayerInfoString(world.getName(), player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerInfoString(Player player, String node, String defaultValue) {
/* 765 */     return getPlayerInfoString(player.getWorld().getName(), (OfflinePlayer)player, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoString(String world, OfflinePlayer player, String node, String value) {
/* 776 */     setPlayerInfoString(world, player.getName(), node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void setPlayerInfoString(String paramString1, String paramString2, String paramString3, String paramString4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerInfoString(World world, String player, String node, String value) {
/* 800 */     setPlayerInfoString(world.getName(), player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerInfoString(Player player, String node, String value) {
/* 810 */     setPlayerInfoString(player.getWorld().getName(), (OfflinePlayer)player, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getGroupInfoString(String paramString1, String paramString2, String paramString3, String paramString4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroupInfoString(World world, String group, String node, String defaultValue) {
/* 832 */     return getGroupInfoString(world.getName(), group, node, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setGroupInfoString(String paramString1, String paramString2, String paramString3, String paramString4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupInfoString(World world, String group, String node, String value) {
/* 852 */     setGroupInfoString(world.getName(), group, node, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean playerInGroup(String world, OfflinePlayer player, String group) {
/* 863 */     return this.perms.playerInGroup(world, player, group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean playerInGroup(String world, String player, String group) {
/* 876 */     return this.perms.playerInGroup(world, player, group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean playerInGroup(World world, String player, String group) {
/* 889 */     return playerInGroup(world.getName(), player, group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean playerInGroup(Player player, String group) {
/* 899 */     return playerInGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPlayerGroups(String world, OfflinePlayer player) {
/* 909 */     return this.perms.getPlayerGroups(world, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String[] getPlayerGroups(String world, String player) {
/* 921 */     return this.perms.getPlayerGroups(world, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String[] getPlayerGroups(World world, String player) {
/* 933 */     return getPlayerGroups(world.getName(), player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPlayerGroups(Player player) {
/* 942 */     return getPlayerGroups(player.getWorld().getName(), (OfflinePlayer)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryGroup(String world, OfflinePlayer player) {
/* 952 */     return this.perms.getPrimaryGroup(world, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPrimaryGroup(String world, String player) {
/* 964 */     return this.perms.getPrimaryGroup(world, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPrimaryGroup(World world, String player) {
/* 976 */     return getPrimaryGroup(world.getName(), player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryGroup(Player player) {
/* 985 */     return getPrimaryGroup(player.getWorld().getName(), (OfflinePlayer)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getGroups() {
/* 993 */     return this.perms.getGroups();
/*     */   }
/*     */ }