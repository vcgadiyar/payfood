package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class OrderConfirmation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String orderId = extras.getString("ORDER_ID");

            final HashMap<String, Integer> hashMap = (HashMap<String, Integer>) getIntent().getSerializableExtra("hashMap");
            //The key argument here must match that used in the other activity

            TextView orderconf = (TextView)findViewById(R.id.conf_t);
            orderconf.append(". Your Order ID is "+orderId);

            Button _fbBtn = (Button) findViewById(R.id.btn_feedbk);

            _fbBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(OrderConfirmation.this, FeedbackActivity.class);
                    intent.putExtra("hashMap", hashMap);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_confirmation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
