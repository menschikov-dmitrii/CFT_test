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

        mButtonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(true);
            }
        });

        updateData(false);
    }

    private void updateData(boolean isLoadServer)
    {
        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

        LiveData<MainObject> data = model.getData(isLoadServer);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable(MainActivity.class.getCanonicalName(),mValuteAdapter.getModel());
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       /* MainObject savedObject = savedInstanceState.getParcelable(MainActivity.class.getCanonicalName());
        if(savedObject!=null)
            mValuteAdapter.addData(savedObject);
        else
            mValuteAdapter.updateData();*/
    }

}