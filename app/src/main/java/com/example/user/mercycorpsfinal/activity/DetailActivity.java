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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initializeViews();
        Intent intent=getIntent();
        r1=(RelativeLayout)findViewById(R.id.r1_mob);
        r2=(RelativeLayout)findViewById(R.id.r2_phone);

        listItem= (ListItem) intent.getSerializableExtra("data");

        if(listItem.getMob()!=null && !listItem.getMob().isEmpty()){
            r1.setVisibility(View.VISIBLE);
            int length=listItem.getMob().length();
            Log.d("lengthValue", String.valueOf(length));
            if(length==8){
                tvDetMobNo.setText("+977"+listItem.getMob());
            }
            else {

                tvDetMobNo.setText(listItem.getMob());
            }
            ImgCallBtnMob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    giveCalllTo(listItem.getMob());
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

            tvDetPhoneNo.setText("+977"+listItem.getLandline());

            ImgBtnCallPh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        giveCall("+977"+listItem.getLandline());


                }
            });

//            ImgCallBtnMsgPh.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sendMessage(listItem.getLandline());
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
