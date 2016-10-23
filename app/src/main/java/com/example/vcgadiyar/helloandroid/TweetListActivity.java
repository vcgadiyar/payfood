package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TweetListActivity extends Activity {
    Button _emailSendBtn;
    Button _createFileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);

        _emailSendBtn = (Button) findViewById(R.id.btn_send);
        _createFileBtn = (Button) findViewById(R.id.btn_create);

        _emailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _emailSendBtn.setText("Sending....");

                Intent emailIntent = new Intent();
                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");// Package Name, Class Name
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vgadiyar@outlook.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Meeting starting now");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please join at meeting room 25 ");
                try {
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(TweetListActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }


                    /*Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vgadiyar@outlook.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Meeting starting now");
                    i.putExtra(Intent.EXTRA_TEXT, "Please join at meeting room 25");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(TweetListActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }*/

                _emailSendBtn.setText("Email sent");


            }
        });

        _emailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateFileActivity.class);
                startActivity(intent);

            }
        });
    }
}
