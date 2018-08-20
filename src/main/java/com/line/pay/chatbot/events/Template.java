package com.line.pay.chatbot.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Template {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("thumbnailImageUrl")
    @Expose
    private String thumbnailImageUrl;
    @SerializedName("imageAspectRatio")
    @Expose
    private String imageAspectRatio;
    @SerializedName("imageSize")
    @Expose
    private String imageSize;
    @SerializedName("imageBackgroundColor")
    @Expose
    private String imageBackgroundColor;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("defaultAction")
    @Expose
    private DefaultAction defaultAction;
    @SerializedName("actions")
    @Expose
    private List<Action> actions = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getImageAspectRatio() {
        return imageAspectRatio;
    }

    public void setImageAspectRatio(String imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageBackgroundColor() {
        return imageBackgroundColor;
    }

    public void setImageBackgroundColor(String imageBackgroundColor) {
        this.imageBackgroundColor = imageBackgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DefaultAction getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(DefaultAction defaultAction) {
        this.defaultAction = defaultAction;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
