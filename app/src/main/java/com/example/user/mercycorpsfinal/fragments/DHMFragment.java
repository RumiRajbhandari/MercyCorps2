package com.example.user.mercycorpsfinal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.adapter.FragmentAdapter;

/**
 * Created by root on 7/28/17.
 */
public class DHMFragment extends Fragment {
    View mView;
    FragmentTabHost mTabHost;
    String url="http://hydrology.gov.np/new/bull3/index.php/hydrology/home/main";

    public  DHMFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* mView= inflater.inflate(R.layout.fragment_dhm, container, false);
        return mView;*/
        View v = inflater.inflate(R.layout.fragment_blank,container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) v.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       /* super.onViewCreated(view, savedInstanceState);
        final WebView myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);

        myWebView.loadUrl(url);
        myWebView.postDelayed(new Runnable() {

            @Override
            public void run() {
                myWebView.loadUrl(url);
            }
        }, 500);

        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(url));
        startActivity(viewIntent);*/

    }
    private void setupViewPager(ViewPager viewPager) {


        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new WebRiver(), "River");
        adapter.addFragment(new WebRainfall(), "Rainfall");
        adapter.addFragment(new WebDadeldhura(), "Dadeldhura Display");


        viewPager.setAdapter(adapter);



    }
}
