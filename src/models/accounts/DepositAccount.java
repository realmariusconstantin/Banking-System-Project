package models.accounts;

public class DepositAccount extends Account {
    public static double AIR = 0.02; // 2%

    public DepositAccount(int id, int custNo) {
        super(id, custNo);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds. Withdrawal denied.");
        }
    }

    public static void setAIR(double newAIR) {
        AIR = newAIR;
    }

    public static double getAIR() {
        return AIR;
    }
}
