package com.example.user.mercycorpsfinal.fragments;


import android.os.Bundle;
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
 * A simple {@link Fragment} subclass.
 */
public class EmergencyNumbers extends Fragment {

    FragmentTabHost mTabHost;

    public EmergencyNumbers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank,container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) v.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);


        return v;
    }
    private void setupViewPager(ViewPager viewPager) {


        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new Sheet2Fragment(), "आपत्कालिन अवस्थामा");
        adapter.addFragment(new Sheet3Fragment(), " अत्यन्त जरुरी फोन नम्बर ");
        viewPager.setAdapter(adapter);



    }
}
