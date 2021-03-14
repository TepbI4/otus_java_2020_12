package ru.otus.testengine;

public class TestResult {

    Boolean isSuccess;

    public TestResult(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    Boolean isSuccess() {
        return isSuccess;
    }
}
