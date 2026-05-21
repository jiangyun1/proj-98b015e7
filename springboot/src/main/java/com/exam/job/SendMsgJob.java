package com.exam.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SendMsgJob {
    /*
     *
     * @Autowired private JmsMessagingTemplate jmsMessageTemplate;
     *
     * @Autowired private Queue msgQueue;
     *
     * @Scheduled(fixedDelay = 5000) public void sendFltMsgByQueue() {
     * this.jmsMessageTemplate.convertAndSend(this.msgQueue, "sjdfisdhfjs");
     *
     * }
     */
}