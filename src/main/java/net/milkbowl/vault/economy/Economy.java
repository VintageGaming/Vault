package net.milkbowl.vault.economy;

import java.util.List;
import org.bukkit.OfflinePlayer;

public interface Economy {
  boolean isEnabled();
  
  String getName();
  
  boolean hasBankSupport();
  
  int fractionalDigits();
  
  String format(double paramDouble);
  
  String currencyNamePlural();
  
  String currencyNameSingular();
  
  @Deprecated
  boolean hasAccount(String paramString);
  
  boolean hasAccount(OfflinePlayer paramOfflinePlayer);
  
  @Deprecated
  boolean hasAccount(String paramString1, String paramString2);
  
  boolean hasAccount(OfflinePlayer paramOfflinePlayer, String paramString);
  
  @Deprecated
  double getBalance(String paramString);
  
  double getBalance(OfflinePlayer paramOfflinePlayer);
  
  @Deprecated
  double getBalance(String paramString1, String paramString2);
  
  double getBalance(OfflinePlayer paramOfflinePlayer, String paramString);
  
  @Deprecated
  boolean has(String paramString, double paramDouble);
  
  boolean has(OfflinePlayer paramOfflinePlayer, double paramDouble);
  
  @Deprecated
  boolean has(String paramString1, String paramString2, double paramDouble);
  
  boolean has(OfflinePlayer paramOfflinePlayer, String paramString, double paramDouble);
  
  @Deprecated
  EconomyResponse withdrawPlayer(String paramString, double paramDouble);
  
  EconomyResponse withdrawPlayer(OfflinePlayer paramOfflinePlayer, double paramDouble);
  
  @Deprecated
  EconomyResponse withdrawPlayer(String paramString1, String paramString2, double paramDouble);
  
  EconomyResponse withdrawPlayer(OfflinePlayer paramOfflinePlayer, String paramString, double paramDouble);
  
  @Deprecated
  EconomyResponse depositPlayer(String paramString, double paramDouble);
  
  EconomyResponse depositPlayer(OfflinePlayer paramOfflinePlayer, double paramDouble);
  
  @Deprecated
  EconomyResponse depositPlayer(String paramString1, String paramString2, double paramDouble);
  
  EconomyResponse depositPlayer(OfflinePlayer paramOfflinePlayer, String paramString, double paramDouble);
  
  @Deprecated
  EconomyResponse createBank(String paramString1, String paramString2);
  
  EconomyResponse createBank(String paramString, OfflinePlayer paramOfflinePlayer);
  
  EconomyResponse deleteBank(String paramString);
  
  EconomyResponse bankBalance(String paramString);
  
  EconomyResponse bankHas(String paramString, double paramDouble);
  
  EconomyResponse bankWithdraw(String paramString, double paramDouble);
  
  EconomyResponse bankDeposit(String paramString, double paramDouble);
  
  @Deprecated
  EconomyResponse isBankOwner(String paramString1, String paramString2);
  
  EconomyResponse isBankOwner(String paramString, OfflinePlayer paramOfflinePlayer);
  
  @Deprecated
  EconomyResponse isBankMember(String paramString1, String paramString2);
  
  EconomyResponse isBankMember(String paramString, OfflinePlayer paramOfflinePlayer);
  
  List<String> getBanks();
  
  @Deprecated
  boolean createPlayerAccount(String paramString);
  
  boolean createPlayerAccount(OfflinePlayer paramOfflinePlayer);
  
  @Deprecated
  boolean createPlayerAccount(String paramString1, String paramString2);
  
  boolean createPlayerAccount(OfflinePlayer paramOfflinePlayer, String paramString);
}