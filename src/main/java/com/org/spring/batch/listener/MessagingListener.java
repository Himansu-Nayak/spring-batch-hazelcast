package com.org.spring.batch.listener;

import com.hazelcast.core.IQueue;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import com.org.spring.batch.messaging.MessagingService;
import com.org.spring.batch.messaging.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Messaging listener.
 */
@Component
@Slf4j
public class MessagingListener implements ItemListener {

    private IQueue<NotificationMessage> messagesQueue;
    private MessagingService messagingService;

    public MessagingListener(IQueue messagesQueue, MessagingService messagingService) {
        this.messagingService = messagingService;

        messagesQueue.addItemListener(this, true);

    }

    private void notificationMessageAdded(NotificationMessage notificationMessage) {
        log.debug("Message Added : {}", notificationMessage);

        if (notificationMessage == null) {
            log.debug("NotificationMessage is null. It should not happen.");
            return;
        }
    }

    @Override
    public void itemAdded(ItemEvent item) {
        if (item.getItem().getClass().isAssignableFrom(NotificationMessage.class))
            notificationMessageAdded((NotificationMessage) item.getItem());
    }

    @Override
    public void itemRemoved(ItemEvent item) {

    }

}