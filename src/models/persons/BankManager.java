package models.persons;

import java.time.LocalDate;

public class BankManager extends BankStaff {

    public BankManager(String firstName, String lastName, String address, LocalDate dob, int empNo, String jobTitle) {
        super(firstName, lastName, address, dob, empNo, jobTitle);
    }

    @Override
    public String toString() {
        return "Bank Manager: " + firstName + " " + lastName + ", EmpNo: " + empNo + ", Job Title: " + jobTitle;
    }
}
