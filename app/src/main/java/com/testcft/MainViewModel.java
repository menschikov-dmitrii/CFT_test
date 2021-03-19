package com.testcft;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String testUrl = "https://www.cbr-xml-daily.ru/daily_json.js";

    private MutableLiveData<MainObject> data;
    private RequestQueue mRequestQueue;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRequestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<MainObject> getData(boolean isLoadServer ) {
        if (data == null || isLoadServer)
        {
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }

    private void loadData() {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, testUrl, null, response -> {

            try {
                JSONObject jsonValutes = response.getJSONObject("Valute");
                List<Valute> list = new ArrayList<>();
                for (Iterator<String> it = jsonValutes.keys(); it.hasNext(); )
                {
                    try {
                        String key = it.next();
                        list.add(new Gson().fromJson(jsonValutes.getJSONObject(key).toString(), Valute.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(list, new Comparator<Valute>() {
                    @Override
                    public int compare(Valute o1, Valute o2) {
                        return o1.getCharCode().compareTo(o2.getCharCode());
                    }
                });

                data.postValue(new MainObject(new Date().getTime(),list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getApplication(),error.getMessage(),Toast.LENGTH_SHORT).show());

        mRequestQueue.add(request);
    }
}
