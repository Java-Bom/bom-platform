package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventConsumerTest {

    private EventConsumer eventConsumer;

    @AfterEach
    void tearDown() {
        eventConsumer.stop();
        eventConsumer = null;
    }

    @DisplayName("EventConsumer가 EventBrokerGroup에서 event를 가져와 소비한다.")
    @Test
    void consumeEvent() throws InterruptedException {
        //given
        CountDownLatch latch = new CountDownLatch(4);

        Map<String, Integer> hitMap = hitMap();

        KakaoEvent kakaoEvent1 = new KakaoEvent(kakaoConsumer(hitMap, latch));
        KakaoEvent kakaoEvent2 = new KakaoEvent(kakaoConsumer(hitMap, latch));

        LineEvent lineEvent1 = new LineEvent(lineConsumer(hitMap, latch));
        LineEvent lineEvent2 = new LineEvent(lineConsumer(hitMap, latch));

        EventBrokerGroup eventBrokerGroup = new EventBrokerGroup();

        eventBrokerGroup.push(kakaoEvent1);
        eventBrokerGroup.push(kakaoEvent2);
        eventBrokerGroup.push(lineEvent1);
        eventBrokerGroup.push(lineEvent2);

        //when
        eventConsumer = new EventConsumer(eventBrokerGroup);
        latch.await();

        //then
        assertThat(hitMap.get(KakaoEvent.class.getSimpleName())).isEqualTo(2);
        assertThat(hitMap.get(LineEvent.class.getSimpleName())).isEqualTo(2);
    }

    private Map<String, Integer> hitMap() {
        Map<String, Integer> hitMap = new HashMap<>();

        hitMap.put(KakaoEvent.class.getSimpleName(), 0);
        hitMap.put(LineEvent.class.getSimpleName(), 0);

        return hitMap;
    }

    private Consumer<KakaoEvent> kakaoConsumer(Map<String, Integer> hitMap, CountDownLatch latch) {
        return (kakaoEvent) -> {
            System.out.println(Thread.currentThread().getName() + " - consume kakaoEvent");
            int count = hitMap.get(kakaoEvent.getClass().getSimpleName());

            hitMap.put(kakaoEvent.getClass().getSimpleName(), count + 1);

            latch.countDown();
        };
    }

    private Consumer<LineEvent> lineConsumer(Map<String, Integer> hitMap, CountDownLatch latch) {
        return (lineEvent) -> {
            System.out.println(Thread.currentThread().getName() + " - consume lineEvent");
            int count = hitMap.get(lineEvent.getClass().getSimpleName());

            hitMap.put(lineEvent.getClass().getSimpleName(), count + 1);

            latch.countDown();
        };
    }


    private static class KakaoEvent implements Event {
        private final Consumer<KakaoEvent> consumer;

        public KakaoEvent(final Consumer<KakaoEvent> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void consume() {
            consumer.accept(this);
        }
    }

    private static class LineEvent implements Event {
        private final Consumer<LineEvent> consumer;

        public LineEvent(final Consumer<LineEvent> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void consume() {
            consumer.accept(this);
        }
    }
}