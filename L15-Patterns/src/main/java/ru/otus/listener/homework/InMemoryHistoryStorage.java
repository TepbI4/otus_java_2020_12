package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryHistoryStorage implements HistoryStorage {

    private final List<HistoryRecord> historyRecords = new ArrayList<>();

    @Override
    public void addRecord(HistoryRecord record) {
        historyRecords.add(record);
    }

    public List<HistoryRecord> getHistory() {
        return Collections.unmodifiableList(historyRecords);
    }
}
