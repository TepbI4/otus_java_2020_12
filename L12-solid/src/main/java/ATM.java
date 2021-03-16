import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ATM {

    private Map<BankNote, Integer> bankNoteCells;

    public ATM(Map<BankNote, Integer> bankNoteCells) {
        this.bankNoteCells = bankNoteCells;
    }

    public Integer getBalance() {
        return bankNoteCells.entrySet().stream()
                .mapToInt(cell -> cell.getKey().getBankNoteRating().getRatingValue() * cell.getValue()).sum();
    }

    public void addMoney(Map<BankNote, Integer> bankNotes) {
        if (!bankNotes.isEmpty()) {
            for (Entry<BankNote, Integer> bankNote : bankNotes.entrySet()) {
                if (bankNoteCells.containsKey(bankNote.getKey())) {
                    Integer currentValue = bankNoteCells.get(bankNote.getKey());
                    bankNoteCells.put(bankNote.getKey(), currentValue + bankNote.getValue());
                }
            }
        }
    }

    public Map<BankNote, Integer> getMoney(Integer value) throws NotEnoughMoneyException, UnableToSplitByExistingBankNotes {
        if (value > getBalance()) {
            throw new NotEnoughMoneyException("ATM has not enough money");
        }

        Map<BankNote, Integer> bankNotes = split(value);

        for (Entry<BankNote, Integer> bankNote : bankNotes.entrySet()) {
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

    private Map<BankNote, Integer> split(Integer value) throws UnableToSplitByExistingBankNotes {
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
