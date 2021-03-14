import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

public class MyTest {

    @Before
    void beforeTest1() {
        System.out.println("beforeTest1 executed" + this);
    }

    @Before
    void beforeTest2() {
        System.out.println("beforeTest2 executed" + this);
    }

    @Test
    void multiplicationTest() {
        System.out.println("multiplicationTest execution" + this);
        assertThat(2*2).isEqualTo(4);
    }

    @Test
    void errorTest() {
        System.out.println("errorTest execution" + this);
        assertThat(TRUE).isEqualTo(FALSE);
    }

    @After
    void closeTest() {
        System.out.println("closeTest execution" + this);
    }
}
