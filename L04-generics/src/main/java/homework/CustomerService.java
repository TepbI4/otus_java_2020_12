package homework;


import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> data = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Entry<Customer, String> smallest = data.firstEntry();
        return new AbstractMap.SimpleEntry<Customer, String>(new Customer(smallest.getKey()), smallest.getValue()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return data.higherEntry(customer); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        this.data.put(customer, data);
    }
}
