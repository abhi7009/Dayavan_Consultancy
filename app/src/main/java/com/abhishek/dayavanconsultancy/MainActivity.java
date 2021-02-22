package com.abhishek.dayavanconsultancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Model> items;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        items = MySharedPreferences.ReadFromSharedPref(this);
        recyclerView.setAdapter(new Adapter(MainActivity.this, items));
        if (items == null) {
            items = new ArrayList<>();
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().
                        addHeader("x-api-key", "WEBX@1433").
                        addHeader("Authorization", "BasicYWRtaW46MTIzNA==").build());
            }
        });
        ((API) new Retrofit.Builder().baseUrl(API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient.build()).build().create(API.class)).getmodels().
                enqueue(new Callback<List<Model>>() {
                    public void onResponse(Call<List<Model>> call, retrofit2.Response<List<Model>> response) {

                        items = response.body();
                        MySharedPreferences.WriteInsSharedPref(MainActivity.this, items);

                        Toast.makeText(MainActivity.this, "Response Received", Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(new Adapter(MainActivity.this, items));

                    }

                    public void onFailure(Call<List<Model>> call, Throwable t) {

                        Toast.makeText(MainActivity.this, "Please check internet connection...", Toast.LENGTH_SHORT).show();
                    }
                });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String toString) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for(Model item : items){
            if(item.getVehicle_no().toLowerCase().contains(toString.toLowerCase())){
                filteredList.add(item);
            }
        }
        recyclerView.setAdapter(new Adapter(MainActivity.this,filteredList));
    }

}