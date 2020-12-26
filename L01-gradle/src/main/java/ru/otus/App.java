package ru.otus;

import com.google.common.base.Ticker;

public class App {
    public static void main(String[] args) {
        Ticker systemTicker = Ticker.systemTicker();
        System.out.println(systemTicker.read());
    }
}
