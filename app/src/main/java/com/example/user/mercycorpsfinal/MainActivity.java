package com.example.user.mercycorpsfinal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.user.mercycorpsfinal.activity.AboutUsActivity;
import com.example.user.mercycorpsfinal.activity.ApatkalinAwasthaActivity;
import com.example.user.mercycorpsfinal.activity.CommunicationActivity;
import com.example.user.mercycorpsfinal.activity.ThresholdActivity;
import com.example.user.mercycorpsfinal.fragments.ClusterFragment;
import com.example.user.mercycorpsfinal.fragments.DHMFragment;
import com.example.user.mercycorpsfinal.fragments.EWSResponse;
import com.example.user.mercycorpsfinal.fragments.EmergencyNumbers;
import com.example.user.mercycorpsfinal.fragments.GaugeReaderFragment;
import com.example.user.mercycorpsfinal.fragments.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    ImageView img_drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
//        img_drawer=(ImageView)drawerLayout.findViewById(R.id.image_view);

        navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                Intent intent;
                switch (menuItem.getItemId()){
                    case  R.id.home:
                        intent =new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.apatkalin:
                        intent =new Intent(MainActivity.this, ApatkalinAwasthaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.dhmTollFree:
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + "1155"));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) !=
                                PackageManager.PERMISSION_GRANTED) {

                        }
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                        break;

                    case R.id.commCha:
                        intent=new Intent(MainActivity.this, CommunicationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.AboutUs:
                        intent=new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.threshold:
                        intent=new Intent(MainActivity.this,ThresholdActivity.class);
                        startActivity(intent);
                        break;





                }

                return false;
            }
        });
        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("EWS Pocket Dairy");

            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapFragment(), "नक्सा");
        adapter.addFragment(new EWSResponse(), "पूर्व सूचना प्रणाली प्रतिकार्य  ");
        adapter.addFragment(new DHMFragment()," जल तथा  मौसम बिज्ञान बिभाग  ");
        adapter.addFragment(new EmergencyNumbers(),"आपत्कालिन नम्बर");
        adapter.addFragment(new ClusterFragment(),"क्षेत्रगत समूह ");
        adapter.addFragment(new GaugeReaderFragment(),"अवलोकन कर्ता");
        viewPager.setAdapter(adapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
        startActivity(intent);
        // return true;

//
        return super.onOptionsItemSelected(item);
    }*/
}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}

