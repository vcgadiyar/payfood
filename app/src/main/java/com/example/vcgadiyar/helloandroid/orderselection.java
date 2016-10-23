package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class orderselection extends Activity {

    String requestID = null;
    ListView list;
    int numItems = 0;
    ProgressBar progressBar;
    TextView responseView;
    MenuListAdapter adapter ;
    Button send_btn;
    HashMap<String, Integer> count = new HashMap<>();

    HashMap<String, Integer> feedbItems = new HashMap<>();

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
            "4 stars (17)",
            "3 stars (90)",
            "2 stars (88)",
            "1 star (55)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderselection);

        responseView = (TextView) findViewById(R.id.total_txt);
        responseView.setTextColor(Color.WHITE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        send_btn = (Button)findViewById(R.id.btn_pay);



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
                numItems = leaders.length();
                Log.d("elements",leaders.toString());
                for(int i=0;i<leaders.length(); i++){
                    JSONObject jsonas = leaders.getJSONObject(i);
                    String name = jsonas.getString("name");
                    Log.d("Name of Dish",name);
                    dishnames.add(i, name);
                    String price = jsonas.getString("price");
                    Log.d("Price of Dish", price);
                    dishprice.add(i, price);
                    String requestID = jsonas.getString("id");
                    requestIds.add(i, requestID);
                }

           } catch (JSONException e) {
               e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);

            adapter = new MenuListAdapter(orderselection.this, dishnames, dishprice, starRating, requestIds, count);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);

            send_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SendOrderTask().execute();




                }
            });


        }

        public RetrieveFeedTask() {

        }


    }

    class SendOrderTask extends AsyncTask<Void, Void, String> {


        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            String resp = makePostRequest("https://apisandbox.dev.clover.com/v3/merchants/N6SHG89MRV4BJ/orders?access_token=566130ee-fa82-8bf5-9cec-444f0065eb11", "{\"state\": \"open\"}");

            try {
                JSONObject object = new JSONObject(resp);
                requestID = object.getString("id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < numItems; i++)
            {
                int n = adapter.getCount(requestIds.get(i));
                if (n > 0)
                {
                    feedbItems.put(dishnames.get(i), n);
                }
                for(int j=0;j<n;j++){
                    String jsondat = "{\"item\": { \"id\":\""+requestIds.get(i)+"\"}}";
                    makePostRequest("https://apisandbox.dev.clover.com/v3/merchants/N6SHG89MRV4BJ/orders/"+requestID+"/line_items?access_token=566130ee-fa82-8bf5-9cec-444f0065eb11", jsondat);
                }
            }
            return "success";

        }

        String makePostRequest(String urlstr, String jsondata)
        {
            try {

                //URL url = new URL("https://apisandbox.dev.clover.com/v3/merchants/N6SHG89MRV4BJ/orders?access_token=566130ee-fa82-8bf5-9cec-444f0065eb11");
                URL url = new URL(urlstr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                //String input = "{\"state\": \"open\"}";
                String input = jsondata;

                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

/*            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }*/

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    stringBuilder.append(output).append("\n");
                }

                conn.disconnect();
                return  stringBuilder.toString();

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return null;

            } catch (Exception e) {

                e.printStackTrace();
                return null;

            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }

            Log.i("INFO", response);

            //responseView.setText(response);


            Intent intent = new Intent(orderselection.this, OrderConfirmation.class);
            intent.putExtra("ORDER_ID", requestID);
            intent.putExtra("hashMap", feedbItems);
            startActivity(intent);





        }

        public SendOrderTask() {

        }


    }


}


