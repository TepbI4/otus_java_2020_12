package ru.otus.alekseiterentev.atm.receiver;

import ru.otus.alekseiterentev.banknote.BankNote;

import java.util.Map;

public class BaseReceiver implements Receiver {

    @Override
    public void addMoney(Map<BankNote, Integer> bankNoteCells, Map<BankNote, Integer> bankNotes) {
        if (!bankNotes.isEmpty()) {
            for (Map.Entry<BankNote, Integer> bankNote : bankNotes.entrySet()) {
                if (bankNoteCells.containsKey(bankNote.getKey())) {
                    Integer currentValue = bankNoteCells.get(bankNote.getKey());
                    bankNoteCells.put(bankNote.getKey(), currentValue + bankNote.getValue());
                }
            }
        }
    }
}
