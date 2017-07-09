package com.org.spring.batch.watcher;

import com.org.spring.batch.messaging.MessagingService;
import com.org.spring.batch.messaging.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Service
@Slf4j
public class WatcherService implements Runnable {

    private WatchService watchService;

    @Autowired
    private MessagingService messagingService;

    @Override
    public void run() {

        while (true) {
            log.info("Running watch service");
            try {
                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        log.info(
                                "Event kind:" + event.kind()
                                        + ". File affected: " + event.context() + ".");
                        NotificationMessage notificationMessage = new NotificationMessage(event.kind().toString(), event
                                .context()
                                .toString());
                        messagingService.addMessage(notificationMessage);
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}