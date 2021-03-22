package ru.otus.testengine;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestResultsHolder {

    private List<TestResult> results = new ArrayList<>();

    public void addTestResult(TestResult result) {
        results.add(result);
    }

    public List<TestResult> readTestResults() {
        return results;
    }

    Integer getTotalResults() {
        return results.size();
    }

    Integer getSuccessRate() {
        return results.stream()
                .filter(TestResult::isSuccess)
                .collect(toList())
                .size();
    }

    Integer getFailed() {
        return getTotalResults() - getSuccessRate();
    }
}
