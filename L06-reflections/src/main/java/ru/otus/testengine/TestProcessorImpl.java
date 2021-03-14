package ru.otus.testengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestProcessorImpl extends TestProcessor {

    private Method testMethod;

    public TestProcessorImpl(Object testClassInstance, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        super(testClassInstance, beforeMethods, afterMethods);
        this.testMethod = testMethod;
    }

    @Override
    void processInternal(TestResultsHolder resultsHolder) throws InvocationTargetException, IllegalAccessException {
        testMethod.invoke(getTestClassInstance());
    }
}
