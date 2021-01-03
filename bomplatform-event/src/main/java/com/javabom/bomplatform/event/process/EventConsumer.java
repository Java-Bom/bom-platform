package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventConsumer {
    private final EventBrokerGroup eventBrokerGroup;
    private final Map<Class<? extends Event>, EventThreadPoolExecutor> eventThreadPoolExecutors = new HashMap<>();

    private boolean running = true;

    public EventConsumer(final EventBrokerGroup eventBrokerGroup) {
        this.eventBrokerGroup = eventBrokerGroup;

        for (Class<? extends Event> eventType : eventBrokerGroup.keySet()) {
            Thread thread = new Thread(() -> consume(eventType));
            thread.setName("Thread_" + eventType.getSimpleName());

            eventThreadPoolExecutors.put(eventType, new EventThreadPoolExecutor(eventType));

            thread.start();
        }
    }

    private void consume(Class<? extends Event> eventType) {
        while (running) {
            Optional<Event> ifExistEvent = eventBrokerGroup.poll(eventType);

            if (!ifExistEvent.isPresent()) {
                continue;
            }

            eventThreadPoolExecutors.get(eventType)
                    .executeJob(() -> ifExistEvent.get().consume());
        }
    }

    public void stop() {
        this.running = false;
    }
}
