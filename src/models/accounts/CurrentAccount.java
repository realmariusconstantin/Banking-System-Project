package models.accounts;

public class CurrentAccount extends Account {
    public static double AIR = 0.005; // 0.5%
    private double overdraft;

    public CurrentAccount(int id, int custNo, double overdraft) {
        super(id, custNo);
        this.overdraft = overdraft;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= (balance + overdraft)) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Withdrawal exceeds overdraft limit. Denied.");
        }
        return false;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public static void setAIR(double newAIR) {
        AIR = newAIR;
    }

    public static double getAIR() {
        return AIR;
    }
}
