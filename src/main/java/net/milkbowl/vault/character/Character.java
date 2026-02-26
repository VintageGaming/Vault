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
import java.util.List;
import java.util.UUID;

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
        return player.getPlayer() != null && perms.playerAddTimedPermission(world.getName(), player.getPlayer(), permission, time);
    }

    public boolean removeTimedPermission(String permission) {
        return player.getPlayer() != null && perms.playerRemoveTimedPermission(world.getName(), player.getPlayer(), permission);
    }

    public String[] getAllPermissions() {
        return player.getPlayer() != null ? perms.getPlayerAllPermissions(player.getPlayer()) : new String[0];
    }

    public String[] getWorldPermissions(String world) {
        return player.getPlayer() != null ? perms.getPlayerWorldPermissions(world, player.getPlayer()) : new String[0];
    }

    public String[] getOwnPermissions() {
        return player.getPlayer() != null ? perms.getPlayerOwnPermissions(world.getName(), player.getPlayer()) : new String[0];
    }

    // Economy Implementations

    public double getBalance() {
        return eco.getBalance(player);
    }

    public double getBalance(String world) {
        return eco.getBalance(player, world);
    }

    public boolean has(double amount) {
        return eco.has(player, amount);
    }

    public EconomyResponse withdraw(double amount) {
        return eco.withdrawPlayer(player, amount);
    }

    public EconomyResponse deposit(double amount) {
        return eco.depositPlayer(player, amount);
    }

    public boolean createPlayerAccount() {
        return eco.createPlayerAccount(player);
    }

    public boolean createPlayerAccount(String world) {
        return eco.createPlayerAccount(player, world);
    }

    public EconomyResponse createBank(String name) {
        return eco.createBank(name, player);
    }

    public EconomyResponse deleteBank(String name) {
        return eco.deleteBank(name);
    }

    public boolean hasAccount() {
        return eco.hasAccount(player);
    }

    public boolean hasAccount(String world) {
        return eco.hasAccount(player, world);
    }

    // Multi-Currency Implementations Non-VaultUnlocked

    public boolean deposit(String plugin, BigDecimal amount, String world) {
        return eco.accountDeposit(plugin, getUUID(), world, amount).transactionSuccess();
    }

    public boolean depositCurrency(String plugin, BigDecimal amount, String currency) {
        return eco.accountDeposit(plugin, getUUID(), amount, currency).transactionSuccess();
    }

    public boolean depositCurrency(String plugin, BigDecimal amount, String currency, String world) {
        return eco.accountDeposit(plugin, getUUID(), world, currency, amount).transactionSuccess();
    }

    public boolean withdraw(String plugin, BigDecimal amount, String world) {
        return eco.accountWithdraw(plugin, getUUID(), world, amount).transactionSuccess();
    }

    public boolean withdrawCurrency(String plugin, BigDecimal amount, String currency) {
        return eco.accountWithdraw(plugin, getUUID(), currency, amount).transactionSuccess();
    }

    public boolean withdrawCurrency(String plugin, BigDecimal amount, String currency, String world) {
        return eco.accountWithdraw(plugin, getUUID(), world, currency, amount).transactionSuccess();
    }

    public BigDecimal getAccountBalance() {
        return eco.getAccountBalance(getUUID());
    }

    public BigDecimal getAccountBalance(String currency) {
        return eco.getAccountBalance(getUUID(), currency);
    }

    public BigDecimal getAccountBalance(String currency, String world) {
        return eco.getAccountBalance(getUUID(), currency, world);
    }

    public boolean hasPlayerAccount() {
        return eco.hasAccount(getUUID());
    }

    public boolean hasPlayerAccount(String world) {
        return eco.hasAccount(getUUID(), world);
    }


    public boolean accountHas(BigDecimal amount) {
        return eco.accountHas(getUUID(), amount);
    }

    public boolean accountHasInWorld(BigDecimal amount, String world) {
        return eco.accountHasInWorld(getUUID(), amount, world);
    }

    public boolean accountHas(BigDecimal amount, String currency) {
        return eco.accountHas(getUUID(), amount, currency);
    }

    public boolean accountHasInWorld(BigDecimal amount, String currency, String world) {
        return eco.accountHasInWorld(getUUID(), amount, currency, world);
    }

    public boolean accountSupportsCurrency(String currency) {
        return eco.accountSupportsCurrency(getUUID(), currency);
    }

    public boolean accountSupportsCurrency(String currency, String world) {
        return eco.accountSupportsCurrency(getUUID(), currency, world);
    }

    public boolean createAccount(UUID accountId, String name) {
        return eco.createAccount(getUUID(), name, true);

    }

    public boolean createAccount(UUID accountId, String name, String worldName) {
        return eco.createAccount(getUUID(), name, worldName, true);
    }

    public boolean setAccount(String plugin, BigDecimal amount) {
        return eco.setAccount(plugin, getUUID(), amount).transactionSuccess();
    }

    public boolean setAccount(String plugin, BigDecimal amount, String worldName) {
        return eco.setAccount(plugin, getUUID(), worldName, amount).transactionSuccess();
    }

    public boolean setAccount(String plugin, String currency, BigDecimal amount, String worldName) {
        return eco.setAccount(plugin, getUUID(), worldName, currency, amount).transactionSuccess();
    }

    public boolean createSharedAccount(UUID accountId, String name) {
        return eco.createSharedAccount(accountId, name, getUUID());
    }

    public List<String> getOwnedAccounts() {
        return eco.accountsOwnedBy(getUUID());
    }

    public List<String> getAccountsMemberOf() {
        return eco.accountsMemberOf(getUUID());
    }

    public List<String> getAccountsAccessTo(AccountPermission permission) {
        return eco.accountsAccessTo(getUUID());
    }

    public boolean isAccountOwner(UUID accountId) {
        return eco.isAccountOwner(accountId, getUUID());
    }

    public boolean isAccountMember(UUID accountId) {
        return eco.isAccountMember(accountId, getUUID());
    }

    public boolean setAccountOwner(UUID accountId) {
        return eco.setAccountOwner(accountId, getUUID());
    }

    public boolean addAccountMember(UUID accountId) {
        return eco.addAccountMember(accountId, getUUID());
    }

    public boolean addAccountMember(UUID accountId, AccountPermission permission) {
        return eco.addAccountMember(accountId, getUUID(), permission);
    }

    public boolean removeAccountMember(UUID accountId) {
        return eco.removeAccountMember(accountId, getUUID());
    }

    public boolean hasAccountPermission(UUID accountId, AccountPermission permission) {
        return eco.hasAccountPermission(accountId, getUUID(), permission);
    }

    public boolean setAccountPermission(String plugin, UUID accountId, AccountPermission permission, boolean value) {
        return eco.updateAccountPermission(plugin, accountId, getUUID(), permission, value);
    }


}
