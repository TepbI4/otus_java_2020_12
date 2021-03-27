package ru.otus.alekseiterentev.exceptons;

public class UnableToSplitByExistingBankNotes extends Exception {

    public UnableToSplitByExistingBankNotes(String message) {
        super(message);
    }
}
