package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EventBrokerTest {

    @DisplayName("capacity 초과시 Exception throw")
    @Test
    void maxCapacityThrowException() {
        final int capacity = 10;
        EventBroker<TestEvent> eventBroker = new EventBroker<>(capacity);

        for (int i = 0; i < 10; i++) {
            eventBroker.push(new TestEvent());
        }

        assertThatThrownBy(() -> eventBroker.push(new TestEvent()))
                .isInstanceOf(IllegalStateException.class);
    }

    //TODO : poll() 테스트는 어떻게 할까

    static class TestEvent implements Event {
        @Override
        public void consume() {

        }
    }
}