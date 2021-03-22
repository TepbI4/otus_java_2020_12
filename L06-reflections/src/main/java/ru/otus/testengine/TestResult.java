package ru.otus.testengine;

public class TestResult {

    private final boolean isSuccess;

    public TestResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    boolean isSuccess() {
        return isSuccess;
    }
}
