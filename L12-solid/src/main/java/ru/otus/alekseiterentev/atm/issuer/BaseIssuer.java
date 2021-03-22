package ru.otus.alekseiterentev.atm.issuer;

import ru.otus.alekseiterentev.banknote.BankNote;
import ru.otus.alekseiterentev.banknote.BankNoteRating;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseIssuer implements Issuer {

    @Override
    public Integer getBalance(Map<BankNote, Integer> bankNoteCells) {
        return bankNoteCells.entrySet().stream()
                .mapToInt(cell -> cell.getKey().getBankNoteRating().getRatingValue() * cell.getValue()).sum();
    }

    @Override
    public Map<BankNote, Integer> issueMoney(Map<BankNote, Integer> bankNoteCells, Integer issuedValue) throws UnableToSplitByExistingBankNotes, NotEnoughMoneyException {
        if (issuedValue > getBalance(bankNoteCells)) {
            throw new NotEnoughMoneyException("ATM has not enough money");
        }

        Map<BankNote, Integer> bankNotes = split(bankNoteCells, issuedValue);

        for (Map.Entry<BankNote, Integer> bankNote : bankNotes.entrySet()) {
            Integer remaining = bankNoteCells.get(bankNote.getKey());
            remaining = remaining - bankNote.getValue();
            if (remaining > 0) {
                bankNoteCells.put(bankNote.getKey(), remaining);
            } else {
                bankNotes.remove(bankNote.getKey());
            }
        }

        return bankNotes;
    }

    private Map<BankNote, Integer> split(Map<BankNote, Integer> bankNoteCells, Integer value) throws UnableToSplitByExistingBankNotes {
        Map<BankNote, Integer> bankNotes = new HashMap<BankNote, Integer>();

        List<BankNoteRating> bankNoteRatings = Arrays.asList(BankNoteRating.values());
        bankNoteRatings.sort(Comparator.comparingInt(BankNoteRating::getRatingValue).reversed());

        Integer mod = value;

        for (BankNoteRating bankNoteRating : bankNoteRatings) {
            Integer div = mod/bankNoteRating.getRatingValue();

            if (div == 0) {
                mod = mod % bankNoteRating.getRatingValue();
                continue;
            }

            BankNote bankNote = new BankNote(bankNoteRating);
            if (!bankNoteCells.containsKey(bankNote)) {
                continue;
            }

            Integer cellRemaining = bankNoteCells.get(bankNote);
            if (cellRemaining < div) {
                bankNotes.put(bankNote, cellRemaining);
                div = div - cellRemaining;
                mod = mod % bankNoteRating.getRatingValue() + div * bankNoteRating.getRatingValue();
                continue;
            }

            bankNotes.put(bankNote, div);
            mod = mod % bankNoteRating.getRatingValue();
        }

        if (mod != 0) {
            throw new UnableToSplitByExistingBankNotes("ATM unable to split requested sum by existing bank notes");
        }

        return bankNotes;
    }
}
