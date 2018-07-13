package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Semantics {
    @SerializedName("type")
    private String mType;

    public String getType() {
        return mType;
    }
}
