package com.org.spring.batch.messaging;

import com.hazelcast.core.IQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessagingServiceImpl implements MessagingService {

    private IQueue<NotificationMessage> messagesQueue;

    public MessagingServiceImpl(IQueue<NotificationMessage> messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    @Override
    public void addMessage(NotificationMessage notificationMessage) {
        log.debug("Adding Notification Message : {}", notificationMessage);
        messagesQueue.add(notificationMessage);
    }
}
