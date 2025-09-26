package net.milkbowl.vault.economy;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/*    */
public class EconomyResponse
{
  public final BigDecimal amount;
  public final BigDecimal balance;
  public final ResponseType type;
  public final String errorMessage;
  
  public enum ResponseType
  {
     SUCCESS(1),
     FAILURE(2),
     NOT_IMPLEMENTED(3);
    
    private int id;
    
    ResponseType(int id) {
       this.id = id;
    }
    
    int getId() {
       return this.id;
    }
  }
/*    */
  public EconomyResponse(double amount, double balance, ResponseType type, String errorMessage) {
     this.amount = BigDecimal.valueOf(amount);
     this.balance = BigDecimal.valueOf(balance);
     this.type = type;
     this.errorMessage = errorMessage;
  }

  public EconomyResponse(@NotNull final BigDecimal amount, @NotNull final BigDecimal balance, @NotNull final ResponseType type, @NotNull final String errorMessage) {
      this.amount = amount;
      this.balance = balance;
      this.type = type;
      this.errorMessage = errorMessage;
    }

/*    */
  public boolean transactionSuccess() {
     switch (this.type) {
      case SUCCESS:
         return true;
    } 
     return false;
  }
}