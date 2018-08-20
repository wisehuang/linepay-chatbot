package com.line.pay.chatbot.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyButtonMessage {

    @SerializedName("replyToken")
    @Expose
    private String replyToken;
    @SerializedName("messages")
    @Expose
    private List<ButtonMessage> buttonMessages = null;

    public String getReplyToken() {
        return replyToken;
    }

    public void setReplyToken(String replyToken) {
        this.replyToken = replyToken;
    }

    public List<ButtonMessage> getButtonMessages() {
        return buttonMessages;
    }

    public void setButtonMessages(List<ButtonMessage> buttonMessages) {
        this.buttonMessages = buttonMessages;
    }



}