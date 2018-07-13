package ru.malakhov.qiwitest.UI;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import ru.malakhov.qiwitest.Data.ObjectsFromJson;
import ru.malakhov.qiwitest.Objects.Element;
import ru.malakhov.qiwitest.R;

public class MainActivity extends AppCompatActivity implements SpinnerCallBack{

    public static final String TAG = "info";
    private List<Element> mElements;
    private RecyclerView mRecyclerView;
    private AdapterRecycler mAdapter;
    private ConstructorRecycler mConstrRecycler;
    private Handler mHandler;


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        mHandler = new Handler();

        mElements = new ObjectsFromJson(this).getListElement(); // получили элементы из JSON
        mConstrRecycler = new ConstructorRecycler(mElements);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterRecycler(this);
        mAdapter.setSpinnerCallBack(this);
        mAdapter.setElements(mConstrRecycler.getListResult());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void spinnerItemSelected(final Element element) {
        Runnable reInit = new Runnable() {
            @Override
            public void run() {
                mConstrRecycler.reInitialization(element);
                mAdapter.setElements(mConstrRecycler.getListResult());
            }
        };
        mHandler.post(reInit);
    }
}