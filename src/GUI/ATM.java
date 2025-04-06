package GUI;

import data.ThomondBankData;
import models.accounts.Account;

import javax.swing.*;
import java.time.LocalDate;

import models.accounts.CurrentAccount;
import models.accounts.DepositAccount;
import models.persons.BankManager;
import models.persons.BankOfficer;
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
            String[] options = {"Customer", "Bank Staff"};
            int Choice = JOptionPane.showOptionDialog(null, "Select Account Type", "Account Type",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (Choice == 0) {
                // Create Customer
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField addressField = new JTextField();
                JTextField dobField = new JTextField();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("First Name:"));
                panel.add(firstNameField);
                panel.add(new JLabel("Last Name:"));
                panel.add(lastNameField);
                panel.add(new JLabel("Address:"));
                panel.add(addressField);
                panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
                panel.add(dobField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Create Customer Account", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String address = addressField.getText();
                    LocalDate dob = LocalDate.parse(dobField.getText());
                    int custNo = ThomondBankData.thomondCustomers.size() + 1;

                    Customer customer = new Customer(firstName, lastName, address, dob, custNo);
                    ThomondBankData.thomondCustomers.add(customer);

                    // Ask what type of account to create
                    String[] accountTypes = {"Deposit Account", "Current Account"};
                    int accType = JOptionPane.showOptionDialog(null, "Select Account Type", "Account Type",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, accountTypes, accountTypes[0]);

                    int newAccountId = ThomondBankData.generateAccountId();

                    if (accType == 0) {
                        DepositAccount da = new DepositAccount(newAccountId, custNo);
                        ThomondBankData.thomondAccounts.add(da);
                        JOptionPane.showMessageDialog(null, "Deposit Account created for customer.");
                    } else if (accType == 1) {
                        String overdraftStr = JOptionPane.showInputDialog("Enter Overdraft Limit for Current Account:");
                        double overdraft = Double.parseDouble(overdraftStr);
                        CurrentAccount ca = new CurrentAccount(newAccountId, custNo, overdraft);
                        ThomondBankData.thomondAccounts.add(ca);
                        JOptionPane.showMessageDialog(null, "Current Account created for customer.");
                    }

                    JOptionPane.showMessageDialog(null, "Customer created. Cust No: " + custNo);
                }
            }
        });
    }
}



