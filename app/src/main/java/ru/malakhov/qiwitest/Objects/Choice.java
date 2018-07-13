package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Choice {
    @SerializedName("value")
    private String mValue;
    @SerializedName("title")
    private String mTitle;

    public String getValue() {
        return mValue;
    }

    public String getTitle() {
        return mTitle;
    }
}
