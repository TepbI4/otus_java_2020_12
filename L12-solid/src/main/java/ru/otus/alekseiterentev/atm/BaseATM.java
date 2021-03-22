package ru.otus.alekseiterentev.atm;

import ru.otus.alekseiterentev.atm.issuer.Issuer;
import ru.otus.alekseiterentev.atm.receiver.Receiver;
import ru.otus.alekseiterentev.banknote.BankNote;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Map;

public class BaseATM implements ATM {

    private Map<BankNote, Integer> bankNoteCells;

    private Issuer issuer;

    private Receiver receiver;

    public BaseATM(Map<BankNote, Integer> bankNoteCells, Issuer issuer, Receiver receiver) {
        this.bankNoteCells = bankNoteCells;
        this.issuer = issuer;
        this.receiver = receiver;
    }

    public Integer getBalance() {
        return issuer.getBalance(bankNoteCells);
    }

    public void addMoney(Map<BankNote, Integer> bankNotes) {
        receiver.addMoney(bankNoteCells, bankNotes);
    }

    public Map<BankNote, Integer> getMoney(Integer value) throws NotEnoughMoneyException, UnableToSplitByExistingBankNotes {
        return issuer.issueMoney(bankNoteCells, value);
    }


}
