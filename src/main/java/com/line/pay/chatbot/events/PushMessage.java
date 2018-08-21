package com.line.pay.chatbot.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushMessage {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("messages")
    @Expose
    private List<StickerMessage> messages = null;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<StickerMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<StickerMessage> messages) {
        this.messages = messages;
    }

}
