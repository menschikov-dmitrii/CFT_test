package com.testcft;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ValuteAdapter mValuteAdapter;
    private LinearLayoutManager mLayoutManager;
    private Button mButtonUpdateData;
    private TextView mTextViewInfoUpdate;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mButtonUpdateData = findViewById(R.id.mButtonUpdateData);
        mTextViewInfoUpdate = findViewById(R.id.mTextViewInfoUpdate);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mValuteAdapter = new ValuteAdapter();
        mRecyclerView.setAdapter(mValuteAdapter);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        mButtonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.loadData();
            }
        });

        LiveData<MainObject> data = model.getData();
        data.observe(this, new Observer<MainObject>() {
            @Override
            public void onChanged(@Nullable MainObject s) {
                mValuteAdapter.addData(s);
                mTextViewInfoUpdate.setText(String.format(getString(R.string.textViewLastUpdate), convertStringToDate(new Date(s.getDateUpdate()))));
            }
        });
    }

    public String convertStringToDate(Date date)
    {
        String dateString = "";
        SimpleDateFormat sdfr = new SimpleDateFormat(getString(R.string.patternDateFormat));
        try{
            dateString = sdfr.format( date );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }

}