package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class EventBrokerGroup {
    public static final int EVENT_CAPACITY = 100;
    private final Map<Class<? extends Event>, EventBroker<? extends Event>> brokers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <E extends Event> void push(E event) {
        EventBroker<E> broker = (EventBroker<E>) brokers.computeIfAbsent(event.getClass(), (key) -> new EventBroker<E>(EVENT_CAPACITY));

        broker.push(event);
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> Optional<E> poll(Class<? extends Event> eventType) {
        EventBroker<E> eventBroker = (EventBroker<E>) brokers.get(eventType);

        if (Objects.isNull(eventBroker)) {
            throw new IllegalStateException("처리할 수 없는 이벤트 타입 : " + eventType.getSimpleName());
        }

        return Optional.ofNullable(eventBroker.poll());
    }

    public Set<Class<? extends Event>> keySet() {
        return brokers.keySet();
    }
}
