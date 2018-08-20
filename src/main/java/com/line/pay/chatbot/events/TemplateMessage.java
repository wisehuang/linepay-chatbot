package com.line.pay.chatbot.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TemplateMessage {

    @SerializedName("replyToken")
    @Expose
    private String replyToken;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;

    public String getReplyToken() {
        return replyToken;
    }

    public void setReplyToken(String replyToken) {
        this.replyToken = replyToken;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


}