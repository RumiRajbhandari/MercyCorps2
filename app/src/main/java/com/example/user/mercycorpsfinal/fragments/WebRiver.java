package com.example.user.mercycorpsfinal.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.user.mercycorpsfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebRiver extends Fragment {
    View mView;
    FragmentTabHost mTabHost;
    String url="http://hydrology.gov.np/new/bull3/index.php/hydrology/screen_display";

    public  WebRiver(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_dhm, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final WebView myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);



        myWebView.loadUrl(url);
        myWebView.postDelayed(new Runnable() {

            @Override
            public void run() {
                myWebView.loadUrl(url);
            }
        }, 500);

        /*Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(url));
        startActivity(viewIntent);*/

    }

}
