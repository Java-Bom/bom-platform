package com.javabom.bomplatform.event.service;

import com.javabom.bomplatform.event.message.Event;

public interface EventService<E extends Event> {
    void provide(E event);

    void consume(E event);
}
