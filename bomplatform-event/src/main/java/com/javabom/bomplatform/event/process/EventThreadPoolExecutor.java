package com.javabom.bomplatform.event.process;

import com.javabom.bomplatform.event.message.Event;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class EventThreadPoolExecutor {
    private static final int MAX_POOL_SIZE = 5;
    private final Semaphore semaphore;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public EventThreadPoolExecutor(Class<? extends Event> eventType) {
        this.semaphore = new Semaphore(MAX_POOL_SIZE);
        this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setThreadNamePrefix("thread_" + eventType.getSimpleName());
        threadPoolTaskExecutor.setCorePoolSize(2); //수행 풀
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE); // 큐에 추가되면 맥스까지 늘어남
        threadPoolTaskExecutor.setQueueCapacity(3); // 대기할 수 있는 작업 수
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.initialize();
    }

    public void executeJob(Runnable job) {
        try {
            semaphore.acquire();
            threadPoolTaskExecutor.execute(() -> execute(job));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void execute(Runnable job) {
        try {
            job.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
