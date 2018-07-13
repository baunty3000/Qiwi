package ru.malakhov.qiwitest.Objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Widget {
    @SerializedName("type")
    private String mType;
    @SerializedName("choices")
    private List<Choice> mChoices;
    @SerializedName("keyboard")
    private String mKeyboard;
    private int mSelectedPosition; // храним текущую нажатую позицию спиннера
    private String mText; // храним введенный текст, чтобы после перерисовки ресайкла его отобразить

    public String getType() {
        return mType;
    }

    public List<Choice> getChoices() {
        return mChoices;
    }

    public String getKeyboard() {
        return mKeyboard;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
