package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventBroker<E extends Event> {
    private final BlockingQueue<E> eventQueue = new LinkedBlockingQueue<>();

    public void push(E event) {
        eventQueue.add(event);
    }

    public E poll() {
        return eventQueue.poll();
    }
}
