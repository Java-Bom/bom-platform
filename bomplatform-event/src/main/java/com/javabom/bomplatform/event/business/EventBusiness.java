package com.javabom.bomplatform.event.business;

import com.javabom.bomplatform.event.message.Event;

public interface EventBusiness<E extends Event> {
    void provide(E event);

    void consume(E event);
}
