package net.milkbowl.vault.economy.plugins;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Economy.Economy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class Economy_CMI extends AbstractEconomy {
  public CMI cmi;
  
  Plugin plugin;
  
  private final String name = "CMI Economy";
  
  private static final Logger log = Logger.getLogger("Minecraft");
  
  public Economy_CMI(Plugin plugin) {
    this.plugin = plugin;
    Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);
    if (this.cmi == null) {
      File file = new File("plugins" + File.separator + "CMI" + File.separator + "config.yml");
      Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
      if (vault == null) {
        log.info("Could not finf Vault plugin");
        return;
      } 
      Vault vaultP = (Vault)vault;
      if (vaultP.getDescription().getDescription().contains("CMIEconomy")) {
        log.info("Vault plugin was found, but it has direct support for CMI Economy already");
        return;
      } 
      if (!file.isFile()) {
        Bukkit.getConsoleSender().sendMessage("[CMIEInjector] " + ChatColor.translateAlternateColorCodes('&', "&cCant find CMI config file"));
        return;
      } 
      YamlConfiguration yaml = new YamlConfiguration();
      try {
        yaml.load(file);
      } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e1) {
        e1.printStackTrace();
        Bukkit.getConsoleSender().sendMessage("[CMIEInjector] " + ChatColor.translateAlternateColorCodes('&', "&cCant find CMI config file"));
        return;
      } 
      if (yaml.getBoolean("Economy.Enabled")) {
        Plugin cm = plugin.getServer().getPluginManager().getPlugin("CMI");
        if (cm != null && cm.isEnabled()) {
          this.cmi = (CMI)cm;
          CMI.getInstance().consoleMessage(String.format("[%s][Economy] %s hooked.", new Object[] { plugin.getDescription().getName(), "CMI Economy" }));
        } 
      } 
    } 
  }
  
  public void setCMI(CMI cmi) {
    this.cmi = cmi;
  }

    @Override
  public boolean isEnabled() {
    return (this.cmi != null);
  }

    @Override
  public String getName() {
    return "CMIEconomy";
  }

    @Override
  public String format(double amount) {
    return Economy.format(amount);
  }

    @Override
  public String currencyNameSingular() {
    return "";
  }

    @Override
  public String currencyNamePlural() {
    return "";
  }

    @Override
  public double getBalance(String playerName) {
    return Economy.getBalance(playerName);
  }

    @Override
  public double getBalance(OfflinePlayer offlinePlayer) {
    return Economy.getBalance(offlinePlayer);
  }

    @Override
  public EconomyResponse withdrawPlayer(String playerName, double amount) {
    return Economy.withdrawPlayer(playerName, amount);
  }

    @Override
  public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
    return Economy.withdrawPlayer(offlinePlayer, amount);
  }

    @Override
  public EconomyResponse depositPlayer(String playerName, double amount) {
    return Economy.depositPlayer(playerName, amount);
  }

    @Override
  public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
    return Economy.depositPlayer(offlinePlayer, amount);
  }

    @Override
  public boolean has(String playerName, double amount) {
    return Economy.has(playerName, amount);
  }

    @Override
  public boolean has(OfflinePlayer offlinePlayer, double amount) {
    return (getBalance(offlinePlayer) >= amount);
  }

    @Override
  public EconomyResponse createBank(String name, String player) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse createBank(String name, OfflinePlayer offlinePlayer) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse deleteBank(String name) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse bankHas(String name, double amount) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse bankWithdraw(String name, double amount) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse bankDeposit(String name, double amount) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse isBankOwner(String name, String playerName) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse isBankOwner(String name, OfflinePlayer offlinePlayer) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse isBankMember(String name, String playerName) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse isBankMember(String name, OfflinePlayer offlinePlayer) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public EconomyResponse bankBalance(String name) {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "CMI currently don't support bank's");
  }

    @Override
  public List<String> getBanks() {
    return new ArrayList<>();
  }

    @Override
  public boolean hasBankSupport() {
    return false;
  }

    @Override
  public boolean hasAccount(String playerName) {
    return true;
  }

    @Override
  public boolean hasAccount(OfflinePlayer offlinePlayer) {
    return true;
  }

    @Override
  public boolean createPlayerAccount(String playerName) {
    return Economy.createPlayerAccount(playerName);
  }

    @Override
  public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
    return Economy.createPlayerAccount(offlinePlayer);
  }

    @Override
  public int fractionalDigits() {
    return -1;
  }

    @Override
  public boolean hasAccount(String playerName, String worldName) {
    return hasAccount(playerName);
  }

    @Override
  public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName) {
    return hasAccount(offlinePlayer);
  }

    @Override
  public double getBalance(String playerName, String worldName) {
    return getBalance(playerName);
  }

    @Override
  public double getBalance(OfflinePlayer offlinePlayer, String worldName) {
    return getBalance(offlinePlayer);
  }

    @Override
  public boolean has(String playerName, String worldName, double amount) {
    return has(playerName, amount);
  }

    @Override
  public boolean has(OfflinePlayer offlinePlayer, String worldName, double amount) {
    return has(offlinePlayer, amount);
  }

    @Override
  public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
    return withdrawPlayer(playerName, amount);
  }

    @Override
  public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
    return withdrawPlayer(offlinePlayer, amount);
  }

    @Override
  public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
    return depositPlayer(playerName, amount);
  }

    @Override
  public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
    return depositPlayer(offlinePlayer, amount);
  }

    @Override
  public boolean createPlayerAccount(String playerName, String worldName) {
    return createPlayerAccount(playerName);
  }

    @Override
  public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName) {
    return createPlayerAccount(offlinePlayer);
  }

  public class EconomyServerListener implements Listener {
    Economy_CMI economy = null;
    
    public EconomyServerListener(Economy_CMI economy) {
      this.economy = economy;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
      if (this.economy.cmi == null) {
        Plugin pl = event.getPlugin();
        if (pl.getDescription().getName().equals("CMI")) {
          this.economy.cmi = (CMI)pl;
          (new Object[2])[0] = Economy_CMI.this.plugin.getDescription().getName();
          this.economy.getClass();
          Economy_CMI.log.info(String.format("[%s][Economy] %s hooked.", new Object[] { null, "CMI Economy" }));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
      if (this.economy.cmi != null && 
        event.getPlugin().getDescription().getName().equals("CMI")) {
        this.economy.cmi = null;
        Economy.plugin = null;
        (new Object[2])[0] = Economy_CMI.this.plugin.getDescription().getName();
        this.economy.getClass();
        Economy_CMI.log.info(String.format("[%s][Economy] %s unhooked.", new Object[] { null, "CMI Economy" }));
      } 
    }
  }
}