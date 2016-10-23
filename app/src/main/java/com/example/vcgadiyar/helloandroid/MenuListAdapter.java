package com.example.vcgadiyar.helloandroid;

/**
 * Created by vcgadiyar on 10/22/16.
 */

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> dishname;
    private final ArrayList<String> dishprice;
    private final  ArrayList<String> requestIds;

    private final String[] starRating;
    private  static double total;
    private static boolean checkchanged = false;
    HashMap<String, Integer> countMap;

    public MenuListAdapter(Activity context, ArrayList<String> dishname, ArrayList<String> dishprice, String[] starRating, ArrayList<String> reuqestIds, HashMap<String, Integer> count
                           ) {
        super(context, R.layout.mylist, dishname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.dishname=dishname;
        this.dishprice=dishprice;
        this.starRating = starRating;
        this.total = 0;
        this.countMap = count;
        this.requestIds = reuqestIds;


    }

    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.menulist, null, true);


        final CheckBox cBox = (CheckBox) rowView.findViewById(R.id.checkBox1);

        final TextView cost = (TextView) rowView.findViewById(R.id.cost);
        final TextView rating = (TextView) rowView.findViewById(R.id.rating);
        final EditText qty = (EditText)rowView.findViewById(R.id.fld_qty);

        final TextView total_txt = (TextView)context.findViewById(R.id.total_txt);
        cBox.setText(dishname.get(position));
        double actualPrice = Double.parseDouble(dishprice.get(position))/100;
        cost.setText("$" + actualPrice);
        rating.setText(starRating[position]);

        cBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = cBox.isChecked();
                if (isChecked) {
                    qty.setText("1");

                } else {
                    qty.setText("0");

                }
                // Do something here.
            }
        });

        qty.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

                if (s.length() != 0 ) {

                    int sval = 0;

                    try
                    {
                        sval = Integer.parseInt(s.toString());
                        Log.i("beforetextchanged", "bef Value of textbox is " + sval);

                    }
                    catch (Exception e)
                    {
                        sval = 0;
                    }
                    total = total - (sval * (Double.parseDouble(dishprice.get(position))/100));
                    Log.i("beforetextchanged", "bef Value of total is " + total);
                }
                checkchanged = false;

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int sval = 0;
                if (s.length() != 0 ) {



                    try
                    {
                        sval = Integer.parseInt(s.toString());
                        Log.i("ontextchanged", "bef Value of textbox is " + sval);

                    }
                    catch (Exception e)
                    {
                        sval = 0;
                    }
                    total = total + (sval * (Double.parseDouble(dishprice.get(position))/100));
                    String tot_txt = "Total = $" + String.valueOf(total);

                    Log.i("ontextchanged", "on Value of total is " + total);

                    total_txt.setText(tot_txt);
                }

                countMap.put(requestIds.get(position), sval);


                checkchanged = false;
            }
        });


        return rowView;

    };

    public Integer getCount(String id) {
        if (countMap.containsKey(id)) {
            return countMap.get(id);
        }
        else {
            return 0;
        }
    }
}

