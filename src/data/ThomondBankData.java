package data;

import javax.swing.*;
import java.util.ArrayList;
import models.accounts.*;

public class ThomondBankData extends javax.swing.JFrame {

    public static ArrayList<Account> thomondAccounts = new ArrayList<>();

    public ThomondBankData() {
        populateMyAccounts();
    }

    private void populateMyAccounts() {
        thomondAccounts.add(new DepositAccount(1, 1));
        thomondAccounts.get(0).deposit(100);

        thomondAccounts.add(new DepositAccount(2, 2));
        thomondAccounts.get(1).deposit(500);

        thomondAccounts.add(new DepositAccount(3, 3));
        thomondAccounts.get(2).deposit(300);

        thomondAccounts.add(new DepositAccount(4, 4));
        thomondAccounts.get(3).deposit(300);

        thomondAccounts.add(new CurrentAccount(5, 1, 100));
        thomondAccounts.add(new CurrentAccount(6, 2, 1000));
        thomondAccounts.add(new CurrentAccount(7, 4, 200));
    }
}
