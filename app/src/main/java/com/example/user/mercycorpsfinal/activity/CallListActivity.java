package com.example.user.mercycorpsfinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.adapter.CallListAdapter;
import com.example.user.mercycorpsfinal.model.LatLon;

public class CallListActivity extends AppCompatActivity {
    LatLon latLon;
    String TAG = "TAG";
    private RecyclerView recyclerView;
    private CallListAdapter cAdapter;
    TextView gauge, river,basedon,location;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        gauge=(TextView)findViewById(R.id.gauge);
        river=(TextView)findViewById(R.id.river);
        location=(TextView)findViewById(R.id.location);
        basedon=(TextView)findViewById(R.id.based);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        latLon = new LatLon();

        Intent intent = getIntent();
        latLon = (LatLon) intent.getSerializableExtra("rumi");
        Log.e(TAG, "onCreate: " + latLon.getGauze());
        gauge.setText("गेज: "+latLon.getGauze());
        Log.e(TAG, "onCreate: "+"नदि" );
        if (latLon.getRiver().isEmpty()) {
            river.setText("");

        }
        else
        {
            river.setText("नदि: "+latLon.getRiver());

        }


        basedon.setText("कैफियत: "+latLon.getBasedOn());
        location.setText("स्थान: "+latLon.getLocation());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        cAdapter = new CallListAdapter(CallListActivity.this,latLon.getContacts());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);


    }
}
