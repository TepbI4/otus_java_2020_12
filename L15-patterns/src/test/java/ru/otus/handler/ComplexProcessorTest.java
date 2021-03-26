package ru.otus.handler;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ClockTrowsExceptionProcessor;
import ru.otus.processor.homework.SwapFieldsProcessor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener).onUpdated(message, message);
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }

    @Test
    @DisplayName("Тестируем смену полей местами")
    void swapFieldsTest() {
        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        var complexProcessor = new ComplexProcessor(List.of(new SwapFieldsProcessor()), ex -> {
        });

        Message afterHandle = complexProcessor.handle(message);
        assertThat(afterHandle.getField11()).isEqualTo(message.getField12());
        assertThat(afterHandle.getField12()).isEqualTo(message.getField11());
    }

    @Test
    @DisplayName("Выбрасывает исключение, если секунда на момент запуска, четная (через Clock)")
    void shouldThrowExceptionIfCurrentSecondIsEvenClock() {
        var message = new Message.Builder(1L).build();

        var oddClock = Clock.fixed(
                Instant.parse("2021-04-06T19:15:01.000Z"),
                ZoneId.systemDefault()
        );
        var complexProcessorOdd = new ComplexProcessor(List.of(new ClockTrowsExceptionProcessor(oddClock)), (ex) -> {
            throw new TestException(ex.getMessage());
        });

        assertThatCode(() -> complexProcessorOdd.handle(message)).doesNotThrowAnyException();

        var evenClock = Clock.fixed(
                Instant.parse("2021-04-06T19:15:02.000Z"),
                ZoneId.systemDefault()
        );
        var complexProcessorEven = new ComplexProcessor(List.of(new ClockTrowsExceptionProcessor(evenClock)), (ex) -> {
            throw new TestException(ex.getMessage());
        });

        assertThatThrownBy(() -> complexProcessorEven.handle(message)).hasMessage("Current second is even");
    }
}