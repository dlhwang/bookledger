package com.test.global.model;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher eventPublisher;

    static void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        Events.eventPublisher = eventPublisher;
    }

    public static void publishEvent(Object event) {
        if (eventPublisher != null) {
            eventPublisher.publishEvent(event);
        }
    }
}
