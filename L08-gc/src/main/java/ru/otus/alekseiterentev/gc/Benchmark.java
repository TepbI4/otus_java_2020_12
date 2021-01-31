package ru.otus.alekseiterentev.gc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Benchmark implements BenchmarkMBean {

    private final int loopCounter;
    private volatile int size = 0;
    private static List<Object> list = new ArrayList<>();

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            list.addAll(Arrays.asList(array).subList(0,size/25));
            Thread.sleep(5000); //Label_1
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
