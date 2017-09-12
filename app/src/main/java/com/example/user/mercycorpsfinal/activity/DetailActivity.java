package com.example.user.mercycorpsfinal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.mercycorpsfinal.R;
import com.example.user.mercycorpsfinal.model.ListItem;

public class DetailActivity extends AppCompatActivity {
    TextView tvDetOrg,tvDetPerson,tvDetPhoneNo,tvDetMobNo;
    ListItem listItem;
    ImageButton ImgBtnCallPh,ImgCallBtnMob,ImgCallBtnMsgPh,ImgCallBtnMsgMob;
    private Toolbar toolbar;
    RelativeLayout r1,r2;
    String mob1,mob2,landline1,landline2;
    int length,len;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initializeViews();
        Intent intent=getIntent();
        r1=(RelativeLayout)findViewById(R.id.r1_mob);
        r2=(RelativeLayout)findViewById(R.id.r2_phone);

        listItem= (ListItem) intent.getSerializableExtra("data");

        if(listItem.getMob()!=null && !listItem.getMob().isEmpty()){
            r1.setVisibility(View.VISIBLE);
             length=listItem.getMob().length();
            Log.d("lengthValue", String.valueOf(length));
            if(length==8){
                mob1="+977"+listItem.getMob();
                tvDetMobNo.setText(mob1);
            }
            else {
                mob2=listItem.getMob();
                tvDetMobNo.setText(mob2);
            }
            ImgCallBtnMob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(length==8){
                        giveCalllTo(mob1);

                    }
                    else {

                        giveCalllTo(mob2);

                    }

                }
            });
                ImgCallBtnMsgMob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage(listItem.getMob());
                    }
                });

        }
        else
        {
            r1.setVisibility(View.GONE);
        }
        if(listItem.getLandline()!=null && !listItem.getLandline().isEmpty())
        {
            r2.setVisibility(View.VISIBLE);
           if(listItem.getLandline()!=null &&!listItem.getLandline().isEmpty()){
               len=listItem.getLandline().length();
               if(len==8){
                   landline1="+977"+listItem.getLandline();
                   tvDetPhoneNo.setText(landline1);


               }
               else{
                   landline2=listItem.getLandline();
                   tvDetPhoneNo.setText(landline2);
               }
           }



            ImgBtnCallPh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(len==8){
                        giveCall(landline1);

                    }
                    else {

                        giveCall(landline2);

                    }

//                        giveCall("+977"+listItem.getLandline());


                }
            });

//            ImgCallBtnMsgPh.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(length==8){
//                        sendMessage(landline1);
//
//                    }
//                    else {
//
//
//
//                    }
//
//                }
//            });

        }
        else {
            r2.setVisibility(View.GONE);
        }
if(listItem.getOrganization()!=null && !listItem.getOrganization().isEmpty()){
    tvDetOrg.setVisibility(View.VISIBLE);
    tvDetOrg.setText(listItem.getOrganization());
}
else
{
    tvDetOrg.setVisibility(View.GONE);
}

       if(listItem.getPerson()!=null && !listItem.getPerson().isEmpty()) {
           tvDetPerson.setVisibility(View.VISIBLE);
           tvDetPerson.setText(listItem.getPerson());
       }
       else{
           tvDetPerson.setVisibility(View.GONE);
       }
    }

    private void sendMessage(final String phoneNO) {
        final EditText msgBody = new EditText(this);

        msgBody.setHint("Enter message");

        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("\n"+"To: "+phoneNO)
                .setView(msgBody)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = msgBody.getText().toString();
                        Log.e("TAG", "onClick: "+message );
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phoneNO, null, message, null, null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }



    public void giveCalllTo(String phoneNo) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNo));
        if (ActivityCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    public void giveCall(String mobNo) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobNo));
        if (ActivityCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    private void initializeViews() {
        tvDetOrg=(TextView)findViewById(R.id.det_org);
        tvDetPerson=(TextView)findViewById(R.id.det_person);

       //r1:
        tvDetMobNo=(TextView)findViewById(R.id.mob);
        ImgCallBtnMob=(ImageButton) findViewById(R.id.callMob);
        ImgCallBtnMsgMob=(ImageButton) findViewById(R.id.mesage_mob);
        //r2:
        tvDetPhoneNo=(TextView)findViewById(R.id.phone);
        ImgBtnCallPh=(ImageButton) findViewById(R.id.callPhone);
//        ImgCallBtnMsgPh=(ImageButton)findViewById(R.id.mailPhone);

    }
}
