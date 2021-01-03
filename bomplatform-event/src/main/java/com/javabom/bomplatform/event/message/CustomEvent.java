package com.javabom.bomplatform.event.message;

import java.util.function.Consumer;

public class CustomEvent implements Event {
    private final Consumer<CustomEvent> consumer;

    public CustomEvent(final Consumer<CustomEvent> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void consume() {
        consumer.accept(this);
    }
}
