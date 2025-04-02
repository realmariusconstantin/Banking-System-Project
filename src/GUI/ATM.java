package GUI;

import data.ThomondBankData;
import models.accounts.Account;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ATM {
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
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(accountIDTextField.getText());
                Account acc = findAccountById(id);
                if (acc != null) {
                    String input = JOptionPane.showInputDialog("Enter amount to deposit:");
                    double amount = Double.parseDouble(input);
                    acc.deposit(amount);
                    JOptionPane.showMessageDialog(null, "Deposited €" + amount);
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found.");
                }
            }
        });
    }
}
