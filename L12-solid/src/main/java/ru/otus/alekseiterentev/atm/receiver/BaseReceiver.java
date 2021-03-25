package ru.otus.alekseiterentev.atm.receiver;

import ru.otus.alekseiterentev.atm.BankNoteCell;
import ru.otus.alekseiterentev.atm.banknote.BankNote;

import java.util.Map;

public class BaseReceiver implements Receiver {

    @Override
    public void addMoney(Map<BankNote, BankNoteCell> bankNoteCells, Map<BankNote, Integer> bankNotes) {
        if (!bankNotes.isEmpty()) {
            for (Map.Entry<BankNote, Integer> bankNote : bankNotes.entrySet()) {
                if (bankNoteCells.containsKey(bankNote.getKey())) {
                    bankNoteCells.get(bankNote.getKey()).addMoney(bankNote.getValue());
                }
            }
        }
    }
}
