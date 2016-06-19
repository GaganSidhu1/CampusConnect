package com.gndec.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class attandence extends AppCompatActivity {
    ListView list1,list2;
    TextView subject_title;
    TextView attendance;
    TextView total_lecturers;
    TextView percentage;
    Float ab;

    ArrayList<HashMap<String, String>> attandencetlist = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> attandenceplist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/attendance_theory.php?roll=";
    private static String url1 = "http://campusconnect.netii.net/api_campusconnect/attendance_practical.php?roll=";

    //JSON Node Names
    private static final String att = "attendance_theory";
    private static final String att1 = "attendance_practical";
    private static final String sname = "subject_title";
    private static final String sattended= "attendance";
    private static final String stotal = "total_lecturers";
    private static final String per="percentage";

    JSONArray attendance_theory = null;
    JSONArray attendance_practical = null;


    double screenInches;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        screenInches = Math.sqrt(x+y);
        //Toast.makeText(getApplicationContext(),String.valueOf(screenInches),Toast.LENGTH_LONG).show();

        if(screenInches <5.5){
            setContentView(R.layout.activity_attandence);
        }else if(screenInches >5.5){
            //do smoothing here
            setContentView(R.layout.attandance_l);

        }


        Intent in=getIntent();
        String roll=in.getStringExtra("roll");
        String sem=in.getStringExtra("sem");
        Log.i("bn", sem);

       // Toast.makeText(getApplicationContext(),roll+sem,Toast.LENGTH_SHORT).show();

        attandencetlist = new ArrayList<HashMap<String, String>>();
        attandenceplist = new ArrayList<HashMap<String, String>>();
        url=url+roll+"&sem="+sem;
        url1=url1+roll+"&sem="+sem;


        new JSONParse().execute();
        new JSONParse1().execute();
     /*   public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0) {
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
                }
                view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }*/
      /*  if(ab<75)
        {
            Log.i("az",String.valueOf(ab));
            percentage.setTextColor(Color.parseColor("#ca413b"));
        }*/
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            subject_title = (TextView) findViewById(R.id.textView6);
            attendance = (TextView) findViewById(R.id.textView7);
            total_lecturers = (TextView) findViewById(R.id.textView8);
            percentage =(TextView)findViewById(R.id.textView9);

            pDialog = new ProgressDialog(attandence.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                attendance_theory = json.getJSONArray(att);
                for (int i = 0; i < attendance_theory.length(); i++) {
                    JSONObject c = attendance_theory.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String subject_title = c.getString(sname);
                    String attendance = c.getString(sattended);
                    String total_lecturers = c.getString(stotal);
                    Float ab=(Float.valueOf(attendance)/ Float.valueOf(total_lecturers))*100;



                    DecimalFormat form = new DecimalFormat("0.00");
                    String f=(form.format(ab));
                    String a= String.valueOf(f);



                    // Adding value HashMap key => value


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(sname, subject_title);
                    map.put(sattended,attendance );
                    map.put(stotal, total_lecturers);
                    map.put(per,a);


                    attandencetlist.add(map);
                    list1 = (ListView) findViewById(R.id.listView);


                    if(screenInches<5.5)
                    {
                        ListAdapter adapter = new SimpleAdapter(attandence.this, attandencetlist,
                                R.layout.layout,
                                new String[]{sname, sattended, stotal,per}, new int[]{
                                R.id.textView6, R.id.textView7, R.id.textView8,R.id.textView9});

                        list1.setAdapter(adapter);
                    }
                    else if(screenInches>5.5)
                    {
                        ListAdapter adapter = new SimpleAdapter(attandence.this, attandencetlist,
                                R.layout.layout_l,
                                new String[]{sname, sattended, stotal,per}, new int[]{
                                R.id.textView6, R.id.textView7, R.id.textView8,R.id.textView9});

                        list1.setAdapter(adapter);
                    }



                    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                           // Toast.makeText(getApplicationContext(), "You Clicked at " + attandencetlist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class JSONParse1 extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            subject_title = (TextView) findViewById(R.id.textView6);
            attendance = (TextView) findViewById(R.id.textView7);
            total_lecturers = (TextView) findViewById(R.id.textView8);
            percentage = (TextView) findViewById(R.id.textView9);

            pDialog = new ProgressDialog(attandence.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url1);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                attendance_practical = json.getJSONArray(att1);
                for (int i = 0; i < attendance_practical.length(); i++) {
                    JSONObject c = attendance_practical.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String subject_title = c.getString(sname);
                    String attendance = c.getString(sattended);
                    String total_lecturers = c.getString(stotal);
                     ab = (Float.valueOf(attendance) / Float.valueOf(total_lecturers)) * 100;


                    DecimalFormat form = new DecimalFormat("0.00");
                    String f=(form.format(ab));
                    String a = String.valueOf(f);

                    // Adding value HashMap key => value


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(sname, subject_title);
                    map.put(sattended, attendance);
                    map.put(stotal, total_lecturers);
                    map.put(per, a);


                    attandenceplist.add(map);
                    list2 = (ListView) findViewById(R.id.listView2);


                    if(screenInches<5.5)
                    {
                        ListAdapter adapter = new SimpleAdapter(attandence.this, attandenceplist,
                                R.layout.layout,
                                new String[]{sname, sattended, stotal,per}, new int[]{
                                R.id.textView6, R.id.textView7, R.id.textView8,R.id.textView9});

                        list2.setAdapter(adapter);
                    }
                    else if(screenInches>5.5)
                    {
                        ListAdapter adapter = new SimpleAdapter(attandence.this, attandenceplist,
                                R.layout.layout_l,
                                new String[]{sname, sattended, stotal,per}, new int[]{
                                R.id.textView6, R.id.textView7, R.id.textView8,R.id.textView9});

                        list2.setAdapter(adapter);
                    }

                    list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                          //  Toast.makeText(getApplicationContext(), "You Clicked at " + attandenceplist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                        }
                    });
                    /*percentage.findViewById(R.id.textView9);
                    String s=percentage.getText().toString();
                    if(Float.valueOf(s)>75.00)
                    {
                        Log.i("s",String.valueOf(ab));

                        // percentage.setTextColor(Color.RED);
                    }
                    else if(Float.valueOf(s)<75.00)
                    {
                        percentage.setTextColor(Color.GREEN);
                    }*/
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
