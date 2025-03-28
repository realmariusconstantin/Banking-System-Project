package models.accounts;

import java.time.LocalDate;

public abstract class Account {
    protected int id;
    protected int custNo;
    protected double balance;
    protected LocalDate dateCreated;

    public Account(int id, int custNo) {
        this.id = id;
        this.custNo = custNo;
        this.balance = 0.0;
        this.dateCreated = LocalDate.now();
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public int getId() { return id; }
    public int getCustNo() { return custNo; }
    public double getBalance() { return balance; }
    public LocalDate getDateCreated() { return dateCreated; }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
