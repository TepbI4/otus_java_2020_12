package ru.otus.alekseiterentev.atm.receiver;

import ru.otus.alekseiterentev.banknote.BankNote;

import java.util.Map;

public interface Receiver {
    void addMoney(Map<BankNote, Integer> bankNoteCells, Map<BankNote, Integer> bankNotes);
}
