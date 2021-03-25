package ru.otus.alekseiterentev.atm.banknote;

import java.util.Objects;

public class BankNote {

    private BankNoteRating bankNoteRating;

    private BankNote(BankNoteRating bankNoteRating) {
        this.bankNoteRating = bankNoteRating;
    }

    public BankNoteRating getBankNoteRating() {
        return bankNoteRating;
    }

    public int getBankNoteRatingValue() {
        return bankNoteRating.getRatingValue();
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

    public static BankNoteBuilder builder() {
        return new BankNoteBuilder();
    }

    public static class BankNoteBuilder {

        private BankNoteRating bankNoteRating;

        public BankNoteBuilder() {
        }

        public BankNoteBuilder bankNoteRating(BankNoteRating bankNoteRating) {
            this.bankNoteRating = bankNoteRating;
            return this;
        }

        public BankNote build() {
            return new BankNote(bankNoteRating);
        }
    }
}
