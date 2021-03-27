package ru.otus.alekseiterentev.atm.issuer;

import ru.otus.alekseiterentev.atm.BankNoteCell;
import ru.otus.alekseiterentev.atm.banknote.BankNote;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Collection;
import java.util.Map;

public interface Issuer {
    Integer getBalance(Collection<BankNoteCell> bankNoteCells);
    Map<BankNote,Integer> issueMoney(Map<BankNote, BankNoteCell> bankNoteCells, int issuedValue) throws UnableToSplitByExistingBankNotes, NotEnoughMoneyException;
}
