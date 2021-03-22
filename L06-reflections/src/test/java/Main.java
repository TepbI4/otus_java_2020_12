import ru.otus.testengine.TestExecutor;

public class Main {
    public static void main(String[] args) throws Exception {

        Class testClass = Class.forName("MyTest");

        TestExecutor testExecutor = new TestExecutor(testClass);
        testExecutor.execute();

        System.out.println();
    }
}
