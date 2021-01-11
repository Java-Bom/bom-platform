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
        createTypeWatcher();
    }

    private void createTypeWatcher() {
        Thread thread = new Thread(this::watch);
        thread.start();
    }

    private void watch() {
        while (true) {
            if (eventBrokerGroup.keySet().size() != eventThreadPoolExecutors.size()) {
                fillEventThreadPoolExecutors();
            }
        }
    }

    private void fillEventThreadPoolExecutors() {
        for (Class<? extends Event> eventType : eventBrokerGroup.keySet()) {

            eventThreadPoolExecutors.computeIfAbsent(eventType, (k) -> {
                Thread thread = new Thread(() -> consume(eventType));
                thread.setName("Thread_" + eventType.getSimpleName());
                thread.start();
                return new EventThreadPoolExecutor(eventType);
            });

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
