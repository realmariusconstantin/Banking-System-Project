package GUI;

import data.ThomondBankData;
import models.accounts.Account;

import javax.swing.*;
import java.time.LocalDate;

import models.accounts.CurrentAccount;
import models.accounts.DepositAccount;
import models.persons.BankManager;
import models.persons.BankOfficer;
import models.persons.BankStaff;
import models.persons.Customer;


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

    // Method to find an account by ID
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

        //withdraw button
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

        //balance button
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

        //logout button
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

        // Create New Account button
        createNewAccountButton.addActionListener(e -> {
            JTextField accountIDField = new JTextField();
            JTextField customerNoField = new JTextField();
            JTextField overdraftField = new JTextField();
            JRadioButton depositAccountRadioButton = new JRadioButton("Deposit Account");
            JRadioButton currentAccountRadioButton = new JRadioButton("Current Account");
            accountTypeGroup.add(depositAccountRadioButton);
            accountTypeGroup.add(currentAccountRadioButton);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Account ID:"));
            panel.add(accountIDField);
            panel.add(new JLabel("Customer Number:"));
            panel.add(customerNoField);
            panel.add(new JLabel("Account Type:"));
            panel.add(depositAccountRadioButton);
            panel.add(currentAccountRadioButton);
            panel.add(new JLabel("Overdraft Limit (Current Only):"));
            panel.add(overdraftField);
            overdraftField.setEnabled(currentAccountRadioButton.isSelected());

            currentAccountRadioButton.addActionListener(ev -> overdraftField.setEnabled(true));
            depositAccountRadioButton.addActionListener(ev -> overdraftField.setEnabled(false));

            int result = JOptionPane.showConfirmDialog(null, panel, "New Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int accountId = Integer.parseInt(accountIDField.getText());
                    int customerNo = Integer.parseInt(customerNoField.getText());

                    Account newAccount;
                    if (currentAccountRadioButton.isSelected()) {
                        double overdraft = Double.parseDouble(overdraftField.getText());
                        newAccount = new CurrentAccount(accountId, customerNo, overdraft);
                    } else {
                        newAccount = new DepositAccount(accountId, customerNo);
                    }

                    ThomondBankData.thomondAccounts.add(newAccount);
                    JOptionPane.showMessageDialog(null, "New account created with ID: " + newAccount.getId());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
                }
            }
        });
        // Change Air Button

        changeAIRButton.addActionListener(e -> { {
                // Ask user which account type
                Object[] options = {"Deposit Account", "Current Account"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Apply AIR to which account type?",
                        "Change AIR",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == JOptionPane.CLOSED_OPTION) return;

                // Ask user for the new AIR %
                String input = JOptionPane.showInputDialog("Enter new AIR (%):");
                try {
                    double air = Double.parseDouble(input) / 100;

                    // Apply to correct account type
                    if (choice == 0) {
                        DepositAccount.setAIR(air);
                        JOptionPane.showMessageDialog(null, "Deposit AIR set to " + (air * 100) + "%");
                    } else if (choice == 1) {
                        CurrentAccount.setAIR(air);
                        JOptionPane.showMessageDialog(null, "Current AIR set to " + (air * 100) + "%");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid AIR. Please enter a number like 2.5");
                }
            }
        });

        // Change OverDraft Limit

        changeOverdraftLimitButton.addActionListener(e ->  {
                try {

                    String idInput = JOptionPane.showInputDialog("Enter Account ID");
                    int accId = Integer.parseInt(idInput);

                    Account acc = findAccountById(accId);

                    if (acc instanceof CurrentAccount) {

                        String overdraftINput = JOptionPane.showInputDialog("Enter New Overdraft Limit");
                        double newLimit = Double.parseDouble(overdraftINput);

                        ((CurrentAccount) acc).setOverdraft(newLimit);

                        JOptionPane.showMessageDialog(null, "Overdraft Limit updated to € " + newLimit + "for Account ID: " + accId);

                    } else if (acc != null) {
                        JOptionPane.showMessageDialog(null, "This is not a Current Account");


                    } else {
                        JOptionPane.showMessageDialog(null, "Account Not Found");

                    }


                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");

                }
        });
    }
}
