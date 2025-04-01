package models.persons;

import java.time.LocalDate;

public class Customer extends Person {
    private int custNo;

    public Customer(String firstName, String lastName, String address, LocalDate dob, int custNo) {
        super(firstName, lastName, address, dob);
        this.custNo = custNo;
    }

    public int getCustNo() {
        return custNo;
    }

    public void setCustNo(int custNo) {
        this.custNo = custNo;
    }

    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + ", ID: " + custNo;
    }
}
