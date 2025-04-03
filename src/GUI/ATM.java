package GUI;

import data.ThomondBankData;
import models.accounts.Account;
import models.accounts.CurrentAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ATM {
    ;

    private JPanel rootPanel;
    private JTabbedPane ATMTabbedPane;
    private JTextField accountIDTextField;
    private JRadioButton depositAccountRadioButton;
    private JRadioButton currentAccountRadioButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton logoutButton;
    private JPanel buttonPanel;
    private JPanel accountPanel;
    private JButton createNewAccountButton;
    private JButton changeAIRButton;
    private JButton changeOverdraftLimitButton;


    public static void main(String[] args) {
        new ThomondBankData();
        JFrame frame = new JFrame("ATM");
        frame.setContentPane(new ATM().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Account findAccountById(int id) {
        for (Account acc : ThomondBankData.thomondAccounts) {
            if (acc.getId() == id) return acc;
        }
        return null;
    }

    public ATM() {
        depositButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(accountIDTextField.getText());
                Account acc = findAccountById(id);
                if (acc != null) {
                    String input = JOptionPane.showInputDialog("Balance: " + acc.getBalance() + "\nEnter amount to deposit:");
                    double amount = Double.parseDouble(input);
                    acc.deposit(amount);
                    JOptionPane.showMessageDialog(null, "Deposited €" + amount + ". \nNew Balance: €" + acc.getBalance());
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });

        withdrawButton.addActionListener(e -> {
            try {
                int accountId = Integer.parseInt(accountIDTextField.getText().trim());
                double amountToWithdraw = Double.parseDouble(
                        JOptionPane.showInputDialog("Enter amount to withdraw:")
                );

                Account account = findAccountById(accountId);

                if (account != null) {
                    boolean success = account.withdraw(amountToWithdraw);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Withdrawal successful. New balance: " + account.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(null, "Withdrawal failed. Insufficient funds.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Account not found.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });


        balanceButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(accountIDTextField.getText());
                Account acc = findAccountById(id);
                if (acc != null) {
                    JOptionPane.showMessageDialog(null, "Balance: €" + acc.getBalance());
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        //Group up the buttons
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(depositAccountRadioButton);
        accountTypeGroup.add(currentAccountRadioButton);

        //when deposit account is selected, disable the overdraft limit button
        depositAccountRadioButton.addActionListener(e -> {
            changeOverdraftLimitButton.setEnabled(false);
        });

        //when current account is selected, enable the overdraft limit button
        currentAccountRadioButton.addActionListener(e -> {
            changeOverdraftLimitButton.setEnabled(true);
        });


    }
}
