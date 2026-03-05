package net.milkbowl.vault.character;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.AccountPermission;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Character {

    private final OfflinePlayer player;
    private final String displayName;
    private final UUID uuid;
    private final World world;

    private final Permission perms = Vault.getInstance().getPerms();
    private final Economy eco = Vault.getInstance().getEcon();

    Character(Player player) {
        this.player = player;
        this.displayName = player.getName();
        this.uuid = player.getUniqueId();
        this.world = player.getWorld();
    }

    Character(OfflinePlayer player) {
        this.player = player;
        this.displayName = player.getName();
        this.uuid = player.getUniqueId();
        this.world = player.getLocation() != null ? player.getLocation().getWorld() : null;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UUID getUUID() {
        return uuid;
    }

    public World getWorld() {
        return world;
    }

    public boolean hasPermission(String permission) {
        return player.getPlayer() != null && perms.playerHas(player.getPlayer(), permission);
    }

    public boolean hasPermission(String world, String permission) {
        return player.getPlayer() != null && perms.playerHas(world, player.getPlayer(), permission);
    }

    public boolean addPermission(String permission) {
        return player.getPlayer() != null && perms.playerAdd(player.getPlayer(), permission);
    }

    public boolean addPermission(String world, String permission) {
        return player.getPlayer() != null && perms.playerAdd(world, player.getPlayer(), permission);
    }

    public boolean removePermission(String permission) {
        return player.getPlayer() != null && perms.playerRemove(player.getPlayer(), permission);
    }

    public boolean removePermission(String world, String permission) {
        return player.getPlayer() != null && perms.playerRemove(world, player.getPlayer(), permission);
    }

    public boolean addTransient(String permission) {
        return player.getPlayer() != null && perms.playerAddTransient(player.getPlayer(), permission);
    }

    public boolean addTransient(String world, String permission) {
        return player.getPlayer() != null && perms.playerAddTransient(world, player.getPlayer(), permission);
    }

    public boolean removeTransient(String permission) {
        return player.getPlayer() != null && perms.playerRemoveTransient(player.getPlayer(), permission);
    }

    public boolean removeTransient(String world, String permission) {
        return player.getPlayer() != null && perms.playerRemoveTransient(world, player.getPlayer(), permission);
    }

    public boolean hasGroup(String group) {
        return player.getPlayer() != null && perms.playerInGroup(player.getPlayer(), group);
    }

    public boolean hasGroup(String world, String group) {
        return player.getPlayer() != null && perms.playerInGroup(world, player.getPlayer(), group);
    }

    public boolean addGroup(String group) {
        return player.getPlayer() != null && perms.playerAddGroup(player.getPlayer(), group);
    }

    public boolean addGroup(String world, String group) {
        return player.getPlayer() != null && perms.playerAddGroup(world, player.getPlayer(), group);
    }

    public boolean removeGroup(String group) {
        return player.getPlayer() != null && perms.playerRemoveGroup(player.getPlayer(), group);
    }

    public boolean removeGroup(String world, String group) {
        return player.getPlayer() != null && perms.playerRemoveGroup(world, player.getPlayer(), group);
    }

    public String[] getGroups(String world) {
        return player.getPlayer() != null ? perms.getPlayerGroups(world, player.getPlayer()) : new String[0];
    }

    public String[] getGroups() {
        return player.getPlayer() != null ? perms.getPlayerGroups(player.getPlayer()) : new String[0];
    }

    public String getPrimaryGroup(String world) {
        return player.getPlayer() != null ? perms.getPrimaryGroup(world, player.getPlayer()) : null;
    }

    // VintageVault Methods

    public boolean addTimedPermission(String permission, long time) {
        try {
            return player.getPlayer() != null && perms.playerAddTimedPermission(world.getName(), player.getPlayer(), permission, time).get(200, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Ignore for now
        }
        return false;
    }

    public boolean removeTimedPermission(String permission) {
        try {
            return player.getPlayer() != null && perms.playerRemoveTimedPermission(world.getName(), player.getPlayer(), permission).get(200, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Ignore for now
        }
        return false;
    }

    public String[] getAllPermissions() {
        try {
            return player.getPlayer() != null ? perms.getPlayerAllPermissions(player.getPlayer()).get(200, TimeUnit.MILLISECONDS) : new String[0];
        } catch (Exception e) {
            // Ignore for now
        }
        return new String[0];
    }

    public String[] getWorldPermissions(String world) {
        try {
            return player.getPlayer() != null ? perms.getPlayerWorldPermissions(world, player.getPlayer()).get(200, TimeUnit.MILLISECONDS) : new String[0];
        } catch (Exception e) {
            // Ignore for now
        }
        return new String[0];
    }

    public String[] getOwnPermissions() {
        try {
            return player.getPlayer() != null ? perms.getPlayerOwnPermissions(world.getName(), player.getPlayer()).get(200, TimeUnit.MILLISECONDS) : new String[0];
        } catch (Exception e) {
            // Ignore for now
        }
        return new String[0];
    }

    // Economy Implementations

    public double getBalance() {
        try {
            return eco.getBalanceAsync(player.getUniqueId(), world.getName()).get(500, TimeUnit.MILLISECONDS).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public double getBalance(String world) {
        try {
            return eco.getBalanceAsync(player.getUniqueId(), world).get(500, TimeUnit.MILLISECONDS).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean has(double amount) {
        try {
            return eco.hasAsync(player.getUniqueId(), world.getName(), BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public EconomyResponse withdraw(String plugin, double amount) {
        try {
            return eco.withdrawAccount(plugin, player.getUniqueId(), world.getName(), BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new EconomyResponse(amount, getBalance(), EconomyResponse.ResponseType.FAILURE, "Took Too Long or Not Implemented");
        }
    }

    public EconomyResponse deposit(String plugin, double amount) {
        try {
            return eco.depositAccount(plugin, player.getUniqueId(), world.getName(), BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new EconomyResponse(amount, getBalance(), EconomyResponse.ResponseType.FAILURE, "Took Too Long or Not Implemented");
        }
    }

    public boolean createPlayerAccount() {
        try {
            return eco.createAccountAsync(player.getUniqueId(), world.getName(), true).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createPlayerAccount(String world) {
        try {
            return eco.createAccountAsync(player.getUniqueId(), world, true).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public EconomyResponse createBank(String name) {
        try {
            return eco.createBankAsync(name, player.getUniqueId()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new EconomyResponse(0, getBalance(), EconomyResponse.ResponseType.FAILURE, "Took Too Long or Not Implemented");
        }
    }

    public EconomyResponse deleteBank(String name) {
        try {
            return eco.deleteBankAsync(name).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new EconomyResponse(0, getBalance(), EconomyResponse.ResponseType.FAILURE, "Took Too Long or Not Implemented");
        }
    }

    public boolean hasAccount() {
        try {
            return eco.hasAccountAsync(player.getUniqueId(), world.getName()).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasAccount(String world) {
        try {
            return eco.hasAccountAsync(player.getUniqueId(), world).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    // Multi-Currency Implementations Non-VaultUnlocked

    public boolean deposit(String plugin, BigDecimal amount, String world) {
        try {
            return eco.depositAccount(plugin, getUUID(), world, amount).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean depositCurrency(String plugin, BigDecimal amount, String currency) {
        try {
            return eco.depositAccount(plugin, getUUID(), getWorld().toString(), amount, currency).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean depositCurrency(String plugin, BigDecimal amount, String currency, String world) {
        try {
            return eco.depositAccount(plugin, getUUID(), world, amount, currency).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean withdraw(String plugin, BigDecimal amount, String world) {
        try {
            return eco.withdrawAccount(plugin, getUUID(), world, amount).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean withdrawCurrency(String plugin, BigDecimal amount, String currency) {
        try {
            return eco.withdrawAccount(plugin, getUUID(), getWorld().toString(), amount, currency).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean withdrawCurrency(String plugin, BigDecimal amount, String currency, String world) {
        try {
            return eco.withdrawAccount(plugin, getUUID(), world, amount, currency).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public BigDecimal getAccountBalance() {
        try {
            return eco.getBalanceAsync(getUUID(), getWorld().toString()).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return eco.getCurrencyMin(eco.getDefaultCurrencyName());
        }
    }

    public BigDecimal getAccountBalance(String currency) {
        try {
            return eco.getBalanceAsync(getUUID(), currency).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return eco.getCurrencyMin(eco.getDefaultCurrencyName());
        }
    }

    public BigDecimal getAccountBalance(String currency, String world) {
        try {
            return eco.getBalanceAsync(getUUID(), currency, world).get(500, TimeUnit.MILLISECONDS);
        }
        catch (Exception e) {
            return eco.getCurrencyMin(currency, world);
        }
    }

    public boolean hasPlayerAccount() {
        try {
            return eco.hasAccountAsync(getUUID(), getWorld().getName()).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasPlayerAccount(String world) {
        try {
            return eco.hasAccountAsync(getUUID(), world).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }


    public boolean accountHas(BigDecimal amount) {
        try {
            return eco.hasAsync(getUUID(), getWorld().getName(), amount).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean accountHasInWorld(BigDecimal amount, String world) {
        try {
            return eco.hasAsync(getUUID(), world, amount).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean accountHas(BigDecimal amount, String currency) {
        try {
            return eco.hasAsync(getUUID(), getWorld().toString(), amount, currency).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean accountHasInWorld(BigDecimal amount, String currency, String world) {
        try {
            return eco.hasAsync(getUUID(), world, amount, currency).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean accountSupportsCurrency(String currency) {
        try {
            return eco.accountSupportsCurrency(getUUID(), currency).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean accountSupportsCurrency(String currency, String world) {
        try {
            return eco.accountSupportsCurrency(getUUID(), currency, world).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createAccount(UUID accountId, String world) {
        try {
            return eco.createAccountAsync(getUUID(), world, true).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }

    }

    public boolean createAccount(UUID accountId, String worldName, String accountName) {
        try {
            return eco.createAccountAsync(getUUID(), worldName, true, accountName).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAccount(String plugin, BigDecimal amount) {
        try {
            return eco.setAccountAsync(plugin, getUUID(), world.getName(), amount).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAccount(String plugin, BigDecimal amount, String worldName) {
        try {
            return eco.setAccountAsync(plugin, getUUID(), worldName, amount).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAccount(String plugin, String currency, BigDecimal amount, String worldName) {
        try {
            return eco.setAccountAsync(plugin, getUUID(), worldName, currency, amount).get(1000, TimeUnit.MILLISECONDS).transactionSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createSharedAccount(UUID accountId, String name) {
        try {
            return eco.createSharedAccountAsync(accountId, name, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getOwnedAccounts() {
        try {
            return eco.accountsOwnedByAsync(getUUID()).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<String> getAccountsMemberOf() {
        try {
            return eco.accountsMemberOfAsync(getUUID()).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<String> getAccountsAccessTo(AccountPermission permission) {
        try {
            return eco.accountsAccessToAsync(getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean isAccountOwner(UUID accountId) {
        try {
            return eco.isAccountOwnerAsync(accountId, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccountMember(UUID accountId) {
        try {
            return eco.isAccountMemberAsync(accountId, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAccountOwner(UUID accountId) {
        try {
            return eco.setAccountOwnerAsync(accountId, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addAccountMember(UUID accountId) {
        try {
            return eco.addAccountMemberAsync(accountId, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addAccountMember(UUID accountId, AccountPermission permission) {
        try {
            return eco.addAccountMemberAsync(accountId, getUUID(), permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeAccountMember(UUID accountId) {
        try {
            return eco.removeAccountMemberAsync(accountId, getUUID()).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasAccountPermission(UUID accountId, AccountPermission permission) {
        try {
            return eco.hasAccountPermissionAsync(accountId, getUUID(), permission).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAccountPermission(String plugin, UUID accountId, AccountPermission permission, boolean value) {
        try {
            return eco.updateAccountPermissionAsync(plugin, accountId, getUUID(), permission, value).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }


}
