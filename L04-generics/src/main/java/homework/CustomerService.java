package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String> data = new TreeMap<Customer, String>((o1, o2) -> Long.compare(o1.getScores(), o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return data.firstEntry(); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return data.higherEntry(customer); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        this.data.put(customer, data);
    }
}
