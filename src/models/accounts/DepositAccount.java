package models.accounts;

public class DepositAccount extends Account {
    public static double AIR = 0.02; // 2%

    public DepositAccount(int id, int custNo) {
        super(id, custNo);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds. Withdrawal denied.");

        }
        return false;
    }

    public static void setAIR(double newAIR) {
        AIR = newAIR;
    }

    public static double getAIR() {
        return AIR;
    }
}
