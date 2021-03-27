package ru.otus.alekseiterentev.atm.receiver;

import ru.otus.alekseiterentev.atm.BankNoteCell;
import ru.otus.alekseiterentev.atm.banknote.BankNote;

import java.util.Map;

public interface Receiver {
    void addMoney(Map<BankNote, BankNoteCell> bankNoteCells, Map<BankNote, Integer> bankNotes);
}
