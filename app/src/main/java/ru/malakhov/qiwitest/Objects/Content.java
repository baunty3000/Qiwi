package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Content {

    @SerializedName("elements")
    private List<Element> mElements = new ArrayList<Element>();

    public List<Element> getElements() {
        return mElements;
    }
}
