package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Predicate {
    @SerializedName("type")
    private String mType;
    @SerializedName("pattern")
    private String mPattern;

    public String getType() {
        return mType;
    }

    public String getPattern() {
        return mPattern;
    }
}
