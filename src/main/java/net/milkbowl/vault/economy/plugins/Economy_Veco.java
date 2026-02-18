package net.milkbowl.vault.economy.plugins;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.veco.Veco;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class Economy_Veco implements Economy {

    private final EconomyResponse NO_BANK_SUPPORT = new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "No Bank Support");

    public Economy_Veco(Vault plugin) {
        plugin.getServer().getPluginManager().registerEvents(new Veco(), plugin);
        Veco.loadBalanceFile(plugin);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Veco";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public String format(double amount) {
        return "$" + amount;
    }

    @Override
    public String currencyNamePlural() {
        return "Dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "Dollar";
    }

    @Override
    public boolean hasAccount(String playerName) {
        return Veco.offlinePlayerAccountExists(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return Veco.offlinePlayerAccountExists(player);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        return Veco.getPlayerBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return Veco.getPlayerBalance(player);
    }

    @Override
    public double getBalance(String playerName, String worldName) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String worldName) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (!has(playerName, amount)) {
            return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Not enough money to withdraw!");
        }

        UUID uuid = Vault.getUUIDFromName(playerName);
        if (uuid == null)
            return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Player not found!");

        double newBalance = getBalance(playerName) - amount;
        Veco.setPlayerBalance(uuid, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "Withdraw Successful");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        return withdrawPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        UUID uuid = Vault.getUUIDFromName(playerName);
        if (uuid == null)
            return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Player not found!");

        double newBalance = getBalance(playerName) + amount;
        Veco.setPlayerBalance(uuid, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "Deposit Successful");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        return depositPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse createBank(String name, String playerName) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return NO_BANK_SUPPORT;
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        if (hasAccount(playerName)) return false;
        UUID uuid = Vault.getUUIDFromName(playerName);
        if (uuid == null) return false;
        Veco.setPlayerBalance(uuid, 0.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (hasAccount(player)) return false;
        Veco.setPlayerBalance(player.getUniqueId(), 0.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }
}
