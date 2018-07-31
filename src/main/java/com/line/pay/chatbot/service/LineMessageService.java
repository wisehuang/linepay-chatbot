package com.line.pay.chatbot.service;

import com.google.gson.Gson;
import com.line.pay.chatbot.events.WebhookEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class LineMessageService {
    private static Logger logger=LogManager.getLogger(LineMessageService.class.getName());


    private static final String HASH_ALGORITHM = "HmacSHA256";

    @Value("${line.channel.id}")
    private String channelId;

    @Value("${line.channel.secret}")
    private String channelSecret;

    public String getChannelSecret() {
        return this.channelSecret;
    }

    public void handleWebhookEvent(String bodyStr) {
        var gson = new Gson();
        var webhookEvent = gson.fromJson(bodyStr, WebhookEvent.class);

        logger.info("body:" + bodyStr);

        for (var event : webhookEvent.getEvents()) {
            logger.info("event type:" + event.getType());

            if("message".equals(event.getType())) {
                logger.info("event content:" + event.getMessage().getText());
                logger.info("reply token:" + event.getReplyToken());
            }
        }
    }

    public void replyMessage(String msg) {

    }

    public boolean isSignatureValid(String signature, byte[] body) {
        var result = false;

        try {
            var sha256HMAC = Mac.getInstance(HASH_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(this.getChannelSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secretKey);

            final byte[] headerSignature = Base64.getDecoder().decode(signature);
            final byte[] bodySignature = sha256HMAC.doFinal(body);

            logger.info("headerSignature = " + headerSignature);
            logger.info("bodySignature = " + bodySignature);

            if(MessageDigest.isEqual(headerSignature, bodySignature)) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            return result;
        }
    }
}
