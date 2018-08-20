
package com.line.pay.chatbot.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentUrl {

    @SerializedName("web")
    @Expose
    private String web;
    @SerializedName("app")
    @Expose
    private String app;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

}
