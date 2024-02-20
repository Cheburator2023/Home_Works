package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerStringMap = new TreeMap<>();

    public TreeMap<Customer, String> getCustomerStringMap() throws CloneNotSupportedException {
        TreeMap<Customer, String> cloneMap = new TreeMap<>();
        for (Customer customer : customerStringMap.keySet()) {
            cloneMap.put(customer.clone(), customerStringMap.get(customer));
        }
        return cloneMap;
    }

    public CustomerService() {
    }

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() throws CloneNotSupportedException {
        return getCustomerStringMap().firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) throws CloneNotSupportedException {
        return getCustomerStringMap().higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerStringMap.put(customer, data);
    }
}