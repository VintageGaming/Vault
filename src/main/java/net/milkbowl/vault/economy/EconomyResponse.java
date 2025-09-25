package net.milkbowl.vault.economy;
/*    */
public class EconomyResponse
{
  public final double amount;
  public final double balance;
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