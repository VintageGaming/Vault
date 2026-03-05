package net.milkbowl.vault.economy;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import net.milkbowl.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface Economy {

    // Hopefully Prevents Same Tick Transactions from the same person/account
    Set<UUID> activeTransactions = ConcurrentHashMap.newKeySet();
    Set<String> activeBankTransactions = ConcurrentHashMap.newKeySet();

    EconomyResponse TRANSACTION_IN_PROGRESS = new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "A transaction is already in progress!");
    EconomyResponse INVALID_PLAYER = new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Invalid Player!");

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

    default CompletableFuture<Boolean> hasAccountAsync(UUID uuid, String worldName) {
      return CompletableFuture.supplyAsync(() -> {
          try {
              OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
              if (offlinePlayer.hasPlayedBefore())
                  return hasAccount(offlinePlayer, worldName);
          } catch (Exception e) {
              // Default to UUID
          }
          return hasAccount(uuid.toString(), worldName);
      }, Vault.vaultEconomyService)
              .exceptionally(ex -> false);
    }

    default CompletableFuture<Boolean> hasAccountAsync(OfflinePlayer offlinePlayer, String worldName) {
      return hasAccountAsync(offlinePlayer.getUniqueId(), worldName);
    }

    private CompletableFuture<Boolean> hasAccountAsync(String accountId, String worldName) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(accountId);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(accountId).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> hasAccountAsync( uuid, worldName))
                .exceptionally(ex -> false);
    }

    @Deprecated
    default boolean hasAccount(String accountId) {
      try {
          return hasAccountAsync(accountId, "").get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
    }

    @Deprecated
    default boolean hasAccount(OfflinePlayer player) {
      try {
          return hasAccountAsync(player, "").get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
    }

    @Deprecated
    default boolean hasAccount(String accountId, String worldName) {
      try {
          return hasAccountAsync(accountId, worldName).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          return false;
      }
    }

    @Deprecated
    default boolean hasAccount(OfflinePlayer player, String worldName) {
      try {
          return hasAccountAsync(player, worldName).get(500, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          return false;
      }
    }

    //-----------------------------------------Get Balance Methods----------------------------------------

    default CompletableFuture<BigDecimal> getBalanceAsync(UUID uuid, String world) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    if (offlinePlayer.hasPlayedBefore()) {
                        return BigDecimal.valueOf(getBalance(offlinePlayer, world));
                    }

                    return BigDecimal.valueOf(getBalance(uuid.toString(), world));

                }, Vault.vaultEconomyService).exceptionally(ex -> BigDecimal.ZERO);
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

    private CompletableFuture<BigDecimal> getBalanceAsync(String accountId, String worldName) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(accountId);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(accountId).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> getBalanceAsync( uuid, worldName))
                .exceptionally(ex -> BigDecimal.ZERO);
    }

    @Deprecated
    default double getBalance(String accountId) {
      try {
          return getBalanceAsync(accountId, "").get(500, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] getBalance() Sync Timeout! Economy Provider is taking too long.");
          return 0.0;
      }
    }

    @Deprecated
    default double getBalance(OfflinePlayer player) {
      try {
          return getBalanceAsync(player, "").get(500, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] getBalance() Sync Timeout! Economy Provider is taking too long.");
          return 0.0;
      }
    }

    @Deprecated
    default double getBalance(String accountId, String worldName) {
      try {
          return getBalanceAsync(accountId, worldName).get(500, TimeUnit.MILLISECONDS).doubleValue();
      }
      catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] getBalance() Sync Timeout! Economy Provider is taking too long.");
          return 0.0;
      }
    }

    @Deprecated
    default double getBalance(OfflinePlayer player, String worldName) {
      try {
          return getBalanceAsync(player, worldName).get(500, TimeUnit.MILLISECONDS).doubleValue();
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] getBalance() Sync Timeout! Economy Provider is taking too long.");
          return 0.0;
      }
    }

    //----------------------------------------------has() Methods----------------------------------------------

    default CompletableFuture<Boolean> hasAsync(UUID uuid, String worldName, BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    double hasAmount = amount.doubleValue();

                    if (offlinePlayer.hasPlayedBefore()) {
                        return has(offlinePlayer, worldName, hasAmount);
                    }

                    return has(uuid.toString(), worldName, hasAmount);

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> false);
    }

    default CompletableFuture<Boolean> hasAsync(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return hasAsync(offlinePlayer.getUniqueId(), worldName, amount);
    }

    default CompletableFuture<Boolean> hasAsync(UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<Boolean> hasAsync(OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return hasAsync(offlinePlayer.getUniqueId(), worldName, amount, currency);
    }

    private CompletableFuture<Boolean> hasAsync(String accountId, String worldName, BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(accountId);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(accountId).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> hasAsync(uuid, worldName, amount))
                .exceptionally(ex -> false);
    }

    @Deprecated
    default boolean has(String accountId, double amount) {
        try {
            return hasAsync(accountId, "", BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Bukkit.getLogger().info("[Vault-Economy] has() Sync Timeout! Economy Provider is taking too long.");
            return false;
        }
    }

    @Deprecated
    default boolean has(OfflinePlayer player, double amount) {
      try {
          return hasAsync(player, "", BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] has() Sync Timeout! Economy Provider is taking too long.");
          return false;
      }
    }

    @Deprecated
    default boolean has(String accountId, String worldName, double amount) {
      try {
          return hasAsync(accountId, worldName, BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] has() Sync Timeout! Economy Provider is taking too long.");
          return false;
      }
    }

    @Deprecated
    default boolean has(OfflinePlayer player, String worldName, double amount) {
      try {
          return hasAsync(player, worldName, BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] has() Sync Timeout! Economy Provider is taking too long.");
          return false;
      }
    }

    //--------------------------------- withdrawAccount() and withdrawPlayer() Methods --------------------------

    // Main Method
    default CompletableFuture<EconomyResponse> withdrawAccount(String plugin, UUID uuid, String worldName, BigDecimal amount) {

        if (!activeTransactions.add(uuid)) {
            return CompletableFuture.completedFuture(TRANSACTION_IN_PROGRESS);
        }

        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    double withdrawAmount = amount.doubleValue();

                    if (offlinePlayer.hasPlayedBefore()) {
                        activeTransactions.remove(uuid);
                        return withdrawPlayer(offlinePlayer, worldName, withdrawAmount);
                    }

                    activeTransactions.remove(uuid);
                    return withdrawPlayer(uuid.toString(), worldName, withdrawAmount);

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> {
                    activeTransactions.remove(uuid);
                    return INVALID_PLAYER;
                });
    }

    default CompletableFuture<EconomyResponse> withdrawAccount(String plugin, OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return withdrawAccount(plugin, offlinePlayer.getUniqueId(), worldName, amount);
    }

    default CompletableFuture<EconomyResponse> withdrawAccount(String plugin, UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<EconomyResponse> withdrawAccount(String plugin, OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return withdrawAccount(plugin, offlinePlayer.getUniqueId(), worldName, amount, currency);
    }

    private CompletableFuture<EconomyResponse> withdrawAccount(String accountId, String worldName, double amount) {

        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);

        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(accountId);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(accountId).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> withdrawAccount("Async-Final", uuid, worldName, withdrawAmount))
                .exceptionally(ex -> INVALID_PLAYER);
    }

    @Deprecated
    default EconomyResponse withdrawPlayer(String accountId, double amount) {
        try {
            return withdrawAccount(accountId, "", amount).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Bukkit.getLogger().warning(String.format(
                    "[Vault] Legacy Sync Timeout! Player %s may have been charged $%.2f without the calling plugin knowing.",
                    accountId, amount
            ));
            return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
        }
    }

    @Deprecated
    default EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
      try {
          return withdrawAccount("Non-Async Call", player, "", BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault] Legacy Sync Timeout! Player %s may have been charged $%.2f without the calling plugin knowing.",
                  player.getName(), amount
          ));
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse withdrawPlayer(String accountId, String worldName, double amount) {
        try {
            return withdrawAccount(accountId, worldName, amount).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Bukkit.getLogger().warning(String.format(
                    "[Vault] Legacy Sync Timeout! Player %s may have been charged $%.2f without the calling plugin knowing.",
                    accountId, amount
            ));
            return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
        }
    }

    @Deprecated
    default EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
      try {
          return withdrawAccount("Non-Async Call", player, worldName, BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      }
      catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault] Legacy Sync Timeout! Player %s may have been charged $%.2f without the calling plugin knowing.",
                  player.getName(), amount
          ));
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    //--------------------------------------depositPlayer() & depositAccount() Methods--------------------------------------

    // Main Method
    default CompletableFuture<EconomyResponse> depositAccount(String plugin, UUID uuid, String worldName, BigDecimal amount) {

        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    double depositAmount = amount.doubleValue();

                    if (offlinePlayer.hasPlayedBefore()) {
                        activeTransactions.remove(uuid);
                        return depositPlayer(offlinePlayer, worldName, depositAmount);
                    }

                    activeTransactions.remove(uuid);
                    return depositPlayer(uuid.toString(), worldName, depositAmount);

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> {
                    activeTransactions.remove(uuid);
                    return INVALID_PLAYER;
                });
    }

    default CompletableFuture<EconomyResponse> depositAccount(String plugin, OfflinePlayer offlinePlayer, String worldName, BigDecimal amount) {
      return depositAccount(plugin, offlinePlayer.getUniqueId(), worldName, amount);
    }

    default CompletableFuture<EconomyResponse> depositAccount(String plugin, UUID uuid, String worldName, BigDecimal amount, String currency) {
      throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<EconomyResponse> depositAccount(String plugin, OfflinePlayer offlinePlayer, String worldName, BigDecimal amount, String currency) {
      return depositAccount(plugin, offlinePlayer.getUniqueId(), worldName, amount, currency);
    }

    private CompletableFuture<EconomyResponse> depositAccount(String accountId, String worldName, double amount) {
        BigDecimal depositAmount = BigDecimal.valueOf(amount);

        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(accountId);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(accountId).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> depositAccount("Async-Final", uuid, worldName, depositAmount))
                .exceptionally(ex -> INVALID_PLAYER);
    }

    @Deprecated
    default EconomyResponse depositPlayer(String accountId, double amount) {
      try {
          return depositAccount(accountId, "", amount).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault-Economy] depositPlayer() Sync Timeout! Player %s may have been deposited $%.2f without the calling plugin knowing.",
                  accountId, amount
          ));
          return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
      try {
          return depositAccount("Non-Sync Call", player, "", BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault-Economy] depositPlayer() Sync Timeout! Player %s may have been deposited $%.2f without the calling plugin knowing.",
                  player.getName(), amount
          ));
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse depositPlayer(String accountId, String worldName, double amount) {
        try {
            return depositAccount(accountId, worldName, amount).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Bukkit.getLogger().warning(String.format(
                    "[Vault-Economy] depositPlayer() Sync Timeout! Player %s may have been deposited $%.2f without the calling plugin knowing.",
                    accountId, amount
            ));
            return new EconomyResponse(0.0, getBalance(accountId), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
        }
    }

    @Deprecated
    default EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
      try {
          return depositAccount("Non-Sync Call", player, worldName, BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault-Economy] depositPlayer() Sync Timeout! Player %s may have been deposited $%.2f without the calling plugin knowing.",
                  player.getName(), amount
          ));
          return new EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    //------------------------------------------------- Bank Methods (Eww) --------------------------------------------------------

    default CompletableFuture<EconomyResponse> createBankAsync(String name, UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    if (offlinePlayer.hasPlayedBefore()) {
                        return createBank(name, offlinePlayer);
                    }

                    return createBank(name, uuid.toString());

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> INVALID_PLAYER);
    }

    default CompletableFuture<EconomyResponse> createBankAsync(String name, OfflinePlayer offlinePlayer) {
      return createBankAsync(name, offlinePlayer.getUniqueId());
    }

    default CompletableFuture<EconomyResponse> deleteBankAsync(String name) {
      return CompletableFuture.supplyAsync(() -> deleteBank(name), Vault.vaultEconomyService);
    }

    default CompletableFuture<EconomyResponse> bankBalanceAsync(String name) {
      return CompletableFuture.supplyAsync(() -> bankBalance(name), Vault.vaultEconomyService);
    }

    default CompletableFuture<EconomyResponse> bankHasAsync(String name, BigDecimal amount) {
      return CompletableFuture.supplyAsync(() -> bankHas(name, amount.doubleValue()), Vault.vaultEconomyService);
    }

    default CompletableFuture<EconomyResponse> bankWithdrawAsync(String name, BigDecimal amount) {

        if (!activeBankTransactions.add(name)) {
            return CompletableFuture.completedFuture(TRANSACTION_IN_PROGRESS);
        }

      return CompletableFuture.supplyAsync(() -> {
          try {
              return bankWithdraw(name, amount.doubleValue());
          } finally {
              activeBankTransactions.remove(name);
          }
      }, Vault.vaultEconomyService);
    }

    default CompletableFuture<EconomyResponse> bankDepositAsync(String name, BigDecimal amount) {

        if (!activeBankTransactions.add(name)) {
            return CompletableFuture.completedFuture(TRANSACTION_IN_PROGRESS);
        }

        return CompletableFuture.supplyAsync(() -> {
          try {
              return bankDeposit(name, amount.doubleValue());
          }
          finally {
              activeBankTransactions.remove(name);
          }
      }, Vault.vaultEconomyService);
    }

    default CompletableFuture<EconomyResponse> isBankOwnerAsync(String name, UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    if (offlinePlayer.hasPlayedBefore()) {
                        return isBankOwner(name, offlinePlayer);
                    }

                    return isBankOwner(name, uuid.toString());

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> INVALID_PLAYER);
    }

    default CompletableFuture<EconomyResponse> isBankOwnerAsync(String name, OfflinePlayer offlinePlayer) {
      return isBankOwnerAsync(name, offlinePlayer.getUniqueId());
    }

    private CompletableFuture<EconomyResponse> isBankOwnerAsync(String name, String playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(playerUUID);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(playerUUID).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> isBankOwnerAsync( name, uuid))
                .exceptionally(ex -> INVALID_PLAYER);
    }

    default CompletableFuture<EconomyResponse> isBankMemberAsync(String name, UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    if (offlinePlayer.hasPlayedBefore()) {
                        return isBankMember(name, offlinePlayer);
                    }

                    return isBankMember(name, uuid.toString());

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> INVALID_PLAYER);
    }

    default CompletableFuture<EconomyResponse> isBankMemberAsync(String name, OfflinePlayer offlinePlayer) {
      return isBankMemberAsync(name, offlinePlayer.getUniqueId());
    }

    private CompletableFuture<EconomyResponse> isBankMemberAsync(String name, String playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return UUID.fromString(playerUUID);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getOfflinePlayer(playerUUID).getUniqueId();
                    }
                }, Vault.vaultEconomyService)
                .thenCompose(uuid -> isBankMemberAsync( name, uuid))
                .exceptionally(ex -> INVALID_PLAYER);
    }

    default CompletableFuture<List<String>> getBanksAsync() {
      return CompletableFuture.supplyAsync(this::getBanks, Vault.vaultEconomyService);
    }

    @Deprecated
    default EconomyResponse createBank(String name, String playerUUID) {
        try {
            UUID target;
            try {
                target = UUID.fromString(playerUUID);
            } catch (IllegalArgumentException e) {
                target = Bukkit.getOfflinePlayer(playerUUID).getUniqueId();
            }
            return createBankAsync(name, target).get(1000, TimeUnit.MILLISECONDS);

        } catch (TimeoutException e) {
            Bukkit.getLogger().warning(String.format(
                    "[Vault-Economy] createBank() Sync Timeout! Bank %s may have been create for %s without the calling plugin knowing.",
                    name, playerUUID
            ));
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Economy timed out (1000ms limit)");
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Error: " + e.getMessage());
        }
    }

    @Deprecated
    default EconomyResponse createBank(String name, OfflinePlayer player) {
      try {
          return createBankAsync(name, player).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault-Economy] createBank() Sync Timeout! Bank %s may have been create for %s without the calling plugin knowing.",
                  name, player.getName()
          ));
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse deleteBank(String name) {
      try {
          return deleteBankAsync(name).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault-Economy] deleteBank() Sync Timeout! Bank %s may have been deleted without the calling plugin knowing.",
                  name
          ));
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse bankBalance(String name) {
      try {
          return bankBalanceAsync(name).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] bankBalance() Sync Timeout! Economy Provider is taking too long.");
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse bankHas(String name, double amount) {
      try {
          return bankHasAsync(name, BigDecimal.valueOf(amount)).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] bankBalance() Sync Timeout! Economy Provider is taking too long.");
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse bankWithdraw(String name, double amount) {
      try {
          return bankWithdrawAsync(name, BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault] bankWithdraw() Sync Timeout! Bank %s may have been charged $%.2f without the calling plugin knowing.",
                  name, amount
          ));
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse bankDeposit(String name, double amount) {
      try {
          return bankDepositAsync(name, BigDecimal.valueOf(amount)).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().warning(String.format(
                  "[Vault] bankDeposit() Sync Timeout! Bank %s may have been deposited $%.2f without the calling plugin knowing.",
                  name, amount
          ));
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse isBankOwner(String name, String playerUUID) {
        try {
            return isBankOwnerAsync(name, playerUUID).get(500, TimeUnit.MILLISECONDS);

        } catch (TimeoutException e) {
            Bukkit.getLogger().info("[Vault-Economy] isBankOwner() Sync Timeout! Economy Provider is taking too long.");
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Economy timed out (500ms limit)");
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Error: " + e.getMessage());
        }
    }

    @Deprecated
    default EconomyResponse isBankOwner(String name, OfflinePlayer player) {
      try {
          return isBankOwnerAsync(name, player).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] isBankOwner() Sync Timeout! Economy Provider is taking too long.");
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default EconomyResponse isBankMember(String name, String playerUUID) {
        try {
            return isBankMemberAsync(name, playerUUID).get(500, TimeUnit.MILLISECONDS);

        } catch (TimeoutException e) {
            Bukkit.getLogger().info("[Vault-Economy] isBankMember() Sync Timeout! Economy Provider is taking too long.");
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Economy timed out (500ms limit)");
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Error: " + e.getMessage());
        }
    }

    @Deprecated
    default EconomyResponse isBankMember(String name, OfflinePlayer player) {
      try {
          return isBankMemberAsync(name, player).get(500, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] isBankMember() Sync Timeout! Economy Provider is taking too long.");
          return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Not Implemented or Took Too Long!");
      }
    }

    @Deprecated
    default List<String> getBanks() {
      try {
          return getBanksAsync().get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] getBanks() Sync Timeout! Economy Provider is taking too long.");
          return new ArrayList<>();
      }
    }

    //------------------------------------------ createPlayerAccount() Methods --------------------------------------------

    default CompletableFuture<Boolean> createAccountAsync(UUID uuid, String worldName, boolean player) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid), Vault.vaultEconomyService)
                .thenApplyAsync(offlinePlayer -> {
                    if (offlinePlayer.hasPlayedBefore()) {
                        return createPlayerAccount(offlinePlayer, worldName);
                    }

                    return createPlayerAccount(uuid.toString(), worldName);

                }, Vault.vaultEconomyService)
                .exceptionally(ex -> false);
    }

    default CompletableFuture<Boolean> createAccountAsync(OfflinePlayer offlinePlayer, String worldName, boolean player) {
      return createAccountAsync(offlinePlayer.getUniqueId(), worldName, true);
    }

    default CompletableFuture<Boolean> createAccountAsync(UUID uuid, String worldName, boolean player, String accountName) {
        return createAccountAsync(uuid, worldName, player);
    }

    default CompletableFuture<Boolean> createAccountAsync(OfflinePlayer offlinePlayer, String worldName, boolean player, String accountName) {
        return createAccountAsync(offlinePlayer.getUniqueId(), worldName, true, accountName);
    }

    private CompletableFuture<Boolean> createAccountAsync(String accountId, String worldName) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(accountId), Vault.vaultEconomyService)
                .thenComposeAsync(op -> {
                    if (op.hasPlayedBefore()) {
                        return createAccountAsync(op, worldName, true);
                    }
                    try {
                        return createAccountAsync(UUID.fromString(accountId), worldName, true);
                    } catch (IllegalArgumentException e) {
                        return CompletableFuture.completedFuture(false);
                    }
                }, Vault.vaultEconomyService)
                .exceptionally(ex -> false);
    }

    @Deprecated
    default boolean createPlayerAccount(String accountId) {
      try {
          return createAccountAsync(accountId, "").get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] createPlayerAccount() Sync Timeout! An account may have been made without the calling plugin knowing");
          return false;
      }
    }

    @Deprecated
    default boolean createPlayerAccount(OfflinePlayer player) {
      try {
          return createAccountAsync(player, null, true).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] createPlayerAccount() Sync Timeout! An account may have been made without the calling plugin knowing");
          return false;
      }
    }

    @Deprecated
    default boolean createPlayerAccount(String accountId, String worldName) {
      try {
          return createAccountAsync(accountId, worldName).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] createPlayerAccount() Sync Timeout! An account may have been made without the calling plugin knowing");
          return false;
      }
    }

    @Deprecated
    default boolean createPlayerAccount(OfflinePlayer player, String worldName) {
      try {
          return createAccountAsync(player, worldName, true).get(1000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
          Bukkit.getLogger().info("[Vault-Economy] createPlayerAccount() Sync Timeout! An account may have been made without the calling plugin knowing");
          return false;
      }
    }

    // --------------------------------------------------------------------------------------------------------
    // --------------------------------VintageVault Specific Methods-------------------------------------------
    // --------------------------------Renamed VaultUnlocked Methods-------------------------------------------
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

    default CompletableFuture<Boolean> deleteCurrency(String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<Boolean> deleteCurrency(String currency, String worldName) {
      return deleteCurrency(currency);
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

    default CompletableFuture<Boolean> accountSupportsCurrency(UUID accountId, String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<Boolean> accountSupportsCurrency(UUID accountId, String currency, String worldName) {
        return accountSupportsCurrency(accountId, currency);
    }

    default CompletableFuture<Boolean> deleteAccount(UUID accountId) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default CompletableFuture<Boolean> deleteAccount(UUID accountId, String worldName) {
        return deleteAccount(accountId);
    }



    // --------------------------------------------------------------------------------------------------------
    // ---------------------------VaultUnlocked / Vault2 Method Implementation---------------------------------
    // --------------------------------------Remapped to Async-------------------------------------------------
    // --------------------------------------------------------------------------------------------------------

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
    default CompletableFuture<Map<UUID, String>> getUUIDNameMap(){
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
    default CompletableFuture<String> getAccountName(@NotNull final UUID accountID){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * A method which changes the name associated with the given UUID in the
     * value returned from {@link #getUUIDNameMap()}.
     *
     * @param accountID UUID whose account is having a name change.
     * @param name String name that will be associated with the UUID in the map.
     * @return true if the name change is successful.
     */
    default CompletableFuture<Boolean> renameAccount(@NotNull final UUID accountID, @NotNull final String name, String worldName){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
                if (compare > 0) {
                    return withdrawAccount(plugin, accountID, worldName, balance.subtract(amount));
                }

                if (compare < 0) {
                    return depositAccount(plugin, accountID, worldName, amount.subtract(balance));
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
    default CompletableFuture<EconomyResponse> setAccountAsync(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount) {
        return getBalanceAsync(accountID, worldName, currency).thenCompose(balance -> {
            final int compare = balance.compareTo(amount);
            if(compare > 0) {
                return withdrawAccount(plugin, accountID, worldName, balance.subtract(amount), currency);
            }

            if(compare < 0) {
                return depositAccount(plugin, accountID, worldName, amount.subtract(balance), currency);
            }

            return CompletableFuture.completedFuture(new EconomyResponse(BigDecimal.ZERO, amount, EconomyResponse.ResponseType.SUCCESS, ""));
        });
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    default CompletableFuture<List<String>> accountsAccessToAsync(@NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

}