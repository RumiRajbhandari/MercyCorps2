package com.example.user.mercycorpsfinal.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.activity.Sheet13;
import com.example.user.mercycorpsfinal.activity.Sheet14;
import com.example.user.mercycorpsfinal.activity.Sheet15;
import com.example.user.mercycorpsfinal.activity.Sheet16;
import com.example.user.mercycorpsfinal.activity.Sheet17;
import com.example.user.mercycorpsfinal.activity.Sheet18;
import com.example.user.mercycorpsfinal.activity.Sheet19;
import com.example.user.mercycorpsfinal.activity.Sheet20;
import com.example.user.mercycorpsfinal.activity.Sheet21;
import com.example.user.mercycorpsfinal.activity.Sheet25;

/**
 * A simple {@link Fragment} subclass.
 */
public class EWSResponse extends Fragment {
    ListView lv;
    ArrayAdapter<String> adapter;
    View mView;
    String[] listItems;

    public EWSResponse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_important_contacts, container, false);
       return mView;



    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listItems=getResources().getStringArray(R.array.list1);
        lv = (ListView) mView.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_main_list_item, listItems);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(position){

                    case 0 :intent = new Intent(getActivity(), Sheet13.class);
                        startActivity(intent);
                        break;
                    case 1 :intent = new Intent(getActivity(), Sheet14.class);
                        startActivity(intent);
                        break;
                    case 2 :intent = new Intent(getActivity(), Sheet15.class);
                        startActivity(intent);
                        break;
                    case 3 :intent = new Intent(getActivity(), Sheet16.class);
                        startActivity(intent);
                        break;
                    case 4 :intent = new Intent(getActivity(), Sheet17.class);
                        startActivity(intent);
                        break;
                    case 5 :intent = new Intent(getActivity(), Sheet18.class);
                        startActivity(intent);
                        break;
                    case 6 :intent = new Intent(getActivity(), Sheet19.class);
                        startActivity(intent);
                        break;
                    case 7 :intent = new Intent(getActivity(), Sheet20.class);
                        startActivity(intent);
                        break;
                    case 8 :intent = new Intent(getActivity(), Sheet21.class);
                        startActivity(intent);
                        break;
                    case 9:intent = new Intent(getActivity(), Sheet25.class);
                        startActivity(intent);
                        break;
//                    case 9:intent=new Intent(getActivity(),Sheet)







                }

            }

        });
    }


}
