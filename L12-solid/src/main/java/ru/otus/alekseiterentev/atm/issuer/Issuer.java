package ru.otus.alekseiterentev.atm.issuer;

import ru.otus.alekseiterentev.banknote.BankNote;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Map;

public interface Issuer {
    Integer getBalance(Map<BankNote, Integer> bankNoteCells);
    Map<BankNote,Integer> issueMoney(Map<BankNote, Integer> bankNoteCells, Integer issuedValue) throws UnableToSplitByExistingBankNotes, NotEnoughMoneyException;
}
