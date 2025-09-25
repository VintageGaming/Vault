package net.milkbowl.vault.economy.plugins;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.ess3.api.MaxMoneyException;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
/*     */
public class Economy_Essentials
  extends AbstractEconomy
{
   private final String name = "Essentials Economy";
  private final Logger log;
   private Plugin plugin = null;
   private Essentials ess = null;
  
  public Economy_Essentials(Plugin plugin) {
     this.plugin = plugin;
     this.log = plugin.getLogger();
     Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);

    
     if (this.ess == null) {
       Plugin essentials = plugin.getServer().getPluginManager().getPlugin("Essentials");
       if (essentials != null && essentials.isEnabled()) {
         this.ess = (Essentials)essentials;
         this.log.info(String.format("[Economy] %s hooked.", new Object[] { "Essentials Economy" }));
      } 
    } 
  }

  @Override
  public boolean isEnabled() {
     if (this.ess == null) {
       return false;
    }
     return this.ess.isEnabled();
  }


  @Override
  public String getName() {
     return "Essentials Economy";
  }


    @Override
  public double getBalance(String playerName) {
    double balance;
    try {
       balance = Economy.getMoney(playerName);
     } catch (UserDoesNotExistException e) {
       createPlayerAccount(playerName);
       balance = 0.0D;
    } 
    
     return balance;
  }

    @Override
  public boolean createPlayerAccount(String playerName) {
     if (hasAccount(playerName)) {
       return false;
    }
     return Economy.createNPC(playerName);
  }

    @Override
  public EconomyResponse withdrawPlayer(String playerName, double amount) {
    double balance;
    EconomyResponse.ResponseType type;
     if (playerName == null) {
       return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
    }
     if (amount < 0.0D) {
       return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
    }


    
     String errorMessage = null;
    
    try {
       Economy.subtract(playerName, amount);
       balance = Economy.getMoney(playerName);
       type = EconomyResponse.ResponseType.SUCCESS;
     } catch (UserDoesNotExistException | MaxMoneyException e) {
       if (createPlayerAccount(playerName)) {
         return withdrawPlayer(playerName, amount);
      }
       amount = 0.0D;
       balance = 0.0D;
       type = EconomyResponse.ResponseType.FAILURE;
       errorMessage = "User does not exist";
    }
     catch (NoLoanPermittedException e) {
      try {
         balance = Economy.getMoney(playerName);
         amount = 0.0D;
         type = EconomyResponse.ResponseType.FAILURE;
         errorMessage = "Loan was not permitted";
       } catch (UserDoesNotExistException e1) {
         amount = 0.0D;
         balance = 0.0D;
         type = EconomyResponse.ResponseType.FAILURE;
         errorMessage = "User does not exist";
      } 
    }
/*     */
     return new EconomyResponse(amount, balance, type, errorMessage);
  }


  public EconomyResponse tryDepositPlayer(String playerName, double amount, int tries) {
    double balance;
    EconomyResponse.ResponseType type;
     if (playerName == null) {
       return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
    }
     if (amount < 0.0D) {
       return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot desposit negative funds");
    }
     if (tries <= 0) {
       return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.FAILURE, "Failed to deposit amount.");
    }


    
     String errorMessage = null;
    
    try {
       Economy.add(playerName, amount);
       balance = Economy.getMoney(playerName);
       type = EconomyResponse.ResponseType.SUCCESS;
     } catch (UserDoesNotExistException | MaxMoneyException e) {
       if (createPlayerAccount(playerName)) {
         return tryDepositPlayer(playerName, amount, tries--);
      }
       amount = 0.0D;
       balance = 0.0D;
       type = EconomyResponse.ResponseType.FAILURE;
       errorMessage = "User does not exist";
    }
     catch (NoLoanPermittedException e) {
      try {
         balance = Economy.getMoney(playerName);
         amount = 0.0D;
         type = EconomyResponse.ResponseType.FAILURE;
         errorMessage = "Loan was not permitted";
       } catch (UserDoesNotExistException e1) {
         balance = 0.0D;
         amount = 0.0D;
         type = EconomyResponse.ResponseType.FAILURE;
         errorMessage = "Loan was not permitted";
      } 
    } 
    
     return new EconomyResponse(amount, balance, type, errorMessage);
  }

    @Override
  public EconomyResponse depositPlayer(String playerName, double amount) {
     return tryDepositPlayer(playerName, amount, 2);
  }
  
  public class EconomyServerListener implements Listener {
     Economy_Essentials economy = null;
    
    public EconomyServerListener(Economy_Essentials economy) {
       this.economy = economy;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
       if (this.economy.ess == null) {
         Plugin essentials = event.getPlugin();
        
         if (essentials.getDescription().getName().equals("Essentials")) {
           this.economy.ess = (Essentials)essentials;
           Economy_Essentials.this.log.info(String.format("[Economy] %s hooked.", new Object[] { "Essentials Economy" }));
        } 
      } 
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
       if (this.economy.ess != null && 
         event.getPlugin().getDescription().getName().equals("Essentials")) {
         this.economy.ess = null;
         Economy_Essentials.this.log.info(String.format("[Economy] %s unhooked.", new Object[] { "Essentials Economy" }));
      } 
    }
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
  public boolean has(String playerName, double amount) {
    try {
       return Economy.hasEnough(playerName, amount);
     } catch (UserDoesNotExistException e) {
       return false;
    } 
  }

    @Override
  public EconomyResponse createBank(String name, String player) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse deleteBank(String name) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse bankHas(String name, double amount) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse bankWithdraw(String name, double amount) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse bankDeposit(String name, double amount) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse isBankOwner(String name, String playerName) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse isBankMember(String name, String playerName) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
  }

    @Override
  public EconomyResponse bankBalance(String name) {
     return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Essentials Eco does not support bank accounts!");
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
     return Economy.playerExists(playerName);
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
  public double getBalance(String playerName, String world) {
     return getBalance(playerName);
  }

    @Override
  public boolean has(String playerName, String worldName, double amount) {
     return has(playerName, amount);
  }

    @Override
  public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
     return withdrawPlayer(playerName, amount);
  }

    @Override
  public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
     return depositPlayer(playerName, amount);
  }

    @Override
  public boolean createPlayerAccount(String playerName, String worldName) {
     return createPlayerAccount(playerName);
  }
}