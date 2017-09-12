package com.example.user.mercycorpsfinal.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.activity.CallListActivity;
import com.example.user.mercycorpsfinal.model.Contact;
import com.example.user.mercycorpsfinal.model.LatLon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    public GoogleMap mgoogleMap,sgoogleMap;
    public MapView mMapView;
    public View mView;
    public String TAG = "TAG";
    public ArrayList<LatLon> latLons;
    public ArrayList<Contact> contacts;
    public Contact contact;
//    public KmlLayer layerRiver,layerSettlement,layerInundation;
    public Button btn_settlement,ews;
    List<Marker> markers;
    List<Marker> markerSettlement;
    Marker m;
    GroundOverlay imageOverlay;
    Handler mHandler;
    Thread thread;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mgoogleMap = googleMap;
       // mgoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getContext())));
        btn_settlement=(Button) mView.findViewById(R.id.btn_settlement);
        ews=(Button) mView.findViewById(R.id.btn_ews);
        markers=new ArrayList<Marker>();
        markerSettlement=new ArrayList<Marker>();
        prepareData();
        prepareEws();


        btn_settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Marker marker: markers
                        ) {
                    marker.setVisible(false);
                }
                LatLngBounds inun = new LatLngBounds(
                        new LatLng(28.33888612675268, 80.22817484429061),       // South west corner
                        new LatLng(29.02009839189039, 81.23091929857333));      // North east corner
                GroundOverlayOptions inunMap = new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.layer1))
                        .positionFromBounds(inun);

                // GroundOverlay imageOverlay = mgoogleMap.addGroundOverlay(inunMap);
                imageOverlay = mgoogleMap.addGroundOverlay(inunMap);
                prepareSettlement();

            }
        });


//        ews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (layerSettlement!=null){
//                    layerSettlement.removeLayerFromMap();
//                }
//                imageOverlay.remove();
//                mgoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getContext())));
//                //mgoogleMap.clear();
//
//                for (Marker marker: markers
//                        ) {
//                    marker.setVisible(true);
//                }
//                mgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                    @Override
//                    public void onInfoWindowClick(Marker marker) {
//                        Log.e("TAG", "onInfoWindowClick: hello"+marker.getId());
//                        String[] str = marker.getId().split("m");
//                        LatLon l1 = latLons.get((Integer.parseInt(str[1]))%37);
//                        Log.e(TAG, "onInfoWindowClick: id is"+(Integer.parseInt(str[1]))%37 );
//
//                        Intent intent=new Intent(getContext(),CallListActivity.class);
//                        intent.putExtra("rumi",(Serializable)l1);
//                        startActivity(intent);
//                    }
//                });
//
//            }
//        });

        //Data prepare

        prepareData();

    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.e("TAG", "onInfoWindowClick: hello"+marker.getId());
        String[] str = marker.getId().split("m");
        LatLon l1 = latLons.get((Integer.parseInt(str[1]))%latLons.size());
        Log.e(TAG, "onInfoWindowClick: id is"+(Integer.parseInt(str[1]))%latLons.size() );

        Intent intent=new Intent(getContext(),CallListActivity.class);
        intent.putExtra("rumi",(Serializable)l1);
        startActivity(intent);
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private LayoutInflater inflater;
        TextView name, location,base;
        View markerView;
        ViewGroup windowContainer;

        CustomInfoWindowAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(final Marker marker) {
            markerView = inflater.inflate(R.layout.custominfowindow, null);
            //markerView.setLayoutParams(new ViewGroup.LayoutParams(350, 200));
            windowContainer = (ViewGroup) markerView.findViewById(R.id.window_container);
            name = (TextView) markerView.findViewById(R.id.name);
            location = (TextView) markerView.findViewById(R.id.location);
            base=(TextView)markerView.findViewById(R.id.based);
            String[] str = marker.getId().split("m");
            int markerId = -1;


            LatLon l1 = latLons.get((Integer.parseInt(str[1])%latLons.size()));
            //Log.e("TAG", "getInfoContents:id is "+marker.getId() );
            name.setText(l1.getContacts().get(0).getName());
            location.setText(l1.getLocation());
            base.setText(l1.getBasedOn());
            //contact.setText(l1.getContacts().get(0).toString());
            return markerView;
        }
    }

    public boolean isInternetAvailable() throws IOException, InterruptedException {
        final ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(getContext(), activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

//    public void internetAvailable(){
//        try {
//
//            // mgoogleMap.clear();
//            layerSettlement=new KmlLayer(mgoogleMap,R.raw.settlementone,getContext());
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            layerSettlement.addLayerToMap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//    }

    public void prepareEws(){


        mgoogleMap.clear();

        mgoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getContext())));
       // prepareData();

        for (final LatLon latlon : latLons
                ) {
            LatLng location = new LatLng(latlon.getLat(), latlon.getLon());
            if (latlon.getGauze()=="नदि")
            {
              m= mgoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.scale)).position(location).title(latlon.getContacts().get(0).getName()).snippet(latlon.getLocation()+"\n"+latlon.getRiver()));

            }
            else {
              m= mgoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.drop1)).position(location).title(latlon.getContacts().get(0).getName()).snippet(latlon.getLocation()+"\n"+latlon.getRiver()));

            }
            markers.add(m);
           /* mgoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.scale));
                    marker.setSnippet(latlon.getLocation()+"\n"+latlon.getRiver());
                    return false;
                }
            });*/

            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mgoogleMap.setOnInfoWindowClickListener(this);

        }
//        mgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.8318, 80.51835), 10.0f));
//
//        try {
//            layerRiver = new KmlLayer(mgoogleMap, R.raw.majorriver, this.getContext());
//            layerRiver.addLayerToMap();
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try{
//            layerRiver.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
//                @Override
//                public void onFeatureClick(Feature feature) {
//                    if (feature!=null){
//                        Log.e(TAG, "onFeatureClick: "+feature.getProperty("name") );
//                        Toast.makeText(getContext(), ""+feature.getProperty("name"), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                    }
//                }
//            });
//        }catch (Exception e){
//
//        }
    }

    public void prepareSettlement(){

        try {

            if (isInternetAvailable()){
//                internetAvailable();
            }
            else {
                Toast.makeText(getContext(), "Please connect the internet.", Toast.LENGTH_SHORT).show();
               /* final Handler handler = new Handler();
                final Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (!isInternetAvailable()){

                            }
                            if (isInternetAvailable()){
                                internetAvailable();
                                Log.e(TAG, "run: " );

                            }
                        } catch (IOException e) {

                        } catch (InterruptedException e) {

                        }
                        Log.e(TAG, "run: r" );
                        //code you want to run every second

                    }
                };
                handler.postDelayed(task, 1000);*/
                      /*  while (isInternetAvailable()!=true){
                            Log.e(TAG, "onClick: " );
                            isInternetAvailable();
                        }*/
                // internetAvailable();
                       /* mHandler=new Handler();
                        thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (isInternetAvailable()!=true){
                                        //Log.e(TAG, "onClick: hello" );
                                       mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    isInternetAvailable();
                                                } catch (IOException e) {

                                                } catch (InterruptedException e) {

                                                }
                                            }}
                                        );
                                       // internetAvailable();
                                    }
                                    internetAvailable();
                                } catch (IOException e) {

                                } catch (InterruptedException e) {

                                }
                            }
                        });
                        thread.start();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    isInternetAvailable();
                                } catch (IOException e) {

                                } catch (InterruptedException e) {

                                }
                            }});*/



            }
        } catch (IOException e) {

        } catch (InterruptedException e) {

        }




        mgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.e(TAG, "onInfoWindowClick: hello" );
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
//        final Handler handler = new Handler();
//        final Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (isInternetAvailable()){
//                        internetAvailable();
//                        Log.e(TAG, "run: " );
//                    }
//                } catch (IOException e) {
//
//                } catch (InterruptedException e) {
//
//                }
//                //code you want to run every second
//
//            }
//        };
//        handler.postDelayed(task, 1000);
//        task.run();



    }
    public void prepareData() {
        latLons = new ArrayList<LatLon>();
        contacts=new ArrayList<Contact>();
        contact=new Contact("जय बहादुर राना","9801378472");
        contacts.add(contact);
        LatLon latLon = new LatLon("तिल्की","नदि", "मछेलि","समुदायमा आधारित", 80.37805,28.81965, contacts);
        latLons.add(0, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("धनु देबीचौधरी","9815697482");
        contacts.add(contact);
        latLon = new LatLon("कटान","नदि", "वनरा","समुदायमा आधारित",80.40397 ,28.84973, contacts);
        latLons.add(1, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("शंकरदत्तजोशी","9848627174");
        contacts.add(contact);
        latLon = new LatLon("मछेलीपुल","नदि", "मछेलि","समुदायमा आधारित",80.34897 ,28.90224, contacts);
        latLons.add(2, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("पल्टा खुना","9805789383");
        contacts.add(contact);
        latLon = new LatLon("बेलकण्डी","नदि", "मछेलि","समुदायमा आधारित",80.4582 ,28.87165, contacts);
        latLons.add(3, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("आशिका सिंह","9868728412");
        contacts.add(contact);
        latLon = new LatLon("सुनवरा पुल","नदि", "सुनवरा","समुदायमा आधारित",80.34897 ,28.90224, contacts);
        latLons.add(4, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("मनिसा खोलीया","9812721162");
        contacts.add(contact);
        latLon = new LatLon("शिबनगर टोल चादनि ","नदि", "जोगुडा","समुदायमा आधारित",80.068973 ,28.917335, contacts);
        latLons.add(5, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("गायत्रीमिश्र","9812727519");
        contacts.add(contact);
        latLon = new LatLon("स्यालीपुल","नदि", "स्यालि","डी एच एम आधारित",80.32819 ,28.91551, contacts);
        latLons.add(6, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("लाल ब।बडेला","9868530916");
        contacts.add(contact);
        latLon = new LatLon("सोत्यासरकोल्मोडा","नदि", "मछेलि","समुदायमा आधारित", 80.531778,28.93113, contacts);
        latLons.add(7, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("तारा दत्तजोशी","9847619752");
        contacts.add(contact);
        latLon = new LatLon("परिगाउं, डडेल्धुरा","नदि", "महाकाली","डी एच एम आधारित",80.270619 ,29.149502, contacts);
        latLons.add(8, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("कृष्ण सिंह नगारी","9749524851");
        contacts.add(contact);
        latLon = new LatLon("दत्तु, दार्चुला","नदि", "महाकाली","डी एच एम आधारित",80.421403 ,29.795898, contacts);
        latLons.add(9, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("लोकेन्द्र बोहरा","9848705022");
        contacts.add(contact);
        latLon = new LatLon("बेदकोट १३ दैजी","नदि", "चौधर","डी एच एम आधारित",80.254325 ,28.950865, contacts);
        latLons.add(10, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("चन्द्रा शाहु","9812720236");
        contacts.add(contact);
        latLon = new LatLon("बेलडाडी ९","नदि", "चौधर","समुदायमा आधारित",80.243388 ,28.797824, contacts);
        latLons.add(11, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("दशरथ चौधरी","9809486826");
        contacts.add(contact);
        latLon = new LatLon("बागफाटा","नदि", "चौधर","समुदायमा आधारित", 80.23855,28.914559, contacts);
        latLons.add(12, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("रमेश लुहार","9806467439");
        contacts.add(contact);
        latLon = new LatLon("बैतोडा ४","नदि", "चौधर","समुदायमा आधारित",  80.258204,28.957506, contacts);
        latLons.add(13, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("हरि किशन राना","9806437727");
        contacts.add(contact);
        latLon = new LatLon("कञ्ज","नदि", "दोदा","समुदायमा आधारित", 80.399991,28.730379, contacts);
        latLons.add(14, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("लाल प्रसाद शर्मा","9749019825");
        contacts.add(contact);
        latLon = new LatLon("पुनर्वास","नदि", "दोदा","समुदायमा आधारित", 80.52383,28.57177, contacts);
        latLons.add(15, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("लाल ब। बोकटी","9810676418");
        contacts.add(contact);
        latLon = new LatLon("रातो ताल","नदि", "दोदा","समुदायमा आधारित", 80.48196,28.6593, contacts);
        latLons.add(16, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("दिल ब। चौधरी","9809481986");
        contacts.add(contact);
        latLon = new LatLon("बनहरा पुल","नदि", "वनरा","समुदायमा आधारित", 80.39687,28.88796, contacts);
        latLons.add(17, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("राम प्रसाद राना","9810656797");
        contacts.add(contact);
        latLon = new LatLon("आन्ध्रजाला","नदि", "स्यालि र सुनवरा","समुदायमा आधारित",80.366162 ,28.773494, contacts);
        latLons.add(18, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("सुनिता राना","9812757928");
        contacts.add(contact);
        latLon = new LatLon("सिमरी","वर्षा", "","समुदायमा आधारित",80.47768 ,28.66817, contacts);
        latLons.add(19, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("लिलाअधिकारी","9812797709");
        contacts.add(contact);
        latLon = new LatLon("बेलौरी,शान्तिपुर","वर्षा", "","डी एच एम आधारित", 80.35,28.683333, contacts);
        latLons.add(20, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("मंगल प्र। चौधरी","9806499855");
        contacts.add(contact);
        latLon = new LatLon("तिल्की","वर्षा", "","समुदायमा आधारित",80.37647 ,28.81766, contacts);
        latLons.add(21, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("कृष्णलाल चौधरी","9848078923");
        contacts.add(contact);
        latLon = new LatLon("दयाअमरपुर","वर्षा", "","समुदायमा आधारित", 80.4994,28.84897, contacts);
        latLons.add(22, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("सितारामचौधरी","9806460771");
        contacts.add(contact);
        latLon = new LatLon("कटान","वर्षा", "","समुदायमा आधारित", 80.40968,28.8518, contacts);
        latLons.add(23, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("कनैया चौधरी","9812751588");
        contacts.add(contact);
        latLon = new LatLon("बेलकुन्डी","वर्षा", "","समुदायमा आधारित", 80.46074,28.87005, contacts);
        latLons.add(24, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("कृष्ण राना","9806440829");
        contacts.add(contact);
        latLon = new LatLon("बनहरा","वर्षा", "","समुदायमा आधारित",80.39783 ,28.88453, contacts);
        latLons.add(25, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("तिर्थ ब. डगौरा","9848763244");
        contacts.add(contact);
        latLon = new LatLon("लालपुर","वर्षा", "","समुदायमा आधारित", 80.233,28.942, contacts);
        latLons.add(26, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("तिलक भण्डारी","9848616174");
        contacts.add(contact);
        latLon = new LatLon("सहजपुर कुमैना","वर्षा", "","समुदायमा आधारित", 80.591182,29.004854, contacts);
        latLons.add(27, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("प्रेम बहादुर महता","9848764323");
        contacts.add(contact);
        latLon = new LatLon("खल्लागोठ","वर्षा", "","समुदायमा आधारित", 80.440338,28.862318, contacts);
        latLons.add(28, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("जय बहादुर बिष्ट","9848715668");
        contacts.add(contact);
        latLon = new LatLon("झलारी","वर्षा", "","समुदायमा आधारित",80.350954 ,28.901229, contacts);
        latLons.add(29, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("संजय सारु","9806486126");
        contacts.add(contact);
        latLon = new LatLon("बगुन दैजी","वर्षा", "","समुदायमा आधारित", 80.294406,28.986302, contacts);
        latLons.add(30, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("बिसन ब। मानन्धर","9806447471");
        contacts.add(contact);
        latLon = new LatLon("दोधारा जोगबुढा","वर्षा", "","समुदायमा आधारित", 80.065207,28.910749, contacts);
        latLons.add(31, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("टेक बहादुर बिष्ट","9749007522");
        contacts.add(contact);
        latLon = new LatLon("रैकवार ६","वर्षा", "","समुदायमा आधारित",80.518681 ,28.770314, contacts);
        latLons.add(32, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("रतन ब। शाह","9805753513");
        contacts.add(contact);
        latLon = new LatLon("हनुमाननगर दैजी","वर्षा", "","समुदायमा आधारित", 80.302126,29.002234, contacts);
        latLons.add(33, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("मन्दोधरी राना","9810654379");
        contacts.add(contact);
        latLon = new LatLon("शंकरपुर,पर्सिया","वर्षा", "","समुदायमा आधारित", 80.389306,28.770922, contacts);
        latLons.add(34, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("जनक राजपन्त","9858790084");
        contacts.add(contact);
        latLon = new LatLon("आपत्कालिन संचालन केन्द्र","वर्षा", "","", 80.184154,28.969355, contacts);
        latLons.add(35, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("राममनि मिश्र","9848318777");
        contacts.add(contact);
        latLon = new LatLon("जलबायु तथा मौसम बिभाग","वर्षा", "","डी एच एम आधारित", 80.55997,28.81271, contacts);
        latLons.add(36, latLon);

        contacts=new ArrayList<Contact>();
        contact=new Contact("Dine Tamang","9841418905");
        contacts.add(contact);
        latLon = new LatLon("Mercy Corpse","वर्षा", "","INGO", 80.55,29.00, contacts);
        latLons.add(37, latLon);

    }


}


