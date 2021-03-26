package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final HistoryStorage historyStorage;

    public HistoryListener(HistoryStorage historyStorage) {
        this.historyStorage = historyStorage;
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        historyStorage.addRecord(new HistoryRecord(oldMsg.copy(), newMsg.copy(), LocalDateTime.now()));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return historyStorage.getHistory().stream()
                .filter(historyRecord -> historyRecord.getOldMsg().getId() == id)
                .map(HistoryRecord::getOldMsg).findAny();
    }
}
