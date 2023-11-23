package homework;

import java.util.ArrayList;
import java.util.List;

public class CustomerReverseOrder {

    private List<Customer> customers = new ArrayList<>();

    public CustomerReverseOrder() {
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    // todo: 2. надо реализовать методы этого класса
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer1) {
        customers.add(customer1);
    }

    public Customer take() {
        return getCustomers().removeLast();
    }
}