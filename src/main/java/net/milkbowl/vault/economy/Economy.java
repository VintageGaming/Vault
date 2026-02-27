package net.milkbowl.vault.economy;

import java.math.BigDecimal;
import java.util.*;

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
  
  @Deprecated
  boolean hasAccount(String playerName);
  
  boolean hasAccount(OfflinePlayer player);
  
  @Deprecated
  boolean hasAccount(String playerName, String worldName);
  
  boolean hasAccount(OfflinePlayer player, String worldName);
  
  @Deprecated
  double getBalance(String playerName);
  
  double getBalance(OfflinePlayer player);
  
  @Deprecated
  double getBalance(String playerName, String worldName);
  
  double getBalance(OfflinePlayer player, String worldName);
  
  @Deprecated
  boolean has(String playerName, double amount);
  
  boolean has(OfflinePlayer player, double amount);
  
  @Deprecated
  boolean has(String playerName, String worldName, double amount);
  
  boolean has(OfflinePlayer player, String worldName, double amount);
  
  @Deprecated
  EconomyResponse withdrawPlayer(String playerName, double amount);
  
  EconomyResponse withdrawPlayer(OfflinePlayer player, double amount);
  
  @Deprecated
  EconomyResponse withdrawPlayer(String playerName, String worldName, double amount);
  
  EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount);
  
  @Deprecated
  EconomyResponse depositPlayer(String playerName, double amount);
  
  EconomyResponse depositPlayer(OfflinePlayer player, double amount);
  
  @Deprecated
  EconomyResponse depositPlayer(String playerName, String worldName, double amount);
  
  EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount);
  
  @Deprecated
  EconomyResponse createBank(String name, String playerName);
  
  EconomyResponse createBank(String name, OfflinePlayer player);
  
  EconomyResponse deleteBank(String name);
  
  EconomyResponse bankBalance(String name);
  
  EconomyResponse bankHas(String name, double amount);
  
  EconomyResponse bankWithdraw(String name, double amount);
  
  EconomyResponse bankDeposit(String name, double amount);
  
  @Deprecated
  EconomyResponse isBankOwner(String name, String playerName);
  
  EconomyResponse isBankOwner(String name, OfflinePlayer player);
  
  @Deprecated
  EconomyResponse isBankMember(String name, String playerName);
  
  EconomyResponse isBankMember(String name, OfflinePlayer player);
  
  List<String> getBanks();
  
  @Deprecated
  boolean createPlayerAccount(String playerName);
  
  boolean createPlayerAccount(OfflinePlayer player);
  
  @Deprecated
  boolean createPlayerAccount(String playerName, String worldName);
  
  boolean createPlayerAccount(OfflinePlayer player, String worldName);

  // --------------------------------------------------------------------------------------------------------
  // --------------------------------VintageVault Specific Methods-------------------------------------------
  // --------------------------------Renamed VaultUnlocked Methods-------------------------------------------
  // --------------------------------------------------------------------------------------------------------


    // Return how many Decimal Points the plugin supports
    default int numberOfDecimals() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default int numberOfDecimals(String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean createCurrency(String currency, String singularName, String pluralName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean createCurrency(String currency, String singularName, String pluralName, String worldName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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

    default String getDefaultCurrencyName() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getDefaultCurrencyName(String worldName) {
        return getDefaultCurrencyName();
    }

    default String getDefaultCurrencySingular() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getDefaultCurrencySingular(String world) {
        return getDefaultCurrencySingular();
    }

    default String getDefaultCurrencyPlural() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getDefaultCurrencyPlural(String worldName) {
        return getDefaultCurrencyPlural();
    }

    default String getCurrencySingular(String currencyName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String getCurrencyPlural(String currencyName) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String[] getCurrencies() {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default String[] getCurrencies(String worldName) {
        return getCurrencies();
    }

    default BigDecimal getAccountBalance(UUID accountId) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default BigDecimal getAccountBalance(UUID accountId, String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default BigDecimal getAccountBalance(UUID accountId, String currency, String worldName) {
        return getAccountBalance(accountId, currency);
    }

    default boolean accountHas(UUID accountId, BigDecimal amount) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean accountHasInWorld(UUID accountId, BigDecimal amount, String worldName) {
        return accountHas(accountId, amount);
    }

    default boolean accountHas(UUID accountId, BigDecimal amount, String currency) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    default boolean accountHasInWorld(UUID accountId, BigDecimal amount, String currency, String worldName) {
        return accountHas(accountId, amount, currency);
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, final boolean player){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    default boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final String worldName, final boolean player){
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
    default String getAccountName(@NotNull final UUID accountID){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Checks if this UUID has an account yet.
     *
     * @param accountID UUID to check for an existing account.
     * @return true if the UUID has an account.
     */
    default boolean hasAccount(@NotNull final UUID accountID){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Checks if this UUID has an account yet on the given world.
     *
     * @param accountID      UUID to check for an existing account.
     * @param worldName world-specific account.
     * @return if the UUID has an account.
     */
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
     * Sets the amount of monies for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
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
     * Sets the amount of specified currency for a player in a specific world.
     *
     * @param accountID the unique identifier of the player's account
     * @param worldName the name of the world where the currency amount is being set
     * @param currency the name of the currency being set
     * @param amount the amount of currency to set for the player in the specified world
     * @return an EconomyResponse object indicating the result of the operation
     */
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
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param accountID   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link EconomyResponse.ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount, @NotNull final String currency){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountWithdraw(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final BigDecimal amount, @NotNull final String currency){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    @NotNull
    default EconomyResponse accountDeposit(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
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
    default boolean createSharedAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final UUID owner){
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
    default List<String> accountsOwnedBy(@NotNull final UUID accountID) {
        return accountsAccessTo(accountID, AccountPermission.OWNER);
    }

    /**
     * Retrieves a list of account IDs that the specified account is a member of.
     *
     * @param accountID the UUID of the account to check membership for
     * @return a List of String values representing the accounts that the account is a member of
     *
     * @since 2.14
     */
    default List<String> accountsMemberOf(@NotNull final UUID accountID) {
        return accountsAccessTo(accountID, AccountPermission.BALANCE, AccountPermission.DEPOSIT, AccountPermission.WITHDRAW);
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
    default List<String> accountsAccessTo(@NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {
        return new ArrayList<>();
    }

    /**
     * Determines whether the specified owner ID is the owner of the account associated with the given account ID and plugin name.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} to check for ownership of the account
     * @return true if the owner ID is the owner of the account, false otherwise
     */
    default boolean isAccountOwner(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Sets the owner of a specified plugin to the given accountID.
     *
     * @param accountID  The {@link UUID} of the account
     * @param uuid       The {@link UUID} of the account to set as the owner.
     * @return true if the owner is successfully set, false otherwise.
     */
    default boolean setAccountOwner(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Determines whether a specific member is an account member of a given plugin.
     *
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} to check for membership.
     * @return true if the member is an account member, false otherwise.
     */
    default boolean isAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Adds a member to an account.
     *
     * @param accountID  The {@link UUID} of the account.
     * @param uuid       The {@link UUID} of the member to be added.
     * @return true if the member was successfully added, false otherwise.
     */
    default boolean addAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
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
    default boolean addAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission... initialPermissions){
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

    /**
     * Removes a member from an account.
     *
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} of the member to be removed
     * @return true if the member was successfully removed, false otherwise
     */
    default boolean removeAccountMember(@NotNull final UUID accountID, @NotNull final UUID uuid){
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
    default boolean hasAccountPermission(@NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission){
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
    default boolean updateAccountPermission(@NotNull String plugin, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission, final boolean value) {
        throw new UnsupportedOperationException(getName() + " does not support this method.");
    }

}