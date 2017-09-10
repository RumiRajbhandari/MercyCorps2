package com.example.user.mercycorpsfinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.mercycorpsfinal.Api.ApiHelper;
import com.example.user.mercycorpsfinal.Api.MercyCorpInterface;
import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.adapter.CustomAdapterList;
import com.example.user.mercycorpsfinal.model.ListItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;

public class GairSarkariSasthaActivity extends AppCompatActivity {
    CustomAdapterList adapter;
    private Toolbar toolbar;
    List<ListItem> userList;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanchanpur_list);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        api = new ApiHelper().getApiWithCaching(getApplicationContext());
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        userList=new ArrayList<>();
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(verticalLayoutmanager);
        rv.setItemAnimator(new DefaultItemAnimator());
        fetchDetails();


    }
    private  void  fetchDetails(){
        final MercyCorpInterface apiService = new ApiHelper().getApiWithCaching(getApplicationContext());
//        MercyCorpInterface apiService = ApiHelper.getClient().create(MercyCorpInterface.class);
        Call<List<ListItem>> call = apiService.getList4();
        call.enqueue(new Callback<List<ListItem>>() {
            @Override
            public void onResponse(Call<List<ListItem>> call, retrofit2.Response<List<ListItem>> response) {
                userList.addAll(response.body());
                adapter = new CustomAdapterList(userList, new CustomAdapterList.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListItem item) {
                        Intent i = new Intent(GairSarkariSasthaActivity.this, DetailActivity.class);
                        i.putExtra("data", (Serializable) item);
                        startActivity(i);
                    }
                });
                rv.setAdapter(adapter);

            }


            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t) {

            }
        });

    }
    }

