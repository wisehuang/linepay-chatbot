package com.line.pay.chatbot.service;

import com.google.gson.Gson;
import com.line.pay.chatbot.events.Event;
import com.line.pay.chatbot.events.Message;
import com.line.pay.chatbot.events.ReplyMessage;
import com.line.pay.chatbot.events.WebhookEvent;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class LineMessageService {
    private static Logger logger=LogManager.getLogger(LineMessageService.class.getName());

    private static final String HASH_ALGORITHM = "HmacSHA256";

    @Autowired
    private DialogFlowService dialogFlowService;

    @Value("${line.channel.id}")
    private String channelId;

    @Value("${line.channel.secret}")
    private String channelSecret;

    @Value("${line.access.token}")
    private String accessToken;

    @Value("${line.api.url}")
    private String apiUrl;

    @Value("${line.api.message.reply.url}")
    private String replyUrl;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String getChannelSecret() {
        return this.channelSecret;
    }

    public void handleWebhookEvent(String bodyStr) {
        var gson = new Gson();

        try {
            var webhookEvent = gson.fromJson(bodyStr, WebhookEvent.class);

            logger.info("body:" + bodyStr);

            for (var event : webhookEvent.getEvents()) {
                logger.info("event type:" + event.getType());

                replyMessage(event);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void replyMessage(Event event) throws Exception{
        if ("message".equals(event.getType())) {

            var msgString = event.getMessage().getText();
            var replyToken = event.getReplyToken();

            logger.info("event content:" + msgString);
            logger.info("reply token:" + replyToken);

            var url = apiUrl + replyUrl;
            var client = new OkHttpClient();

            var replyMsg = new ReplyMessage();
            var msg = new Message();
            replyMsg.setReplyToken(replyToken);
            msg.setText("hello back");
            msg.setType("text");

            replyMsg.setMessage(msg);

            Gson gson = new Gson();
            var json = gson.toJson(replyMsg);

            RequestBody body = RequestBody.create(JSON, json);
            var token = "Bearer " + accessToken;

            Request request = new Request.Builder()
                    .addHeader("Authorization", token)
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            logger.info("Response HTTP Status:" + response.code());

//                    var textList = new ArrayList<String>();
//                    textList.add(event.getMessage().getText());

            //dialogFlowService.detectIntentTexts("linepaydev", textList, event.getReplyToken(), "en-US");
        }
    }

    public boolean isSignatureValid(String signature, byte[] body) {
        var result = false;

        try {
            var sha256HMAC = Mac.getInstance(HASH_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(this.getChannelSecret().getBytes(StandardCharsets.UTF_8), HASH_ALGORITHM);
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
