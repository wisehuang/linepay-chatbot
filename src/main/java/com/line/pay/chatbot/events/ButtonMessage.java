
package com.line.pay.chatbot.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ButtonMessage {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("action")
    @Expose
    private Action action;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("color")
    @Expose
    private String color;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
