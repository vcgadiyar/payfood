package com.example.vcgadiyar.helloandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeedbackActivity extends Activity {

    ArrayList<String> tvs =  new ArrayList<>();


    ProgressBar progressBar;
    Integer count = 0;
    String jsondat1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            final HashMap<String, Integer> hashMap = (HashMap<String, Integer>) getIntent().getSerializableExtra("hashMap");
            //The key argument here must match that used in the other activity

            count = hashMap.size();

            TextView tvp1 = (TextView) findViewById(R.id.lblitem1);
            //tvp1.setTextColor(Color.WHITE);
            TextView tvp2 = (TextView) findViewById(R.id.lblitem2);
            //tvp2.setTextColor(Color.WHITE);
            TextView tvp3 = (TextView) findViewById(R.id.lblitem3);
           // tvp3.setTextColor(Color.WHITE);

            final RatingBar rb1 = (RatingBar) findViewById(R.id.ratingBar1);
            final RatingBar rb2 = (RatingBar) findViewById(R.id.ratingBar2);
            final RatingBar rb3 = (RatingBar) findViewById(R.id.ratingBar3);

            final EditText et1 = (EditText) findViewById(R.id.fld_fb1);
            final EditText et2 = (EditText) findViewById(R.id.fld_fb2);
            final EditText et3 = (EditText) findViewById(R.id.fld_fb3);


            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                tvs.add((String)pair.getKey());
                it.remove(); // avoids a ConcurrentModificationException
            }
            tvp1.setText(tvs.get(0));
            tvp2.setText(tvs.get(1));
            tvp3.setText(tvs.get(2));

            Button _sendBtn = (Button) findViewById(R.id.btnSubmit1);

            _sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    jsondat1 = "[\n" +
                            "    {\n" +
                            "   \"merchantId\" : 12,\n" +
                            "   \"customerId\" : 20,\n" +
                            "   \"username\" : \"vignesh\",\n" +
                            "   \"intemId\" : \"34\",\n" +
                            "   \"itemName\" : "+tvs.get(0)+",\n" +
                            "   \"reviewStars\" : "+rb1.getNumStars()+",\n" +
                            "   \"reviewComments\" : "+et1.getText()+",\n" +
                            "   \"timestamp\": 1477191379898\n" +
                            "    },\n" +
                            "    {\n" +
                            "    \"merchantId\" : 12,\n" +
                            "   \"customerId\" : 20,\n" +
                            "   \"username\" : \"vignesh\",\n" +
                            "   \"intemId\" : \"34\",\n" +
                            "   \"itemName\" : "+tvs.get(1)+",\n" +
                            "   \"reviewStars\" : "+rb2.getNumStars()+",\n" +
                            "   \"reviewComments\" : "+et2.getText()+",\n" +
                            "   \"timestamp\": 1577191379898\n" +
                            "    },\n" +
                            "    {\n" +
                            "    \"merchantId\" : 12,\n" +
                            "   \"customerId\" : 20,\n" +
                            "   \"username\" : \"vignesh\",\n" +
                            "   \"intemId\" : \"10\",\n" +
                            "   \"itemName\" : "+tvs.get(2)+",\n" +
                            "   \"reviewStars\" : "+rb3.getNumStars()+",\n" +
                            "   \"reviewComments\" : "+et3.getText()+",\n" +
                            "   \"timestamp\": 1577191379898\n" +
                            "    }\n" +
                            "]";

                    //new SendOrderTask().execute();
                    Toast.makeText(FeedbackActivity.this, "Thanks for the Feedback!!! =)",
                            Toast.LENGTH_LONG).show();



                }
            });
        }
    }

    class SendOrderTask extends AsyncTask<Void, Void, String> {


        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            makePostRequest("http://10.101.1.235:8080/sendFeedbacks", jsondat1);

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
                Log.i("INform", input);

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


            Toast.makeText(FeedbackActivity.this, "Thanks for the Feedback!!! =)",
                    Toast.LENGTH_LONG).show();
        }

        public SendOrderTask() {

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
