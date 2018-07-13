package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("type")
    private String mType;
    @SerializedName("field")
    private String mField;
    @SerializedName("predicate")
    private Predicate mPredicate;

    public String getType() {
        return mType;
    }

    public String getField() {
        return mField;
    }

    public Predicate getPredicate() {
        return mPredicate;
    }
}
