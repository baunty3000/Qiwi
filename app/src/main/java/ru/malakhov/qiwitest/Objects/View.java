package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class View {
    @SerializedName("title")
    private String mTitle;
    @SerializedName("prompt")
    private String mPrompt;
    @SerializedName("widget")
    private Widget mWidget;

    public String getTitle() {
        return mTitle;
    }

    public String getPrompt() {
        return mPrompt;
    }

    public Widget getWidget() {
        return mWidget;
    }
}
