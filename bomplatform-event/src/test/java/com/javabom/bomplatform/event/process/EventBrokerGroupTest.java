package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventBrokerGroupTest {

    @DisplayName("새로운 Event 타입이 push 되면 Map에 새로 키를 등록한다.")
    @Test
    void pushCreateKey() {
        Event mockEvent = () -> {

        };
        Event mockEvent2 = () -> {

        };

        EventBrokerGroup eventBrokerGroup = new EventBrokerGroup();

        eventBrokerGroup.push(mockEvent);
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(1);

        eventBrokerGroup.push(mockEvent2);
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(2);
    }

    @DisplayName("이미 존재하는 Event 타입이 push 되면 Map에 키를 추가하지 않는다.")
    @Test
    void sameEventDoNotCreateKey() {
        Event mockEvent = new MockEvent();
        Event mockEvent1 = new MockEvent();

        EventBrokerGroup eventBrokerGroup = new EventBrokerGroup();

        eventBrokerGroup.push(mockEvent);
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(1);

        eventBrokerGroup.push(mockEvent1);
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(1);
    }

    @DisplayName("인자로 넘겨준 Event Type에 맞는 Event를 반환한다.")
    @Test
    void pollEvent() {
        Event mockEvent = () -> {
        };
        Event mockEvent2 = () -> {
        };

        EventBrokerGroup eventBrokerGroup = new EventBrokerGroup();

        eventBrokerGroup.push(mockEvent);
        eventBrokerGroup.push(mockEvent2);

        Event expectedMockEvent = eventBrokerGroup.poll(mockEvent.getClass())
                .get();

        assertThat(expectedMockEvent).isEqualTo(mockEvent);

        expectedMockEvent = eventBrokerGroup.poll(mockEvent2.getClass())
                .get();

        assertThat(expectedMockEvent).isEqualTo(mockEvent2);
    }

    @DisplayName("등록되지 않은 EventType으로 찾는다면 Exception throw")
    @Test
    void noEventReturnOptional() {
        EventBrokerGroup eventBrokerGroup = new EventBrokerGroup();

        assertThatThrownBy(() -> eventBrokerGroup.poll(MockEvent.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("처리할 수 없는 이벤트 타입 : " + MockEvent.class.getSimpleName());
    }

    private static class MockEvent implements Event {
        @Override
        public void consume() {

        }
    }
}