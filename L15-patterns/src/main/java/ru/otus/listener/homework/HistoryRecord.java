package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class HistoryRecord {

    private final Message oldMsg;
    private final Message newMsg;
    private final LocalDateTime createdAt;

    public HistoryRecord(Message oldMsg, Message newMsg, LocalDateTime createdAt) {
        this.oldMsg = oldMsg;
        this.newMsg = newMsg;
        this.createdAt = createdAt;
    }

    Message getOldMsg() {
        return oldMsg;
    }
}
