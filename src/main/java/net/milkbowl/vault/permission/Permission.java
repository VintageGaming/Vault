package net.milkbowl.vault.permission;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.milkbowl.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public abstract class Permission
{
   protected static final Logger log = Logger.getLogger("Minecraft");
   protected Plugin plugin = null;


    public abstract String getName();

    public abstract boolean isEnabled();

    public abstract boolean hasGroupSupport();

    public abstract boolean hasSuperPermsCompat();

    //-------------------------------------------------has() & playerHas() methods----------------------------------------------

    public CompletableFuture<Boolean> hasAsync(@Nonnull CommandSender sender, String permission) {
        return CompletableFuture.supplyAsync(() -> sender.hasPermission(permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    public CompletableFuture<Boolean> hasAsync(@Nonnull Player player, @NotNull String permission) {
        return CompletableFuture.supplyAsync(() -> has(player, permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    public CompletableFuture<Boolean> hasAsync(String worldName, @NotNull Player player, @NotNull String permission) {
        return CompletableFuture.supplyAsync(() -> playerHas(worldName, player, permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // DON'T FORGET TO OVERRIDE THIS METHOD
    public CompletableFuture<Boolean> hasAsync(String worldName, @Nonnull OfflinePlayer player, String permission) {
        return CompletableFuture.supplyAsync(() -> playerHas(worldName, player.getName(), permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> hasAsync(String worldName, @Nonnull String player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> hasAsync( worldName, offlinePlayer, permission))
                .exceptionally(ex -> false);
    }

    @Deprecated // Calls playersHas(String, String, String)
    public boolean has(String world, @Nonnull String player, @Nonnull String permission) {
        if (world == null) {
            return playerHas((String)null, player, permission);
        }
        return playerHas(world, player, permission);
    }

    @Deprecated // Calls playerHas(String, String, String)
    public boolean has(World world, @Nonnull String player, @Nonnull String permission) {
        if (world == null) {
            return playerHas((String)null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }

    public boolean has(@Nonnull CommandSender sender, @Nonnull String permission) {
        try {
            return hasAsync(sender, permission).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean has(@Nonnull Player player, @Nonnull String permission) {
        try {
            return hasAsync(player, permission).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }


    // This was Vaults Original abstract method
    @Deprecated
    public boolean playerHas(String worldName, @Nonnull String player, @Nonnull String permission) {
        try {
            return hasAsync(worldName, player, permission).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated // Calls playerHas(String, String, String)
    public boolean playerHas(World world, @Nonnull String player, @Nonnull String permission) {
        if (world == null) {
            return playerHas((String)null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }


    // Calls playerHas(String, String, String)
    public boolean playerHas(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        if (world == null) {
            return has((String)null, player.getName(), permission);
        }
        return has(world, player.getName(), permission);
    }

    public boolean playerHas(@Nonnull Player player, @Nonnull String permission) {
        return has(player, permission);
    }

    //-----------------------------------------------playerAdd() methods-----------------------------------------

    // Main Method for Player
    public CompletableFuture<Boolean> playerAddAsync(String world, @Nonnull Player player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> playerAdd(player.getWorld().getName(), player.getName(), permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // Don't Forget to Override this Method - Main Method for OfflinePlayer
    public CompletableFuture<Boolean> playerAddAsync(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (player.hasPlayedBefore()) {
                    if (player.isOnline())
                        return playerAdd(world, player.getPlayer(), permission);
                    return playerAdd((String) null, player.getName(), permission);
                }
            } catch (Exception e) {
                // Invalid Player
            }
            return false;
        }, Vault.vaultPermissionService).exceptionally(ex -> false);
    }

    public CompletableFuture<Boolean> playerAddAsync(@Nonnull Player player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> playerAdd(player.getWorld().getName(), player.getName(), permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> playerAddAsync(String world, @Nonnull String player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> playerAddAsync( world, offlinePlayer, permission))
                .exceptionally(ex -> false);
    }

    // New Main Method
    public CompletableFuture<Boolean> playerAddTransientAsync(String world, @Nonnull Player player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
            for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
                if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(this.plugin)) {
                    paInfo.getAttachment().setPermission(permission, true);
                    return true;
                }
            }

            PermissionAttachment attach = player.addAttachment(this.plugin);
            attach.setPermission(permission, true);

            return true;
        }, Vault.vaultPermissionService).exceptionally(ex -> false);
    }

    public CompletableFuture<Boolean> playerAddTransientAsync(@Nonnull Player player, @Nonnull String permission) {
        return playerAddTransientAsync((String) null, player, permission);
    }

    @Deprecated // This was Vaults Original abstract Method
    public boolean playerAdd(String worldName, @Nonnull String player, @Nonnull String permission) {
        try {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
            if (offlinePlayer.hasPlayedBefore()) {
                if (offlinePlayer.isOnline())
                    return playerAddAsync(worldName, offlinePlayer.getPlayer(), permission).get(500, TimeUnit.MILLISECONDS);
                return playerAddAsync(worldName, offlinePlayer, permission).get(1000, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    @Deprecated
    public boolean playerAdd(World world, @Nonnull String player, @Nonnull String permission) {
        if (world == null) {
            return playerAdd((String)null, player, permission);
        }
        return playerAdd(world.getName(), player, permission);
    }

    public boolean playerAdd(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        if (world == null) {
            return playerAdd((String)null, player.getName(), permission);
        }
        return playerAdd(world, player.getName(), permission);
    }

    public boolean playerAdd(@Nonnull Player player, @Nonnull String permission) {
        return playerAdd(player.getWorld().getName(), (OfflinePlayer)player, permission);
    }

    // Vaults Original Implementation (Defaulted Method)
    public boolean playerAddTransient(@Nonnull Player player, @Nonnull String permission) {
        return playerAddTransient((String) null, player, permission);
    }

    public boolean playerAddTransient(@Nonnull OfflinePlayer player, @Nonnull String permission) throws UnsupportedOperationException {
        try {
            if (player.isOnline()) {
                return playerAddTransientAsync((Player) player, permission).get(1000, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            // Invalid Player or Took Too Long
        }
        throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
    }

    public boolean playerAddTransient(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        return playerAddTransient(player, permission);
    }

    public boolean playerAddTransient(String worldName, @Nonnull Player player, @Nonnull String permission) {
        try {
            return playerAddTransientAsync(player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    //--------------------------------------------------playerRemove() methods------------------------------------------

    // New Main Method
    public CompletableFuture<Boolean> playerRemoveAsync(String worldName, @Nonnull Player player, String permission) {
        return CompletableFuture.supplyAsync(() -> playerRemove(worldName, player, permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    public CompletableFuture<Boolean> playerRemoveAsync(@Nonnull Player player, String permission) {
        return playerRemoveTransientAsync(player.getWorld().getName(), player, permission);
    }

    public CompletableFuture<Boolean> playerRemoveAsync(String worldName, @Nonnull OfflinePlayer player, String permission) {
        return CompletableFuture.supplyAsync(() -> playerRemove(worldName, player, permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> playerRemoveAsync(String worldName, @Nonnull String player, String permission) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> playerRemoveAsync( worldName, offlinePlayer, permission))
                .exceptionally(ex -> false);
    }

    // New Main Method
    public CompletableFuture<Boolean> playerRemoveTransientAsync(String world, @Nonnull Player player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
            for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
                if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(this.plugin)) {
                    paInfo.getAttachment().unsetPermission(permission);
                    return true;
                }
            }
            return false;
        }, Vault.vaultPermissionService);
    }

    public CompletableFuture<Boolean> playerRemoveTransientAsync(@Nonnull Player player, @Nonnull String permission) {
        return playerRemoveTransientAsync(player.getWorld().getName(), player, permission);
    }

    public CompletableFuture<Boolean> playerRemoveTransientAsync(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (player.hasPlayedBefore()) {
                    if (player.isOnline())
                        return playerRemoveTransientAsync(world, player.getPlayer(), permission).get(1000, TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                // Invalid Player
            }
            return false;
        }, Vault.vaultPermissionService);
    }

    @Deprecated // Vaults Original abstract Method
    public boolean playerRemove(String worldName, @Nonnull String player, @Nonnull String permission) {
        try {
            return playerRemoveAsync(worldName, player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean playerRemove(String world, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        try {
            return playerRemoveAsync(world, player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    @Deprecated
    public boolean playerRemove(World world, @Nonnull String player, @Nonnull String permission) {
        if (world == null) {
            return playerRemove((String)null, player, permission);
        }
        return playerRemove(world.getName(), player, permission);
    }

    public boolean playerRemove(@Nonnull Player player, @Nonnull String permission) {
        try {
            return playerRemoveAsync(player.getWorld().getName(), player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    // Vaults Original abstract Method
    public boolean playerRemoveTransient(@Nonnull Player player, @Nonnull String permission) {
        try {
            return playerRemoveTransientAsync(player.getWorld().getName(), player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    public boolean playerRemoveTransient(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        return playerRemoveTransient(player, permission);
    }

    public boolean playerRemoveTransient(String worldName, @Nonnull Player player, @Nonnull String permission) {
        return playerRemoveTransient(player, permission);
    }

    public boolean playerRemoveTransient(@Nonnull OfflinePlayer player, @Nonnull String permission) {
        try {
            return playerRemoveTransientAsync(null, player, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }



    //----------------------------------------------group has, add, remove methods--------------------------------------

    // New Main Method
    public CompletableFuture<Boolean> groupHasAsync(String worldName, @Nonnull String group, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> groupHas(worldName, group, permission), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // New Main Method
    public CompletableFuture<Boolean> groupAddAsync(String worldName, @Nonnull String group, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> groupAdd(worldName, group, permission), Vault.vaultPermissionService);
    }

    // New Main Method
    public CompletableFuture<Boolean> groupRemoveAsync(String worldName, @Nonnull String group, @Nonnull String permission) {
        return CompletableFuture.supplyAsync(() -> groupRemove(worldName, group, permission));
    }


    // Vaults Original abstract Method
    public boolean groupHas(String worldName, @Nonnull String group, @Nonnull String permission) {
        try {
            return groupHasAsync(worldName, group, permission).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    public boolean groupHas(World world, @Nonnull String group, @Nonnull String permission) {
        if (world == null) {
            return groupHas((String)null, group, permission);
        }
        return groupHas(world.getName(), group, permission);
    }

    // Vaults Original abstract Method
    public boolean groupAdd(String worldName, @Nonnull String group, @Nonnull String permission) {
        try {
            return groupAddAsync(worldName, group, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    public boolean groupAdd(World world, String group, String permission) {
        if (world == null) {
            return groupAdd((String)null, group, permission);
        }
        return groupAdd(world.getName(), group, permission);
    }

    // Vaults Original Abstract Method
    public boolean groupRemove(String worldName, @Nonnull String group, @Nonnull String permission) {
        try {
            return groupRemoveAsync(worldName, group, permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    public boolean groupRemove(World world, String group, String permission) {
        if (world == null) {
            return groupRemove((String)null, group, permission);
        }
        return groupRemove(world.getName(), group, permission);
    }

    //--------------------------------------------playerInGroup Methods----------------------------------------------

    // New Main Method for Player
    public CompletableFuture<Boolean> playerInGroupAsync(String worldName, @Nonnull Player player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerInGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // New Main Method for OfflinePlayer
    public CompletableFuture<Boolean> playerInGroupAsync(String worldName, @Nonnull OfflinePlayer player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerInGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> playerInGroupAsync(String worldName, @Nonnull String player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> playerInGroupAsync( worldName, offlinePlayer, group))
                .exceptionally(ex -> false);
    }

    @Deprecated // Vaults Original abstract Method
    public boolean playerInGroup(String worldName, @Nonnull String player, @Nonnull String group) {
        try {
            return playerInGroupAsync(worldName, player, group).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated
    public boolean playerInGroup(World world, @Nonnull String player, @Nonnull String group) {
        if (world == null) {
            return playerInGroup((String)null, player, group);
        }
        return playerInGroup(world.getName(), player, group);
    }

    public boolean playerInGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
        if (world == null) {
            return playerInGroup((String)null, player.getName(), group);
        }
        return playerInGroup(world, player.getName(), group);
    }

    public boolean playerInGroup(@Nonnull Player player, @Nonnull String group) {
        return playerInGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
    }

    //----------------------------------------------playerAddGroup() Methods--------------------------------------------

    // New Main Method for Player
    public CompletableFuture<Boolean> playerAddGroupAsync(String worldName, @Nonnull Player player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerAddGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // New Main Method for Offline Player
    public CompletableFuture<Boolean> playerAddGroupAsync(String worldName, @Nonnull OfflinePlayer player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerAddGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> playerAddGroupAsync(String worldName, @Nonnull String player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> playerAddGroupAsync( worldName, offlinePlayer, group))
                .exceptionally(ex -> false);

    }

    @Deprecated // Vault Original abstract Method
    public boolean playerAddGroup(String worldName, @Nonnull String player, @Nonnull String group) {
        try {
            return playerAddGroupAsync(worldName, player, group).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated
    public boolean playerAddGroup(World world, @Nonnull String player, @Nonnull String group) {
        if (world == null) {
            return playerAddGroup((String)null, player, group);
        }
        return playerAddGroup(world.getName(), player, group);
    }

    public boolean playerAddGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
    if (world == null) {
        return playerAddGroup((String)null, player.getName(), group);
    }
        return playerAddGroup(world, player.getName(), group);
    }

    public boolean playerAddGroup(@Nonnull Player player, @Nonnull String group) {
        return playerAddGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
    }

    //----------------------------------------------playerRemoveGroup() Methods----------------------------------------

    // New Main Method for Player
    public CompletableFuture<Boolean> playerRemoveGroupAsync(String worldName, @Nonnull Player player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerRemoveGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    // New Main Method for OfflinePlayer
    public CompletableFuture<Boolean> playerRemoveGroupAsync(String worldName, @Nonnull OfflinePlayer player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> playerRemoveGroup(worldName, player, group), Vault.vaultPermissionService)
                .exceptionally(ex -> false);
    }

    private CompletableFuture<Boolean> playerRemoveGroupAsync(String worldName, @Nonnull String player, @Nonnull String group) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> playerRemoveGroupAsync( worldName, offlinePlayer, group))
                .exceptionally(ex -> false);

    }

    @Deprecated // Vaults Original abstract Method
    public boolean playerRemoveGroup(String worldName, @Nonnull String player, @Nonnull String group) {
        try {
            return playerRemoveGroupAsync(worldName, player, group).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return false;
    }

    @Deprecated
    public boolean playerRemoveGroup(World world, @Nonnull String player, @Nonnull String group) {
        if (world == null) {
            return playerRemoveGroup((String)null, player, group);
        }
        return playerRemoveGroup(world.getName(), player, group);
    }

    public boolean playerRemoveGroup(String world, @Nonnull OfflinePlayer player, @Nonnull String group) {
        if (world == null) {
            return playerRemoveGroup((String)null, player.getName(), group);
        }
        return playerRemoveGroup(world, player.getName(), group);
    }

    public boolean playerRemoveGroup(@Nonnull Player player, @Nonnull String group) {
        return playerRemoveGroup(player.getWorld().getName(), (OfflinePlayer)player, group);
    }

    //----------------------------------------------player groups Methods----------------------------------------------

    // New Main Method for Player
    public CompletableFuture<String[]> getPlayerGroupsAsync(String worldName, @Nonnull Player player) {
        return CompletableFuture.supplyAsync(() -> getPlayerGroups(worldName, player), Vault.vaultPermissionService)
                .exceptionally(ex -> new String[0]);
    }

    // New Main Method for OfflinePlayer
    public CompletableFuture<String[]> getPlayerGroupsAsync(String worldName, @Nonnull OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> getPlayerGroups(worldName, player), Vault.vaultPermissionService)
                .exceptionally(ex -> new String[0]);
    }

    private CompletableFuture<String[]> getPlayerGroupsAsync(String worldName, String player) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> getPlayerGroupsAsync( worldName, offlinePlayer))
                .exceptionally(ex -> new String[0]);
    }

    @Deprecated // Vaults Original Abstract Method
    public String[] getPlayerGroups(String worldName, @Nonnull String player) {
        try {
            return getPlayerGroupsAsync(worldName, player).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return new String[0];
    }

    @Deprecated
    public String[] getPlayerGroups(World world, @Nonnull String player) {
        if (world == null) {
            return getPlayerGroups((String)null, player);
        }
        return getPlayerGroups(world.getName(), player);
    }

    public String[] getPlayerGroups(String world, @Nonnull OfflinePlayer player) {
        return getPlayerGroups(world, player.getName());
    }

    public String[] getPlayerGroups(@Nonnull Player player) {
        return getPlayerGroups(player.getWorld().getName(), (OfflinePlayer)player);
    }

    //-------------------------------------getGroups() & getPrimaryGroup() Methods-------------------------------------

    // New Main Method for getGroups()
    public CompletableFuture<String[]> getGroupsAsync() {
        return CompletableFuture.supplyAsync(this::getGroups, Vault.vaultPermissionService).exceptionally(ex -> null);
    }

    // New Main Method for Player
    public CompletableFuture<String> getPrimaryGroupAsync(String worldName, @Nonnull Player player) {
        return CompletableFuture.supplyAsync(() -> getPrimaryGroup(worldName, player), Vault.vaultPermissionService)
                .exceptionally(ex -> null);
    }

    // New Main Method for OfflinePlayer
    public CompletableFuture<String> getPrimaryGroupAsync(String worldName, @Nonnull OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> getPrimaryGroup(worldName, player), Vault.vaultPermissionService)
                .exceptionally(ex -> null);
    }

    private CompletableFuture<String> getPrimaryGroupAsync(String worldName, @Nonnull String player) {
        return CompletableFuture.supplyAsync(() -> {
                    OfflinePlayer op;
                    try {
                        op = Bukkit.getOfflinePlayer(player);
                        if (op.hasPlayedBefore())
                            return op;
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(UUID.fromString(player));
                    }
                }, Vault.vaultPermissionService)
                .thenCompose(offlinePlayer -> getPrimaryGroupAsync( worldName, offlinePlayer))
                .exceptionally(ex -> null);
    }

    @Deprecated // Vaults Original abstract Method
    public String getPrimaryGroup(String worldName, @Nonnull String player) {
        try {
            return getPrimaryGroupAsync(worldName, player).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long
        }
        return "";
    }

    @Deprecated
    public String getPrimaryGroup(World world, @Nonnull String player) {
        if (world == null) {
            return getPrimaryGroup((String)null, player);
        }
        return getPrimaryGroup(world.getName(), player);
    }

    public String getPrimaryGroup(String world, @Nonnull OfflinePlayer player) {
        return getPrimaryGroup(world, player.getName());
    }

    public String getPrimaryGroup(@Nonnull Player player) {
        return getPrimaryGroup(player.getWorld().getName(), (OfflinePlayer)player);
    }

    // Vaults Original Abstract Method for getGroups()
    public String[] getGroups() {
        try {
            return getGroupsAsync().get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Took Too Long (How?? Skill issue)
        }
        return new String[0];
    }

    //-------------------------------------------------VintageVaultMethods-----------------------------------------------

    //EXTRA METHODS IMPLEMENTED AFTER VAULT 1.7.3

    public CompletableFuture<Boolean> playerAddTimedPermission(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission, long time) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented Player Timed Permissions feature with Vault!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    public CompletableFuture<Boolean> playerRemoveTimedPermission(String worldName, @Nonnull OfflinePlayer player, @Nonnull String permission) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented Player Timed Permissions feature with Vault!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    public CompletableFuture<String[]> getPlayerAllPermissions(OfflinePlayer player) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getPlayerAllPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String[]> getPlayerOwnPermissions(String world, OfflinePlayer player) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getPlayerOwnPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String[]> getPlayerWorldPermissions(String world, @Nonnull OfflinePlayer player) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getPlayerWorldPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String[]> getGroupAllPermissions(String world, @Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupAllPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String[]> getGroupOwnPermissions(@Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupOwnPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String[]> getGroupWorldPermissions(String worldName, @Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupWorldPermissions feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<Boolean> groupAddTimedPermission(String worldName, @Nonnull String group, @Nonnull String permission, long time) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented Timed Permissions feature with Vault!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    public CompletableFuture<Boolean> groupRemoveTimedPermission(String worldName, @Nonnull String group, @Nonnull String permission) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented Timed Permissions feature with Vault!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    //Added Support for GroupManager
    public CompletableFuture<String[]> getGroupParents(String worldName, @Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupParents feature!");
        return CompletableFuture.supplyAsync(() -> new String[0]);
    }

    public CompletableFuture<String> getGroupPrefix(@Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupPrefix feature!");
        return CompletableFuture.supplyAsync(() -> "");
    }

    public CompletableFuture<Boolean> setGroupPrefix(@Nonnull String group, @Nonnull String prefix) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented setGroupPrefix feature!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    public CompletableFuture<String> getGroupSuffix(@Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getGroupSuffix feature!");
        return CompletableFuture.supplyAsync(() -> "");
    }

    public CompletableFuture<Boolean> setGroupSuffix(@Nonnull String group, @Nonnull String suffix) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented setGroupSuffix feature");
        return CompletableFuture.supplyAsync(() -> false);
    }

    //Added Support for GroupManager
    public CompletableFuture<Boolean> groupCreate(String worldName, @Nonnull String group, boolean isDefault) {
        log.severe("Plugin " + plugin.getName() + ", Doesn't allow Vault to Create Groups!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    //Added Support for GroupManager
    public CompletableFuture<Boolean> groupDelete(String worldName, @Nonnull String group) {
        log.severe("Plugin " + plugin.getName() + ", Doesn't allow Vault to Delete Groups!");
        return CompletableFuture.supplyAsync(() -> false);
    }

    public CompletableFuture<String> getDefaultGroup(@Nonnull String world) {
        log.severe("Plugin " + plugin.getName() + ", has not Implemented getDefaultGroup feature!");
        return CompletableFuture.supplyAsync(() -> "");
    }

}