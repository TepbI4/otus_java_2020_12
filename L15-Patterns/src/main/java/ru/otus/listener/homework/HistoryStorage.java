package ru.otus.listener.homework;

import java.util.List;

public interface HistoryStorage {

    void addRecord(HistoryRecord record);
    List<HistoryRecord> getHistory();
}
