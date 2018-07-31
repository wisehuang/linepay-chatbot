package com.line.pay.chatbot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LineMessageService {
    private static Logger logger=LogManager.getLogger(LineMessageService.class.getName());

    @Value("${line.channel.id}")
    private String channelId;

    @Value("${line.channel.secret}")
    private String channelSecret;

    public String getChannelSecret() {
        return this.channelSecret;
    }
}
