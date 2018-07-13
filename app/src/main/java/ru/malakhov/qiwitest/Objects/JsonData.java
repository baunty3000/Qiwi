package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class JsonData {

    @SerializedName("content")
    private Content mContent;

    public Content getContent() {
        return mContent;
    }
}
