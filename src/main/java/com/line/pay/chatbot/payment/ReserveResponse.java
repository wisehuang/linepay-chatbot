
package com.line.pay.chatbot.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReserveResponse {

    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("returnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
