package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Validator {
    @SerializedName("type")
    private String mType;
    @SerializedName("predicate")
    private Predicate mPredicate;
    @SerializedName("message")
    private String mMessage;

    public String getType() {
        return mType;
    }

    public Predicate getPredicate() {
        return mPredicate;
    }

    public String getMessage() {
        return mMessage;
    }
}
