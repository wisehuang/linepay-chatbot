
package com.line.pay.chatbot.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfirmResponse {

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

    public class Info {

        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("transactionId")
        @Expose
        private long transactionId;
        @SerializedName("payInfo")
        @Expose
        private List<PayInfo> payInfo = null;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(long transactionId) {
            this.transactionId = transactionId;
        }

        public List<PayInfo> getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(List<PayInfo> payInfo) {
            this.payInfo = payInfo;
        }

    }

    public class PayInfo {

        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("amount")
        @Expose
        private long amount;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

    }
}
