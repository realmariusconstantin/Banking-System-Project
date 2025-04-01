package models.persons;

import java.time.LocalDate;

public class BankOfficer extends BankStaff {

    public BankOfficer(String firstName, String lastName, String address, LocalDate dob, int empNo, String jobTitle) {
        super(firstName, lastName, address, dob, empNo, jobTitle);
    }

    @Override
    public String toString() {
        return "Bank Officer: " + firstName + " " + lastName + ", EmpNo: " + empNo + ", Job Title: " + jobTitle;
    }
}
