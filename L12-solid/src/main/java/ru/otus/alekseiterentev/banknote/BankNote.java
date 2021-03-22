package ru.otus.alekseiterentev.banknote;

import java.util.Objects;

public class BankNote {

    private BankNoteRating bankNoteRating;

    public BankNote(BankNoteRating bankNoteRating) {
        this.bankNoteRating = bankNoteRating;
    }

    public BankNoteRating getBankNoteRating() {
        return bankNoteRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankNote)) return false;
        BankNote bankNote = (BankNote) o;
        return bankNoteRating == bankNote.bankNoteRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankNoteRating);
    }
}
