package com.line.pay.chatbot.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReserveRequest {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImageUrl")
    @Expose
    private String productImageUrl;
    @SerializedName("amount")
    @Expose
    private long amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("confirmUrl")
    @Expose
    private String confirmUrl;
    @SerializedName("cancelUrl")
    @Expose
    private String cancelUrl;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("confirmUrlType")
    @Expose
    private String confirmUrlType;
    @SerializedName("checkConfirmUrlBrowser")
    @Expose
    private String checkConfirmUrlBrowser;
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("capture")
    @Expose
    private String capture;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getConfirmUrlType() {
        return confirmUrlType;
    }

    public void setConfirmUrlType(String confirmUrlType) {
        this.confirmUrlType = confirmUrlType;
    }

    public String getCheckConfirmUrlBrowser() {
        return checkConfirmUrlBrowser;
    }

    public void setCheckConfirmUrlBrowser(String checkConfirmUrlBrowser) {
        this.checkConfirmUrlBrowser = checkConfirmUrlBrowser;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }
}