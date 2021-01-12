package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class EventBrokerGroup {
    private final Map<Class<? extends Event>, EventBroker<? extends Event>> brokers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <E extends Event> void push(E event) {
        EventBroker<E> broker = (EventBroker<E>) brokers.computeIfAbsent(event.getClass(), (key) -> new EventBroker<E>());

        broker.push(event);
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> Optional<E> poll(Class<? extends Event> eventType) {
        EventBroker<E> eventBroker = (EventBroker<E>) brokers.get(eventType);
        return Optional.ofNullable(eventBroker.poll());
    }

    public Set<Class<? extends Event>> keySet() {
        return brokers.keySet();
    }
}
