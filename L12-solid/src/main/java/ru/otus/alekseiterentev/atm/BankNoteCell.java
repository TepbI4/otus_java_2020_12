package ru.otus.alekseiterentev.atm;

import ru.otus.alekseiterentev.atm.banknote.BankNote;

public class BankNoteCell {

    private final BankNote bankNote;
    private Integer banknotesCount;

    public BankNoteCell(BankNote bankNote, Integer banknotesCount) {
        this.bankNote = bankNote;
        this.banknotesCount = banknotesCount;
    }

    public Integer getBanknotesCount() {
        return banknotesCount;
    }

    public Integer getCellBalance() {
        return banknotesCount * bankNote.getBankNoteRatingValue();
    }

    public void issueMoney(Integer value) {
        banknotesCount -= value;
    }

    public void addMoney(Integer value) {
        banknotesCount += value;
    }
}
