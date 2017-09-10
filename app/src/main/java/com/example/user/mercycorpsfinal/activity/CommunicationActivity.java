package com.example.user.mercycorpsfinal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.example.user.mercycorpsfinal.R;

public class CommunicationActivity extends AppCompatActivity {
ZoomControls simpleZoomControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        final ImageView imageView=(ImageView)findViewById(R.id.imgCommunication);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.communicationchannel));
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        imageView.startAnimation(zoomAnimation);
        simpleZoomControls = (ZoomControls) findViewById(R.id.simpleZoomControl); // initiate a ZoomControls


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // show Zoom Controls on touch of image
                simpleZoomControls.show();
                return false;
            }
        });
        // perform setOnZoomInClickListener event on ZoomControls
        simpleZoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calculate current scale x and y value of ImageView
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                // set increased value of scale x and y to perform zoom in functionality
                imageView.setScaleX((float) (x + 1));
                imageView.setScaleY((float) (y + 1));

            }
        });
        // perform setOnZoomOutClickListener event on ZoomControls
        simpleZoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calculate current scale x and y value of ImageView
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                // set decreased value of scale x and y to perform zoom out functionality
                imageView.setScaleX((float) (x - 1));
                imageView.setScaleY((float) (y - 1));

            }
        });
    }


}
