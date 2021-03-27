package ru.otus.alekseiterentev.atm;

import ru.otus.alekseiterentev.atm.issuer.Issuer;
import ru.otus.alekseiterentev.atm.receiver.Receiver;
import ru.otus.alekseiterentev.atm.banknote.BankNote;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Map;

public class BaseATM implements ATM {

    private Map<BankNote, BankNoteCell> bankNoteCells;

    private Issuer issuer;

    private Receiver receiver;

    public BaseATM(Map<BankNote, BankNoteCell> bankNoteCells, Issuer issuer, Receiver receiver) {
        this.bankNoteCells = bankNoteCells;
        this.issuer = issuer;
        this.receiver = receiver;
    }

    public Integer getBalance() {
        return issuer.getBalance(bankNoteCells.values());
    }

    public void addMoney(Map<BankNote, Integer> bankNotes) {
        receiver.addMoney(bankNoteCells, bankNotes);
    }

    public Map<BankNote, Integer> getMoney(Integer value) throws NotEnoughMoneyException, UnableToSplitByExistingBankNotes {
        return issuer.issueMoney(bankNoteCells, value);
    }
}
