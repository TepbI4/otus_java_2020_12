package ru.otus.testengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class TestProcessor {

    private TestProcessor next;
    private boolean isSuccess = true;
    private final Object testClassInstance;
    private final List<Method> beforeMethods;
    private final List<Method> afterMethods;

    public TestProcessor(Object testClassInstance, List<Method> beforeMethods, List<Method> afterMethods) {
        this.beforeMethods = beforeMethods;
        this.afterMethods = afterMethods;
        this.testClassInstance = testClassInstance;
    }

    public void setNext(TestProcessor testProcessor) {
        next = testProcessor;
    }

    public TestProcessor getNext() {
        return next;
    }

    public Object getTestClassInstance() {
        return testClassInstance;
    }

    void processBefore() throws InvocationTargetException, IllegalAccessException {
        for (Method beforeMethod : beforeMethods) {
            beforeMethod.invoke(testClassInstance);
        }
    }

    void processAfter() throws InvocationTargetException, IllegalAccessException {
        for (Method afterMethod : afterMethods) {
            afterMethod.invoke(testClassInstance);
        }
    }

    void process(TestResultsHolder resultsHolder) {
        try {
            processBefore();
            processInternal(resultsHolder);
        } catch (InvocationTargetException | IllegalAccessException e) {
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if (targetException instanceof AssertionError) {
                    System.err.println(targetException.getMessage());
                }
            }
            isSuccess = false;
        } finally {
            terminateTest();
            resultsHolder.addTestResult(new TestResult(isSuccess));
            if (getNext() != null) {
                getNext().process(resultsHolder);
            }
        }
    }

    private void terminateTest() {
        try {
            processAfter();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            isSuccess = false;
        }
    }

    abstract void processInternal(TestResultsHolder resultsHolder) throws InvocationTargetException, IllegalAccessException;
}
