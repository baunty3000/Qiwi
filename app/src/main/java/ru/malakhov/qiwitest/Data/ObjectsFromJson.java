package ru.malakhov.qiwitest.Data;

import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ru.malakhov.qiwitest.Objects.Content;
import ru.malakhov.qiwitest.Objects.Element;
import ru.malakhov.qiwitest.Objects.JsonData;

import static ru.malakhov.qiwitest.UI.MainActivity.TAG;

public class ObjectsFromJson {

    public static final String FILE = "main.JSON";
    private Context mContext;

    public ObjectsFromJson(Context context) {
        mContext = context;
    }

    public List<Element> getListElement(){
        return getContent().getElements();
    }

    public Content getContent() {
        Gson gson = new Gson();
        Content content = gson.fromJson(getJSON(), JsonData.class).getContent();
        return content;
    }

    private String getJSON() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(FILE), "UTF-8"));
            String line;
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }
            return result;
        } catch (IOException e) {
            Log.d(TAG, "getJSON Exception: "+e);
            Toast.makeText(mContext, "Проверь JSON", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
