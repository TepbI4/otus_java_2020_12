package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Clock;
import java.time.LocalTime;

public class ClockTrowsExceptionProcessor implements Processor {

    private final Clock clock;

    public ClockTrowsExceptionProcessor(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Message process(Message message) {
        if (LocalTime.now(clock).getSecond() % 2 == 0) {
            throw new RuntimeException("Current second is even");
        }
        return message;
    }
}
