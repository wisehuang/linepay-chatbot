package com.line.pay.chatbot.service;

import com.google.gson.Gson;
import com.line.pay.chatbot.payment.ConfirmRequest;
import com.line.pay.chatbot.payment.ConfirmResponse;
import com.line.pay.chatbot.payment.ReserveRequest;
import com.line.pay.chatbot.payment.ReserveResponse;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LinePayService {
    private static Logger logger= LogManager.getLogger(LineMessageService.class.getName());

    @Value("${line.pay.channel.id}")
    private String channelId;

    @Value("${line.pay.channel.secret}")
    private String channelSecret;

    @Value("${line.pay.api.url}")
    private String payApiUrl;

    @Value("${line.pay.api.reserve.url}")
    private String payApiReserveUrl;

    @Value("${line.pay.confirm.url}")
    private String confirmUrl;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ReserveResponse invokeReserve(long amount, String userId) {
        ReserveRequest reserveRequest = getReserveRequest(amount, userId);

        var gson = new Gson();

        var json = gson.toJson(reserveRequest);
        logger.info("Reserve request:" + json);

        ReserveResponse reserveResponse = getReserveResponse(gson, json);

        return reserveResponse;
    }

    public void invokeConfirm(long transactionId, int amount) throws Exception{

        var url = payApiUrl + "/v2/payments/" + transactionId + "/confirm";

        logger.info("confirm API:" + url);

        var client = new OkHttpClient();

        var confirmRequest = new ConfirmRequest();

        confirmRequest.setAmount(amount);
        confirmRequest.setCurrency("TWD");

        var gson = new Gson();
        var json = gson.toJson(confirmRequest);

        logger.info("confirm request:" + json);

        var body = RequestBody.create(JSON, json);

        Request request = buildLinePayRequest(body, url);
        Response response = client.newCall(request).execute();

        logger.info("Response HTTP Status:" + response.code());

        var responseBody = response.body().string();

        logger.info("Response Body:" + responseBody);

        response.close();

        //var confirmResponse = gson.fromJson(responseBody, ConfirmResponse.class);
    }

    public ReserveRequest getReserveRequest(long amount, String userId) {
        var reserveRequest = new ReserveRequest();

        var orderId = UUID.randomUUID().toString();

        reserveRequest.setAmount(amount);
        reserveRequest.setCapture("true");
        reserveRequest.setCheckConfirmUrlBrowser("false");
        reserveRequest.setConfirmUrl(confirmUrl + "?amount=" + amount + "&userId=" + userId);
        reserveRequest.setConfirmUrlType("CLIENT");
        reserveRequest.setCurrency("TWD");
        reserveRequest.setOrderId(orderId);
        reserveRequest.setPayType("NORMAL");
        reserveRequest.setProductImageUrl("testUrl");
        reserveRequest.setProductName("TEST");
        return reserveRequest;
    }

    public ReserveResponse getReserveResponse(Gson gson, String json) {
        RequestBody body = RequestBody.create(JSON, json);

        var url = payApiUrl + payApiReserveUrl;

        var client = new OkHttpClient();

        Request request = buildLinePayRequest(body, url);

        var reserveResponse = new ReserveResponse();

        try {
            Response response = client.newCall(request).execute();
            logger.info("Response HTTP Status:" + response.code());

            var responseBody = response.body().string();

            response.close();

            reserveResponse = gson.fromJson(responseBody, ReserveResponse.class);

        } catch (Exception e) {
            logger.error(e);
        }
        return reserveResponse;
    }

    public Request buildLinePayRequest(RequestBody body, String url) {
        return new Request.Builder()
                    .addHeader("X-LINE-ChannelId", channelId)
                    .addHeader("X-LINE-ChannelSecret", channelSecret)
                    .url(url)
                    .post(body)
                    .build();
    }
}
