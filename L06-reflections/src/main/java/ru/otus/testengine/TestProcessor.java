package ru.otus.testengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class TestProcessor {

    private TestProcessor next;
    private Object testClassInstance;
    private List<Method> beforeMethods;
    private List<Method> afterMethods;

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

    void before() throws InvocationTargetException, IllegalAccessException {
        for (Method beforeMethod : beforeMethods) {
            beforeMethod.invoke(testClassInstance);
        }
    }

    void after() throws InvocationTargetException, IllegalAccessException {
        for (Method afterMethod : afterMethods) {
            afterMethod.invoke(testClassInstance);
        }
    }

    void process(TestResultsHolder resultsHolder) {
        Boolean isSuccess = Boolean.TRUE;
        try {
            before();
            processInternal(resultsHolder);
        } catch (InvocationTargetException | IllegalAccessException e) {
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if (targetException instanceof AssertionError) {
                    System.err.println(targetException.getMessage());
                }
            }
            isSuccess = Boolean.FALSE;
        } finally {
            try {
                after();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                isSuccess = Boolean.FALSE;
            }
            resultsHolder.addTestResult(new TestResult(isSuccess));
            if (getNext() != null) {
                getNext().process(resultsHolder);
            }
        }
    }

    abstract void processInternal(TestResultsHolder resultsHolder) throws InvocationTargetException, IllegalAccessException;
}
