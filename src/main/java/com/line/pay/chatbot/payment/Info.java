
package com.line.pay.chatbot.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("paymentUrl")
    @Expose
    private PaymentUrl paymentUrl;
    @SerializedName("transactionId")
    @Expose
    private long transactionId;
    @SerializedName("paymentAccessToken")
    @Expose
    private String paymentAccessToken;

    public PaymentUrl getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(PaymentUrl paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentAccessToken() {
        return paymentAccessToken;
    }

    public void setPaymentAccessToken(String paymentAccessToken) {
        this.paymentAccessToken = paymentAccessToken;
    }

}
