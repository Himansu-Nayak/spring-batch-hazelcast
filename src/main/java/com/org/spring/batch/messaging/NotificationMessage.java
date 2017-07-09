package com.org.spring.batch.messaging;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class NotificationMessage implements Serializable {

    private String eventKind;
    private String fileName;

    public NotificationMessage(String eventKind, String fileName) {
        this.eventKind = eventKind;
        this.fileName = fileName;
    }

}