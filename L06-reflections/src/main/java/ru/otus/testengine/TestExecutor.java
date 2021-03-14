package ru.otus.testengine;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestExecutor {

    Class testClass;

    public TestExecutor(Class testClass) {
        this.testClass = testClass;
    }

    public void execute() throws Exception {
        List<Method> beforeMethods = getAnnotatedMethods(testClass, Before.class);
        List<Method> testMethods = getAnnotatedMethods(testClass, Test.class);
        List<Method> afterMethods = getAnnotatedMethods(testClass, After.class);

        TestProcessor firstTest = null;
        TestProcessor previousTest = null;

        for (Method testMethod : testMethods) {
            Object testInstance = testClass.getDeclaredConstructor().newInstance();
            if (firstTest == null) {
                firstTest = new TestProcessorImpl(testInstance, testMethod, beforeMethods, afterMethods);
                previousTest = firstTest;
            } else {
                TestProcessor test = new TestProcessorImpl(testInstance, testMethod, beforeMethods, afterMethods);
                previousTest.setNext(test);
            }
        }

        TestResultsHolder resultsHolder = new TestResultsHolder();

        firstTest.process(resultsHolder);

        printResults(resultsHolder);
        System.out.println();
    }

    static List<Method> getAnnotatedMethods(Class clazz, Class<? extends Annotation> annotationType) {
        List<Method> annotatedMethods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getDeclaredAnnotation(annotationType) != null)
                .collect(toList());

        annotatedMethods.forEach(method -> method.setAccessible(true));

        return annotatedMethods;
    }

    void printResults(TestResultsHolder resultsHolder) {
        System.out.println("Total tests: " + resultsHolder.getTotalResults());
        System.out.println("Successful tests: " + resultsHolder.getSuccessRate());
        System.out.println("Failed tests: " + resultsHolder.getFailed());
    }
}
