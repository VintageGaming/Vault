package net.milkbowl.vault.economy;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface Economy {
  boolean isEnabled();
  
  String getName();
  
  boolean hasBankSupport();
  
  int fractionalDigits();
  
  String format(double amount);
  
  String currencyNamePlural();
  
  String currencyNameSingular();

  //------------------------------------------------------------------------------------------------------
  //-----------------------------------NOTICE: accountId OR Player UUID-----------------------------------
  //------------------------------------------------------------------------------------------------------

  default CompletableFuture<Boolean> hasAccountAsync(UUID uuid) {
      return CompletableFuture.supplyAsync(() -> hasAccount(uuid.toString()));
  }

  default CompletableFuture<Boolean> hasAccountAsync(OfflinePlayer offlinePlayer) {
      return hasAccountAsync(offlinePlayer.getUniqueId());
  }

  default CompletableFuture<Boolean> hasAccountAsync(UUID uuid, String worldName) {
      return CompletableFuture.supplyAsync(() -> hasAccount(uuid.toString(), worldName));
  }

  default CompletableFuture<Boolean> hasAccountAsync(OfflinePlayer offlinePlayer, String worldName) {
      return hasAccountAsync(offlinePlayer.getUniqueId(), worldName);
  }

  @Deprecated
  default boolean hasAccount(String accountId) {
      try {
          return hasAccountAsync(UUID.fromString(accountId)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean hasAccount(OfflinePlayer player) {
      try {
          return hasAccountAsync(player.getUniqueId()).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }
  
  @Deprecated
  default boolean hasAccount(String accountId, String worldName) {
      try {
          return hasAccountAsync(UUID.fromString(accountId), worldName).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean hasAccount(OfflinePlayer player, String worldName) {
      try {
          return hasAccountAsync(player.getUniqueId(), worldName).get(50, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return false;
      }
  }

  //-----------------------------------------Get Balance Methods----------------------------------------

  default CompletableFuture<BigDecimal> getBalanceAsync(UUID uuid) {
      return CompletableFuture.supplyAsync(() -> BigDecimal.valueOf(getBalance(uuid.toString())));
  }

  default CompletableFuture<BigDecimal> getBalanceAsync(OfflinePlayer offlinePlayer) {
      return getBalanceAsync(offlinePlayer.getUniqueId());
  }

  default CompletableFuture<BigDecimal> getBalanceAsync(UUID uuid, String world) {
      return CompletableFuture.supplyAsync(() -> BigDecimal.valueOf(getBalance(uuid.toString(), world)));
  }

  default CompletableFuture<BigDecimal> getBalanceAsync(OfflinePlayer offlinePlayer, String world) {
      return getBalanceAsync(offlinePlayer.getUniqueId(), world);
  }

  default  CompletableFuture<BigDecimal> getBalanceAsync(UUID uuid, String world, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
  }

  default CompletableFuture<BigDecimal> getBalanceAsync(OfflinePlayer offlinePlayer, String world, String currency) {
      return getBalanceAsync(offlinePlayer.getUniqueId(), world, currency);
  }

  @Deprecated
  default double getBalance(String accountId) {
      try {
          return getBalanceAsync(UUID.fromString(accountId)).get(50, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          return 0.0;
      }
  }

  @Deprecated
  default double getBalance(OfflinePlayer player) {
      try {
          return getBalanceAsync(player.getUniqueId()).get(50, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          return 0.0;
      }
  }
  
  @Deprecated
  default double getBalance(String accountId, String worldName) {
      try {
          return getBalanceAsync(UUID.fromString(accountId), worldName).get(50, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          return 0.0;
      }
  }

  @Deprecated
  default double getBalance(OfflinePlayer player, String worldName) {
      try {
          return getBalanceAsync(player.getUniqueId(), worldName).get(50, TimeUnit.MILLISECONDS).doubleValue();
      } catch (Exception e) {
          return 0.0;
      }
  }

  //----------------------------------------------has() Methods----------------------------------------------

  default CompletableFuture<Boolean> has(UUID uuid, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> has(uuid.toString(), amount.doubleValue()));
  }

  default CompletableFuture<Boolean> has(OfflinePlayer offlinePlayer, BigDecimal amount) {
      return has(offlinePlayer.getUniqueId(), amount);
  }

  default CompletableFuture<Boolean> has(UUID uuid, String worldName, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> has(uuid.toString(), worldName, amount.doubleValue()));
  }

  default CompletableFuture<Boolean> has(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return has(offlinePlayer.getUniqueId(), worldName, amount);
  }

  default CompletableFuture<Boolean> has(UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
  }

  default CompletableFuture<Boolean> has(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return has(offlinePlayer.getUniqueId(), worldName, amount, currency);
  }

  @Deprecated
  default boolean has(String accountId, double amount) {
      try {
          return has(UUID.fromString(accountId), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean has(OfflinePlayer player, double amount) {
      try {
          return has(player.getUniqueId(), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }
  
  @Deprecated
  default boolean has(String accountId, String worldName, double amount) {
      try {
          return has(UUID.fromString(accountId), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean has(OfflinePlayer player, String worldName, double amount) {
      try {
          return has(player.getUniqueId(), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  //--------------------------------- withdrawAccount() and withdrawPlayer() Methods --------------------------

  default CompletableFuture<EconomyResponse> withdrawAccount(UUID uuid, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> withdrawPlayer(uuid.toString(), amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> withdrawAccount(OfflinePlayer offlinePlayer, BigDecimal amount) {
      return withdrawAccount(offlinePlayer.getUniqueId(), amount);
  }

  default CompletableFuture<EconomyResponse> withdrawAccount(UUID uuid, String worldName, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> withdrawPlayer(uuid.toString(), worldName, amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> withdrawAccount(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return withdrawAccount(offlinePlayer.getUniqueId(), worldName, amount);
  }

  default CompletableFuture<EconomyResponse> withdrawAccount(UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
  }

  default CompletableFuture<EconomyResponse> withdrawAccount(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return withdrawAccount(offlinePlayer.getUniqueId(), worldName, amount, currency);
  }

  @Deprecated
  default EconomyResponse withdrawPlayer(String accountId, double amount) {
      try {
          return withdrawAccount(UUID.fromString(accountId), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
      try {
          return withdrawAccount(player.getUniqueId(), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }
  
  @Deprecated
  default EconomyResponse withdrawPlayer(String accountId, String worldName, double amount) {
      try {
          return withdrawAccount(UUID.fromString(accountId), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
      try {
          return withdrawAccount(player.getUniqueId(), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  //--------------------------------------depositPlayer() & depositAccount() Methods--------------------------------------

  default CompletableFuture<EconomyResponse> depositAccount(UUID uuid, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> depositPlayer(uuid.toString(), amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> depositAccount(OfflinePlayer offlinePlayer, BigDecimal amount) {
      return depositAccount(offlinePlayer.getUniqueId(), amount);
  }

  default CompletableFuture<EconomyResponse> depositAccount(UUID uuid, String worldName, BigDecimal amout) {
      return CompletableFuture.supplyAsync(() -> depositPlayer(uuid.toString(), worldName, amout.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> depositAccount(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return depositAccount(offlinePlayer.getUniqueId(), worldName, amount);
  }

  default CompletableFuture<EconomyResponse> depositAccount(UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
  }

  default CompletableFuture<EconomyResponse> depositAccount(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return depositAccount(offlinePlayer.getUniqueId(), worldName, amount, currency);
  }

  @Deprecated
  default EconomyResponse depositPlayer(String accountId, double amount) {
      try {
          return depositAccount(UUID.fromString(accountId), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
      try {
          return depositAccount(player.getUniqueId(), BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }
  
  @Deprecated
  default EconomyResponse depositPlayer(String accountId, String worldName, double amount) {
      try {
          return depositAccount(UUID.fromString(accountId), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
      try {
          return depositAccount(player.getUniqueId(), worldName, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  //------------------------------------------------- Bank Methods (Eww) --------------------------------------------------------

  default CompletableFuture<EconomyResponse> createBankAsync(String name, UUID uuid) {
      return CompletableFuture.supplyAsync(() -> createBank(name, uuid.toString()));
  }

  default CompletableFuture<EconomyResponse> createBankAsync(String name, OfflinePlayer offlinePlayer) {
      return createBankAsync(name, offlinePlayer.getUniqueId());
  }

  default CompletableFuture<EconomyResponse> deleteBankAsync(String name) {
      return CompletableFuture.supplyAsync(() -> deleteBank(name));
  }

  default CompletableFuture<EconomyResponse> bankBalanceAsync(String name) {
      return CompletableFuture.supplyAsync(() -> bankBalance(name));
  }

  default CompletableFuture<EconomyResponse> bankHasAsync(String name, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> bankHas(name, amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> bankWithdrawAsync(String name, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> bankWithdraw(name, amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> bankDepositAsync(String name, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> bankDeposit(name, amount.doubleValue()));
  }

  default CompletableFuture<EconomyResponse> isBankOwnerAsync(String name, UUID uuid) {
      return CompletableFuture.supplyAsync(() -> isBankOwner(name, uuid.toString()));
  }

  default CompletableFuture<EconomyResponse> isBankOwnerAsync(String name, OfflinePlayer offlinePlayer) {
      return isBankOwnerAsync(name, offlinePlayer.getUniqueId());
  }

  default CompletableFuture<EconomyResponse> isBankMemberAsync(String name, UUID uuid) {
      return CompletableFuture.supplyAsync(() -> isBankMember(name, uuid.toString()));
  }

  default CompletableFuture<EconomyResponse> isBankMemberAsync(String name, OfflinePlayer offlinePlayer) {
      return isBankMemberAsync(name, offlinePlayer.getUniqueId());
  }

  default CompletableFuture<List<String>> getBanksAsync() {
      return CompletableFuture.supplyAsync(this::getBanks);
  }

  @Deprecated
  default EconomyResponse createBank(String name, String playerUUID) {
      try {
          return createBankAsync(name, UUID.fromString(playerUUID)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse createBank(String name, OfflinePlayer player) {
      try {
          return createBankAsync(name, player.getUniqueId()).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse deleteBank(String name) {
      try {
          return deleteBankAsync(name).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse bankBalance(String name) {
      try {
          return bankBalanceAsync(name).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse bankHas(String name, double amount) {
      try {
          return bankHasAsync(name, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse bankWithdraw(String name, double amount) {
      try {
          return bankWithdrawAsync(name, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse bankDeposit(String name, double amount) {
      try {
          return bankDepositAsync(name, BigDecimal.valueOf(amount)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }
  
  @Deprecated
  default EconomyResponse isBankOwner(String name, String playerUUID) {
      try {
          return isBankOwnerAsync(name, UUID.fromString(playerUUID)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse isBankOwner(String name, OfflinePlayer player) {
      try {
          return isBankOwnerAsync(name, player.getUniqueId()).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }
  
  @Deprecated
  default EconomyResponse isBankMember(String name, String playerUUID) {
      try {
          return isBankMemberAsync(name, UUID.fromString(playerUUID)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default EconomyResponse isBankMember(String name, OfflinePlayer player) {
      try {
          return isBankMemberAsync(name, player.getUniqueId()).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
  }

  @Deprecated
  default List<String> getBanks() {
      try {
          return getBanksAsync().get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return new ArrayList<>();
      }
  }

  //------------------------------------------ createPlayerAccount() Methods --------------------------------------------

  default CompletableFuture<Boolean> createPlayerAccountAsync(UUID uuid) {
      return CompletableFuture.supplyAsync(() -> createPlayerAccount(uuid.toString()));
  }

  default CompletableFuture<Boolean> createPlayerAccountAsync(OfflinePlayer offlinePlayer) {
      return createPlayerAccountAsync(offlinePlayer.getUniqueId());
  }

  default CompletableFuture<Boolean> createPlayerAccountAsync(UUID uuid, String worldName) {
        return CompletableFuture.supplyAsync(() -> createPlayerAccount(uuid.toString(), worldName));
  }

  default CompletableFuture<Boolean> createPlayerAccountAsync(OfflinePlayer offlinePlayer, String worldName) {
      return createPlayerAccountAsync(offlinePlayer.getUniqueId(), worldName);
  }

  @Deprecated
  default boolean createPlayerAccount(String accountId) {
      try {
          return createPlayerAccountAsync(UUID.fromString(accountId)).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean createPlayerAccount(OfflinePlayer player) {
      try {
          return createPlayerAccountAsync(player.getUniqueId()).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }
  
  @Deprecated
  default boolean createPlayerAccount(String accountId, String worldName) {
      try {
          return createPlayerAccountAsync(UUID.fromString(accountId), worldName).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  @Deprecated
  default boolean createPlayerAccount(OfflinePlayer player, String worldName) {
      try {
          return createPlayerAccountAsync(player.getUniqueId(), worldName).get(50, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
  }

  // --------------------------------------------------------------------------------------------------------
  // --------------------------------VintageVault Specific Methods-------------------------------------------
  // --------------------------------Renamed VaultUnlocked Methods-------------------------------------------
  // --------------------------------------------------------------------------------------------------------


    // Return how many Decimal Points the plugin supports
    default int numberOfDecimals() {
        return fractionalDigits();
    }

    default int numberOfDecimals(String currency) {
        return numberOfDecimals();
    }

    default CompletableFuture<Boolean> createCurrency(String currency, String singularName, String pluralName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<Boolean> createCurrency(String currency, String singularName, String pluralName, String worldName) {
        return createCurrency(currency, singularName, pluralName);
    }

    // Returns Whether the currency exists and or supported, with an option to specify a world
    default boolean hasCurrency(String currency, String worldName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean getDefaultCurrency() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean getDefaultCurrency(String worldName) {
        return getDefaultCurrency();
    }

    // Added Method in VintageVault 1.8.8
    default String getDefaultCurrencyName() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    // Added Method in VintageVault 1.8.8
    default String getDefaultCurrencyName(String worldName) {
        return getDefaultCurrencyName();
    }

    default String getDefaultCurrencySingular() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getDefaultCurrencySingular(String world) {
        return getDefaultCurrencySingular();
    }

    // Added Method in VintageVault 1.8.8
    default String getDefaultCurrencyPlural() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    // Added Method in VintageVault 1.8.8
    default String getDefaultCurrencyPlural(String worldName) {
        return getDefaultCurrencyPlural();
    }

    default String getCurrencySingular(String currencyName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getCurrencySingular(String currencyName, String world) {
        return getCurrencySingular(currencyName);
    }

    default String getCurrencyPlural(String currencyName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getCurrencyPlural(String currencyName, String world) {
        return getCurrencyPlural(currencyName);
    }

    default String[] getCurrencies() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String[] getCurrencies(String worldName) {
        return getCurrencies();
    }

    // Added Method in VintageVault 1.8.8
    default BigDecimal getCurrencyMax(String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    // Added Method in VintageVault 1.8.8
    default BigDecimal getCurrencyMax(String currency, String world) {
        return getCurrencyMax(currency);
    }

    // Added Method in VintageVault 1.8.8
    default BigDecimal getCurrencyMin(String currency) {
        return BigDecimal.ZERO;
    }

    // Added Method in VintageVault 1.8.8
    default BigDecimal getCurrencyMin(String currency, String world) {
        return getCurrencyMin(currency);
    }

    // Added Method in VintageVault 1.8.8
    default boolean canConvertCurrency(String originalCurrency, String newCurrency) {
        return false;
    }

    // Added Method in VintageVault 1.8.8
    default boolean canConvertCurrency(String originalCurrency, String newCurrency, String world) {
        return canConvertCurrency(originalCurrency, newCurrency);
    }

    // Added Method in VintageVault 1.8.8
    default BigDecimal convertCurrency(String initialCurrency, BigDecimal amount, String newCurrency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default BigDecimal convertCurrency(String initialCurrency, BigDecimal amount, String newCurrency, String world) {
        return convertCurrency(initialCurrency, amount, newCurrency);
    }

    default BigDecimal getAccountBalance(UUID accountId) {
        return BigDecimal.valueOf(getBalance(accountId.toString()));
    }

    default BigDecimal getAccountBalance(UUID accountId, String currency) {
        return getAccountBalance(accountId); // Plugin Doesn't Have Multi-Currency Support
    }

    default BigDecimal getAccountBalance(UUID accountId, String currency, String worldName) {
        return getAccountBalance(accountId, currency);
    }

    default boolean accountHas(UUID accountId, BigDecimal amount) {
        return has(accountId.toString(), amount.doubleValue());
    }

    default boolean accountHasInWorld(UUID accountId, BigDecimal amount, String worldName) {
        return accountHas(accountId, amount);
    }

    default boolean accountHas(UUID accountId, BigDecimal amount, String currency) {
        return accountHas(accountId, amount); // Plugin Doesn't Have Multi-Currency Support
    }

    default boolean accountHasInWorld(UUID accountId, BigDecimal amount, String currency, String worldName) {
        return accountHasInWorld(accountId, amount, worldName); // Plugin Doesn't Have Multi-Currency Support
    }

    default boolean accountSupportsCurrency(UUID accountId, String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean accountSupportsCurrency(UUID accountId, String currency, String worldName) {
        return accountSupportsCurrency(accountId, currency);
    }

    default boolean renameAccount(UUID accountID, String name, String worldName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean deleteAccount(UUID accountId) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean deleteAccount(UUID accountId, String worldName) {
        return deleteAccount(accountId);
    }



  // --------------------------------------------------------------------------------------------------------
  // ---------------------------VaultUnlocked / Vault2 Method Implementation---------------------------------
  // ---------------------------------Removed ALL pluginName Parameters--------------------------------------
  // --------------------------------------------------------------------------------------------------------
    /**
     * Returns true if the economy plugin supports shared accounts.
     *
     * @return true if the economy plugin supports shared accounts.
     */
    default boolean hasSharedAccountSupport(){
        return false;
    }

    /**
     * Returns true if the economy plugin supports multiple currencies.
     *
     * @return true if the economy plugin supports multiple currencies.
     */
    default boolean hasMultiCurrencySupport(){
        return false;
    }

    /*
     * Currency-related methods follow.
     */

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     *
     * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    @NotNull
    default String format(@NotNull final BigDecimal amount){
        return format(amount.doubleValue()); // Backwards Compatibility Support
    }

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     * @param currency the currency to use for the format.
     *
     * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    @NotNull
    default String format(@NotNull final BigDecimal amount, @NotNull final String currency){
        return format(amount); // Plugin Doesn't Have Multi-Currency Support
    }

    /**
     * Returns true if a currency with the specified name exists.
     *
     * @param currency the currency to use.
     *
     * @return true if a currency with the specified name exists.
     */
    default boolean hasCurrency(@NotNull final String currency){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /*
     * Account-related methods follow.
     */

    /**
     * Attempts to create an account for the given UUID.
     *
     * @deprecated This method is deprecated as of version 2.8, and has been replaced by {@link #createAccount(UUID, String, String, boolean)}.
     * This allows economy plugins to know exactly if the account is a player or not. This may be removed in a future release.
     *
     * @param accountID UUID associated with the account.
     * @param name UUID associated with the account.
     * @return true if the account creation was successful.
     */
    @Deprecated
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name){
        return createAccount(accountID, name, true);
    }

    /**
     * Creates a new account with the provided information.
     *
     * @param accountID The UUID of the account to be created.
     * @param name The name associated with the account.
     * @param player A flag indicating if the account is a player account.
     *
     * @return true if the account was successfully created, false otherwise.
     */
    @Deprecated
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, final boolean player){
        return createPlayerAccount(accountID.toString()); // Backwards Compatibility
    }

    /**
     * Attempts to create an account for the given UUID on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then
     * false will always be returned.
     *
     * @deprecated This method is deprecated as of version 2.8, and has been replaced by {@link #createAccount(UUID, String, String, boolean)}.
     * This allows economy plugins to know exactly if the account is a player or not. This may be removed in a future release.
     *
     * @param accountID      UUID associated with the account.
     * @param name      UUID associated with the account.
     * @param worldName String name of the world.
     * @return if the account creation was successful
     */
    @Deprecated
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final String worldName){
        return createAccount(accountID, name, worldName, true);
    }

    /**
     * Creates a new account with the given parameters.
     *
     * @param accountID The UUID of the account to be created.
     * @param name The name of the account holder.
     * @param worldName The world name associated with the account.
     * @param player A boolean indicating if the account belongs to a player.
     *
     * @return True if the account was successfully created, false otherwise.
     */
    @Deprecated
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final String worldName, final boolean player){
        return createAccount(accountID, name, player);
    }

    /**
     * Returns a map that represents all the UUIDs which have accounts in the
     * plugin, as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     *
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    @Deprecated
    @NotNull
    default Map<UUID, String> getUUIDNameMap(){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Gets the last known name of an account owned by the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     *
     * @param accountID UUID associated with the account.
     * @return An optional containing the last known name if the account exists, otherwise an empty
     * optional.
     */
    @Deprecated
    default String getAccountName(@NotNull final UUID accountID){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Checks if this UUID has an account yet.
     *
     * @param accountID UUID to check for an existing account.
     * @return true if the UUID has an account.
     */
    @Deprecated
    default boolean hasAccount(@NotNull final UUID accountID){
        return hasAccount(accountID.toString()); // Backwards Compatibility
    }

    /**
     * Checks if this UUID has an account yet on the given world.
     *
     * @param accountID      UUID to check for an existing account.
     * @param worldName world-specific account.
     * @return if the UUID has an account.
     */
    @Deprecated
    default boolean hasAccount(@NotNull final UUID accountID, @NotNull final String worldName){
        return hasAccount(accountID);
    }

    /**
     * A method which changes the name associated with the given UUID in the
     * value returned from {@link #getUUIDNameMap()}.
     *
     * @param accountID UUID whose account is having a name change.
     * @param name String name that will be associated with the UUID in the map.
     * @return true if the name change is successful.
     */
    @Deprecated
    default boolean renameAccount(@NotNull final UUID accountID, @NotNull final String name){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /*
     * Account balance related methods follow.
     */

    /**
     *
     * Sets the amount of monies for a player.
     *
     * @param accountID the unique identifier of the player's account
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    @Deprecated
    default EconomyResponse setAccount(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount) {

        final BigDecimal balance = getAccountBalance(accountID);
        final int compare = balance.compareTo(amount);
        if(compare > 0) {
            return accountWithdraw(plugin, accountID, balance.subtract(amount));
        }

        if(compare < 0) {
            return accountDeposit(plugin, accountID, amount.subtract(balance));
        }

        return new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     *
     * Sets the amount of monies for a player.
     *
     * @param accountID the unique identifier of the player's account
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    default CompletableFuture<EconomyResponse> setAccountAsync(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount) {
        return getBalanceAsync(accountID).thenCompose(balance -> {
            final int compare = balance.compareTo(amount);
            if(compare > 0) {
                return withdrawAccount(accountID, balance.subtract(amount));
            }

            if(compare < 0) {
                return depositAccount(accountID, amount.subtract(balance));
            }

            return CompletableFuture.completedFuture(new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, ""));
        });
    }

    /**
     *
     * Sets the amount of monies for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    @Deprecated
    default EconomyResponse setAccount(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount) {

        final BigDecimal balance = getAccountBalance(accountID, worldName);
        final int compare = balance.compareTo(amount);
        if(compare > 0) {
            return accountWithdraw(plugin, accountID, worldName, balance.subtract(amount));
        }

        if(compare < 0) {
            return accountDeposit(plugin, accountID, worldName, amount.subtract(balance));
        }

        return new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     *
     * Sets the amount of monies for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    default CompletableFuture<EconomyResponse> setAccountAsync(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount) {
        return getBalanceAsync(accountID, worldName).thenCompose(balance -> {
            final int compare = balance.compareTo(amount);
            if(compare > 0) {
                return withdrawAccount(accountID, worldName, balance.subtract(amount));
            }

            if(compare < 0) {
                return depositAccount(accountID, worldName, amount.subtract(balance));
            }

            return CompletableFuture.completedFuture(new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, ""));
        });
    }

    /**
     *
     * Sets the amount of specified currency for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param currency the name of the currency being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    @Deprecated
    default EconomyResponse setAccount(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount) {

        final BigDecimal balance = getAccountBalance(accountID, worldName, currency);
        final int compare = balance.compareTo(amount);
        if(compare > 0) {
            return accountWithdraw(plugin, accountID, worldName, currency, balance.subtract(amount));
        }

        if(compare < 0) {
            return accountDeposit(plugin, accountID, worldName, currency, amount.subtract(balance));
        }

        return new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     *
     * Sets the amount of specified currency for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param currency the name of the currency being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
    default CompletableFuture<EconomyResponse> setAccountAsync(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount) {
        return getBalanceAsync(accountID, worldName, currency).thenCompose(balance -> {
            final int compare = balance.compareTo(amount);
            if(compare > 0) {
                return withdrawAccount(accountID, worldName, balance.subtract(amount), currency);
            }

            if(compare < 0) {
                return depositAccount(accountID, worldName, amount.subtract(balance), currency);
            }

            return CompletableFuture.completedFuture(new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, ""));
        });
    }

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param accountID   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount){
        return withdrawPlayer(accountID.toString(), amount.doubleValue()); // Backwards Compatibility
    }

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param accountID   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount, @NotNull final String currency){
        return accountWithdraw(plugin, accountID, amount); // Plugin Doesn't Have Multi-Currency Support
    }

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param accountID      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount){
        return accountWithdraw(plugin, accountID, amount);
    }

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param accountID      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount){
        return accountWithdraw(plugin, accountID, worldName, amount); // Plugin Doesn't Have Multi-Currency Support
    }

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param accountID   the UUID associated with the account to deposit to.
     * @param amount Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount){
        return depositPlayer(accountID.toString(), amount.doubleValue()); // Backwards Compatibility
    }

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param accountID   the UUID associated with the account to deposit to.
     * @param amount Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount, @NotNull final String currency){
        return accountDeposit(plugin, accountID, amount); // Plugin Doesn't Have Multi-Currency Support
    }

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param accountID  the {@link UUID} associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount){
        return accountDeposit(plugin, accountID, amount);
    }

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param accountID      the {@link UUID} associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @Deprecated
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount){
        return accountDeposit(plugin, accountID, currency, amount);
    }

    /*
     * Shared Account Methods
     */

    /**
     * Creates a shared account with the specified parameters.
     *
     * @param accountID  the {@link UUID} of the account
     * @param name       the name of the account
     * @param owner      the {@link UUID} of the account owner
     * @return true if the shared account is successfully created, false otherwise
     */
    @Deprecated
    default boolean createSharedAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final UUID owner){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Creates a shared account with the specified parameters.
     *
     * @param accountID  the {@link UUID} of the account
     * @param name       the name of the account
     * @param owner      the {@link UUID} of the account owner
     * @return true if the shared account is successfully created, false otherwise
     */
    default CompletableFuture<Boolean> createSharedAccountAsync(@NotNull final UUID accountID, @NotNull final String name, @NotNull final UUID owner){
        return CompletableFuture.supplyAsync(() -> createSharedAccount(accountID, name, owner));
    }

    /**
     * Retrieves a list of account IDs owned by the specified account ID.
     *
     * @param accountID the unique identifier of the account
     * @return a list of account names owned by the specified account ID
     *
     * @since 2.14
     */
    @Deprecated
    default List<String> accountsOwnedBy(@NotNull final UUID accountID) {
        return accountsAccessTo(accountID, AccountPermission.OWNER);
    }

    /**
     * Retrieves a list of account IDs owned by the specified account ID.
     *
     * @param accountID the unique identifier of the account
     * @return a list of account names owned by the specified account ID
     *
     * @since 2.14
     */
    default CompletableFuture<List<String>> accountsOwnedByAsync(@NotNull final UUID accountID) {
        return accountsAccessToAsync(accountID, AccountPermission.OWNER);
    }

    /**
     * Retrieves a list of account IDs that the specified account is a member of.
     *
     * @param accountID the UUID of the account to check membership for
     * @return a List of String values representing the accounts that the account is a member of
     *
     * @since 2.14
     */
    @Deprecated
    default List<String> accountsMemberOf(@NotNull final UUID accountID) {
        return accountsAccessTo(accountID, AccountPermission.BALANCE, AccountPermission.DEPOSIT, AccountPermission.WITHDRAW);
    }

    /**
     * Retrieves a list of account IDs that the specified account is a member of.
     *
     * @param accountID the UUID of the account to check membership for
     * @return a List of String values representing the accounts that the account is a member of
     *
     * @since 2.14
     */
    default CompletableFuture<List<String>> accountsMemberOfAsync(@NotNull final UUID accountID) {
        return accountsAccessToAsync(accountID, AccountPermission.BALANCE, AccountPermission.DEPOSIT, AccountPermission.WITHDRAW);
    }

    /**
     * Retrieves a list of account IDs that the specified account has the specified permissions for.
     *
     * @param accountID the UUID of the account to check access for
     * @param permissions variable number of permissions to check for
     * @return a list of accounts that the account has the specified permissions to
     *
     * @since 2.14
     */
    @Deprecated
    default List<String> accountsAccessTo(@NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {
        return new ArrayList<>();
    }

    /**
     * Retrieves a list of account IDs that the specified account has the specified permissions for.
     *
     * @param accountID the UUID of the account to check access for
     * @param permissions variable number of permissions to check for
     * @return a list of accounts that the account has the specified permissions to
     *
     * @since 2.14
     */
    default CompletableFuture<List<String>> accountsAccessToAsync(@NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {
        return CompletableFuture.supplyAsync(() -> accountsAccessTo(accountID, permissions));
    }

    /**
     * Determines whether the specified owner ID is the owner of the account associated with the given account ID and plugin name.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} to check for ownership of the account
     * @return true if the owner ID is the owner of the account, false otherwise
     */
    @Deprecated
    default boolean isAccountOwner(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Determines whether the specified owner ID is the owner of the account associated with the given account ID and plugin name.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} to check for ownership of the account
     * @return true if the owner ID is the owner of the account, false otherwise
     */
    default CompletableFuture<Boolean> isAccountOwnerAsync(@NotNull final UUID accountID, @NotNull final UUID uuid){
        return CompletableFuture.supplyAsync(() -> isAccountOwner(accountID, uuid));
    }

    /**
     * Sets the owner of a specified plugin to the given accountID.
     *
     * @param accountID  The {@link UUID} of the account
     * @param uuid       The {@link UUID} of the account to set as the owner.
     * @return true if the owner is successfully set, false otherwise.
     */
    @Deprecated
    default boolean setAccountOwner(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Sets the owner of a specified plugin to the given accountID.
     *
     * @param accountID  The {@link UUID} of the account
     * @param uuid       The {@link UUID} of the account to set as the owner.
     * @return true if the owner is successfully set, false otherwise.
     */
    default CompletableFuture<Boolean> setAccountOwnerAsync(@NotNull final UUID accountID, @NotNull final UUID uuid){
        return CompletableFuture.supplyAsync(() -> setAccountOwner(accountID, uuid));
    }

    /**
     * Determines whether a specific member is an account member of a given plugin.
     *
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} to check for membership.
     * @return true if the member is an account member, false otherwise.
     */
    @Deprecated
    default boolean isAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Determines whether a specific member is an account member of a given plugin.
     *
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} to check for membership.
     * @return true if the member is an account member, false otherwise.
     */
    default CompletableFuture<Boolean> isAccountMemberAsync(@NotNull final UUID accountID, @NotNull final UUID uuid){
        return CompletableFuture.supplyAsync(() -> isAccountMember(accountID, uuid));
    }

    /**
     * Adds a member to an account.
     *
     * @param accountID  The {@link UUID} of the account.
     * @param uuid       The {@link UUID} of the member to be added.
     * @return true if the member was successfully added, false otherwise.
     */
    @Deprecated
    default boolean addAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Adds a member to an account.
     *
     * @param accountID  The {@link UUID} of the account.
     * @param uuid       The {@link UUID} of the member to be added.
     * @return true if the member was successfully added, false otherwise.
     */
    default CompletableFuture<Boolean> addAccountMemberAsync(@NotNull final UUID accountID, @NotNull final UUID uuid){
        return CompletableFuture.supplyAsync(() -> addAccountMember(accountID, uuid));
    }

    /**
     * Adds a member to an account with the specified initial permissions.
     *
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} of the member to be added.
     * @param initialPermissions The initial permissions to be assigned to the member. The values for
     *                           these should be assumed to be "true."
     * @return true if the member was added successfully, false otherwise.
     */
    @Deprecated
    default boolean addAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission... initialPermissions){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Adds a member to an account with the specified initial permissions.
     *
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} of the member to be added.
     * @param initialPermissions The initial permissions to be assigned to the member. The values for
     *                           these should be assumed to be "true."
     * @return true if the member was added successfully, false otherwise.
     */
    default CompletableFuture<Boolean> addAccountMemberAsync(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission... initialPermissions){
        return CompletableFuture.supplyAsync(() -> addAccountMember(accountID, uuid, initialPermissions));
    }

    /**
     * Removes a member from an account.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} of the member to be removed
     * @return true if the member was successfully removed, false otherwise
     */
    @Deprecated
    default boolean removeAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Removes a member from an account.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} of the member to be removed
     * @return true if the member was successfully removed, false otherwise
     */
    default CompletableFuture<Boolean> removeAccountMemberAsync(@NotNull final UUID accountID, @NotNull final UUID uuid){
        return CompletableFuture.supplyAsync(() -> removeAccountMember(accountID, uuid));
    }

    /**
     * Checks if the specified account has the given permission for the given plugin.
     *
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to check for the permission
     * @param permission   the permission to check for
     * @return true if the account has the specified permission, false otherwise
     */
    @Deprecated
    default boolean hasAccountPermission(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Checks if the specified account has the given permission for the given plugin.
     *
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to check for the permission
     * @param permission   the permission to check for
     * @return true if the account has the specified permission, false otherwise
     */
    default CompletableFuture<Boolean> hasAccountPermissionAsync(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission){
        return CompletableFuture.supplyAsync(() -> hasAccountPermission(accountID, uuid, permission));
    }

    /**
     * Updates the account permission for a specific plugin and user.
     *
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to update the permission for
     * @param permission   the new account permissions to set
     * @param value        the new permission value to set for this value
     * @return true if the account permission was successfully updated, false otherwise
     */
    @Deprecated
    default boolean updateAccountPermission(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission, final boolean value) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Updates the account permission for a specific plugin and user.
     *
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to update the permission for
     * @param permission   the new account permissions to set
     * @param value        the new permission value to set for this value
     * @return true if the account permission was successfully updated, false otherwise
     */
    default CompletableFuture<Boolean> updateAccountPermissionAsync(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission, final boolean value) {
        return CompletableFuture.supplyAsync(() -> updateAccountPermission(plugin, accountID, uuid, permission, value));
    }

}