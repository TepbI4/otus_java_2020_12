package ru.otus.alekseiterentev.atm;

import ru.otus.alekseiterentev.banknote.BankNote;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Map;

public interface ATM {

    Integer getBalance();
    void addMoney(Map<BankNote, Integer> bankNotes);
    Map<BankNote, Integer> getMoney(Integer value) throws NotEnoughMoneyException, UnableToSplitByExistingBankNotes;
}
