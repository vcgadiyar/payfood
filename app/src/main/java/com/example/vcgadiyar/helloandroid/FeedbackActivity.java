package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeedbackActivity extends Activity {

    ArrayList<String> tvs =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            final HashMap<String, Integer> hashMap = (HashMap<String, Integer>) getIntent().getSerializableExtra("hashMap");
            //The key argument here must match that used in the other activity

            TextView tvp1 = (TextView) findViewById(R.id.lblitem1);
            TextView tvp2 = (TextView) findViewById(R.id.lblitem2);
            TextView tvp3 = (TextView) findViewById(R.id.lblitem3);

            RatingBar rb1 = (RatingBar) findViewById(R.id.ratingBar1);
            RatingBar rb2 = (RatingBar) findViewById(R.id.ratingBar2);
            RatingBar rb3 = (RatingBar) findViewById(R.id.ratingBar3);

            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                tvs.add((String)pair.getKey());
                it.remove(); // avoids a ConcurrentModificationException
            }
            tvp1.setText(tvs.get(0));
            tvp2.setText(tvs.get(1));
            tvp3.setText(tvs.get(2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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
