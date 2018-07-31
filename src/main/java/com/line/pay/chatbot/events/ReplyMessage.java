
package com.line.pay.chatbot.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyMessage {

    @SerializedName("replyToken")
    @Expose
    private String replyToken;
    @SerializedName("message")
    @Expose
    private Message message;

    public String getReplyToken() {
        return replyToken;
    }

    public void setReplyToken(String replyToken) {
        this.replyToken = replyToken;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
