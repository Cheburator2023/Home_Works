package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private Deque<Customer> customers = new ArrayDeque<>();

    public CustomerReverseOrder() {
    }

    public Deque<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Deque<Customer> customers) {
        this.customers = customers;
    }


    public void add(Customer customer) {
        customers.addLast(customer);
    }

    public Customer take() {
        return getCustomers().removeLast();
    }
}