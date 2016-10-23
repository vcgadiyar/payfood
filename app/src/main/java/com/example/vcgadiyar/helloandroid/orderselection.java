package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class orderselection extends Activity {

    ListView list;
    ProgressBar progressBar;
    TextView responseView;
    Button send_btn;
    HashMap<String, Integer> count = new HashMap<>();

    static final String SessionToken = "566130ee-fa82-8bf5-9cec-444f0065eb11";
    static final String API_URL = "https://apisandbox.dev.clover.com/v3/merchants/N6SHG89MRV4BJ/items";



   ArrayList<String> dishnames = new ArrayList<>();

    ArrayList<String> dishprice = new ArrayList<>();

    ArrayList<String> requestIds = new ArrayList<>();

  /*  String[] dishprice ={
            "14.95",
            "13.95",
            "20",
            "7",
    };*/


    String[] starRating ={
            "4.5 stars (123)",
            "2 stars (22)",
            "3 stars (44)",
            "5 stars (35)",
            "4 stars",
            "3 stars",
            "2 stars",
            "1 star"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderselection);

        responseView = (TextView) findViewById(R.id.total_txt);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        send_btn = (Button)findViewById(R.id.btn_send);



        new RetrieveFeedTask().execute();





        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {


        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            try {
                URL url = new URL(API_URL + "?access_token=" + SessionToken);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }

            Log.i("INFO", response);

            //responseView.setText(response);



            try {
                JSONObject object = new JSONObject(response);

                JSONArray leaders= object.getJSONArray("elements");
                Log.d("elements",leaders.toString());
                for(int i=0;i<leaders.length(); i++){
                    JSONObject jsonas = leaders.getJSONObject(i);
                    String name = jsonas.getString("name");
                    Log.d("Name of Dish",name);
                    dishnames.add(i, name);
                    String price = jsonas.getString("price");
                    Log.d("Price of Dish", price);
                    dishprice.add(i, price);
                    String requestID = object.getString("id");
                    requestIds.add(i, requestID);
                }

           } catch (JSONException e) {
               e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);

            final MenuListAdapter adapter = new MenuListAdapter(orderselection.this, dishnames, dishprice, starRating, requestIds, count);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);


        }

        public RetrieveFeedTask() {

        }
    }
}


