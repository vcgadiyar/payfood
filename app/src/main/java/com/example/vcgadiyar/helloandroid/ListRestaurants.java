package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListRestaurants extends Activity {

    ListView list;

    String[] itemname ={
            "Chutneys",
            "Ginza",
            "Kings",
            "Pumphouse",
    };

    String[] itemDescription ={
            "Authentic Indian Cuisine in Bellevue",
            "Tasty Japanese Yakinuku",
            "Chinese Noodles",
            "Bar and Grill",
    };

    Integer[] imgid={
            R.mipmap.chutneys,
            R.mipmap.ginza,
            R.mipmap.kings,
            R.mipmap.pumphouse
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);

        CustomListAdapter adapter = new CustomListAdapter(this, itemname, itemDescription, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListRestaurants.this, orderselection.class);
                startActivity(intent);

            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_restaurants, menu);
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
    }*/
}
