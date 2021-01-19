package homework;


import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    PriorityQueue<Customer> data = new PriorityQueue<Customer>((o1, o2) -> Long.compare(o2.getScores(), o1.getScores()));
    Iterator<Customer> iterator;

    public void add(Customer customer) {
        this.data.add(customer);
    }

    public Customer take() {
        if (this.iterator == null) {
            this.iterator = data.iterator();
        }
        return iterator.next(); // это "заглушка, чтобы скомилировать"
    }
}
