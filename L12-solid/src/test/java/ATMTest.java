import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.alekseiterentev.atm.ATM;
import ru.otus.alekseiterentev.atm.BaseATM;
import ru.otus.alekseiterentev.atm.issuer.BaseIssuer;
import ru.otus.alekseiterentev.atm.receiver.BaseReceiver;
import ru.otus.alekseiterentev.banknote.BankNote;
import ru.otus.alekseiterentev.banknote.BankNoteRating;
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
        Map<BankNote, Integer> initialCells = new HashMap<>();
        initialCells.put(new BankNote(BankNoteRating.FIFTY), 1000);
        initialCells.put(new BankNote(BankNoteRating.ONE_HUNDRED), 1000);
        initialCells.put(new BankNote(BankNoteRating.TWO_HUNDRED), 1000);
        initialCells.put(new BankNote(BankNoteRating.FIVE_HUNDRED), 1000);
        initialCells.put(new BankNote(BankNoteRating.ONE_THOUSAND), 1000);
        initialCells.put(new BankNote(BankNoteRating.TWO_THOUSAND), 1000);
        initialCells.put(new BankNote(BankNoteRating.FIVE_THOUSAND), 1000);

        this.atm = new BaseATM(initialCells, new BaseIssuer(), new BaseReceiver());
    }

    @Test
    void getBalanceTest() {
        assertThat(atm.getBalance()).isEqualTo(8850000);
    }

    @Test
    void addBankNotesTest() {
        Map<BankNote, Integer> bankNotes = new HashMap<>();
        bankNotes.put(new BankNote(BankNoteRating.FIFTY), 3);
        bankNotes.put(new BankNote(BankNoteRating.ONE_HUNDRED), 5);
        bankNotes.put(new BankNote(BankNoteRating.TWO_HUNDRED), 10);
        bankNotes.put(new BankNote(BankNoteRating.FIVE_THOUSAND), 1);

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
