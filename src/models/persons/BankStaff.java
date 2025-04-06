package models.persons;

import java.time.LocalDate;

public class BankStaff extends Person {
    protected int empNo;
    protected String jobTitle;

    public BankStaff(String firstName, String lastName, String address, LocalDate dob, int empNo, String jobTitle) {
        super(firstName, lastName, address, dob);
        this.empNo = empNo;
        this.jobTitle = jobTitle;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
