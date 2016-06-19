package com.gndec.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class theory extends AppCompatActivity {

    ListView list;
    TextView subject_title;
    TextView paper_id;
    TextView marks_external;
    TextView marks_internal;
    TextView result_type;
    TextView subject_code;
    ArrayList<HashMap<String, String>> resultlist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/semester_result.php?roll=";

    //JSON Node Names
    private static final String sem = "sem_result";
    private static final String sname = "subject_title";
    private static final String spaper= "paper_id";
    private static final String smarksi = "marks_internal";
    private static final String smarkse="marks_external";
    private static final String stype = "result_type";
    private static final String scode="subject_code";
    private static final String stp="theory_practical";

    JSONArray sem_result = null;

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

        if(screenInches<5.5)
        {
            setContentView(R.layout.activity_theory);
        }
        else if(screenInches>5.5)
        {
            setContentView(R.layout.theory_l);
        }


        resultlist = new ArrayList<HashMap<String, String>>();

        Intent in=getIntent();
        String roll=in.getStringExtra("roll");
        String sem=in.getStringExtra("sem");

        url=url+roll+"&sem="+sem;

        new JSONParse().execute();
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            subject_title = (TextView) findViewById(R.id.textView18);
            paper_id = (TextView) findViewById(R.id.textView19);
            marks_internal = (TextView) findViewById(R.id.textView20);
            marks_external =(TextView)findViewById(R.id.textView21);
            result_type =(TextView)findViewById(R.id.textView22);
            subject_code =(TextView)findViewById(R.id.textView23);

            pDialog = new ProgressDialog(theory.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
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
                sem_result = json.getJSONArray(sem);
                for (int i = 0; i < sem_result.length(); i++) {
                    JSONObject c = sem_result.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String subject_title = c.getString(sname);
                    String paper_id = c.getString(spaper);
                    String marks_external = c.getString(smarksi);
                    String marks_internal = c.getString(smarkse);
                    String result_type = c.getString(stype);
                    String subject_code = c.getString(scode);
                    String theory_practical = c.getString(stp);


                    // Adding value HashMap key => value
                    if(theory_practical.equals("T")) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(sname, subject_title);
                        map.put(spaper, paper_id);
                        map.put(smarksi, marks_external);
                        map.put(smarkse, marks_internal);
                        map.put(stype, result_type);
                        map.put(scode, subject_code);


                        resultlist.add(map);
                        list = (ListView) findViewById(R.id.listView4);


                        if(screenInches<5.5)
                        {
                            ListAdapter adapter = new SimpleAdapter(theory.this, resultlist,
                                    R.layout.layout3,
                                    new String[]{sname, spaper, smarksi, smarkse, stype, scode}, new int[]{
                                    R.id.textView18, R.id.textView19, R.id.textView20, R.id.textView21, R.id.textView22, R.id.textView23});

                            list.setAdapter(adapter);
                        }
                        else if(screenInches>5.5)
                        {
                            ListAdapter adapter = new SimpleAdapter(theory.this, resultlist,
                                    R.layout.layout3_l,
                                    new String[]{sname, spaper, smarksi, smarkse, stype, scode}, new int[]{
                                    R.id.textView18, R.id.textView19, R.id.textView20, R.id.textView21, R.id.textView22, R.id.textView23});

                            list.setAdapter(adapter);
                        }
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                              //  Toast.makeText(getApplicationContext(), "You Clicked at " + resultlist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theory, menu);
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
