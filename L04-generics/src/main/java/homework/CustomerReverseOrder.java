package homework;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private final Deque<Customer> stackCustomer = new ArrayDeque<>();

    public void add(Customer customer) {
        stackCustomer.push(customer);
    }

    public Customer take() {
        return stackCustomer.pop(); // это "заглушка, чтобы скомилировать"
    }
}
