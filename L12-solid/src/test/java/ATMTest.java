import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.alekseiterentev.atm.ATM;
import ru.otus.alekseiterentev.atm.BankNoteCell;
import ru.otus.alekseiterentev.atm.BaseATM;
import ru.otus.alekseiterentev.atm.issuer.BaseIssuer;
import ru.otus.alekseiterentev.atm.receiver.BaseReceiver;
import ru.otus.alekseiterentev.atm.banknote.BankNote;
import ru.otus.alekseiterentev.atm.banknote.BankNoteRating;
import ru.otus.alekseiterentev.exceptons.NotEnoughMoneyException;
import ru.otus.alekseiterentev.exceptons.UnableToSplitByExistingBankNotes;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ATMTest {

    private ATM atm;

    @BeforeEach
    void init() {
        BankNote fifty = BankNote.builder().bankNoteRating(BankNoteRating.FIFTY).build();
        BankNote oneHundred = BankNote.builder().bankNoteRating(BankNoteRating.ONE_HUNDRED).build();
        BankNote twoHundred = BankNote.builder().bankNoteRating(BankNoteRating.TWO_HUNDRED).build();
        BankNote fiveHundred = BankNote.builder().bankNoteRating(BankNoteRating.FIVE_HUNDRED).build();
        BankNote oneThousand = BankNote.builder().bankNoteRating(BankNoteRating.ONE_THOUSAND).build();
        BankNote twoThousand = BankNote.builder().bankNoteRating(BankNoteRating.TWO_THOUSAND).build();
        BankNote fiveThousand = BankNote.builder().bankNoteRating(BankNoteRating.FIVE_THOUSAND).build();
        Map<BankNote, BankNoteCell> initialCells = new HashMap<>();
        initialCells.put(fifty, new BankNoteCell(fifty, 1000));
        initialCells.put(oneHundred, new BankNoteCell(oneHundred, 1000));
        initialCells.put(twoHundred, new BankNoteCell(twoHundred, 1000));
        initialCells.put(fiveHundred, new BankNoteCell(fiveHundred, 1000));
        initialCells.put(oneThousand, new BankNoteCell(oneThousand, 1000));
        initialCells.put(twoThousand, new BankNoteCell(twoThousand, 1000));
        initialCells.put(fiveThousand, new BankNoteCell(fiveThousand, 1000));

        this.atm = new BaseATM(initialCells, new BaseIssuer(), new BaseReceiver());
    }

    @Test
    void getBalanceTest() {
        assertThat(atm.getBalance()).isEqualTo(8850000);
    }

    @Test
    void addBankNotesTest() {
        Map<BankNote, Integer> bankNotes = new HashMap<>();
        bankNotes.put(BankNote.builder().bankNoteRating(BankNoteRating.FIFTY).build(), 3);
        bankNotes.put(BankNote.builder().bankNoteRating(BankNoteRating.ONE_HUNDRED).build(), 5);
        bankNotes.put(BankNote.builder().bankNoteRating(BankNoteRating.TWO_HUNDRED).build(), 10);
        bankNotes.put(BankNote.builder().bankNoteRating(BankNoteRating.FIVE_THOUSAND).build(), 1);

        atm.addMoney(bankNotes);

        assertThat(atm.getBalance()).isEqualTo(8857650);
    }

    @Test
    void getMoneyTest() throws UnableToSplitByExistingBankNotes, NotEnoughMoneyException {
        Integer remainingBalance = atm.getBalance();
        Integer requestedValue = 11550;

        Map<BankNote, Integer> receivedBankNotes = atm.getMoney(requestedValue);
        Integer receivedValue = receivedBankNotes.entrySet().stream()
                .mapToInt(bankNote -> bankNote.getKey().getBankNoteRating().getRatingValue() * bankNote.getValue()).sum();

        assertThat(requestedValue).isEqualTo(receivedValue);

        assertThat(atm.getBalance()).isEqualTo(remainingBalance - requestedValue);
    }

    @Test
    void getMoneyWithExceptionTest() {
        assertThatThrownBy( () -> atm.getMoney(8850001))
                .isInstanceOf(NotEnoughMoneyException.class)
                .hasMessage("ATM has not enough money");
    }
}
