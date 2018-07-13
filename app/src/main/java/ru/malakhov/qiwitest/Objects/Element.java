package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

public class Element {
    @SerializedName("type")
    private String mType;
    @SerializedName("value")
    private String mValue;
    @SerializedName("validator")
    private Validator mValidator;
    @SerializedName("name")
    private String mName;
    @SerializedName("view")
    private View mView;
    @SerializedName("condition")
    private Condition mCondition;
    @SerializedName("content")
    private Content mContent;
    @SerializedName("semantics")
    private Semantics mSemantics;

    public String getType() {
        return mType;
    }

    public String getValue() {
        return mValue;
    }

    public Validator getValidator() {
        return mValidator;
    }

    public String getName() {
        return mName;
    }

    public View getView() {
        return mView;
    }

    public Condition getCondition() {
        return mCondition;
    }

    public Content getContent() {
        return mContent;
    }

    public Semantics getSemantics() {
        return mSemantics;
    }
}

