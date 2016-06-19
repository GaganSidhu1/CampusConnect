package com.gndec.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class event extends AppCompatActivity {

    ListView list;
    TextView description,title,evn,id;

    String a;


    ArrayList<HashMap<String, String>> eventlist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/events.php";

    //JSON Node Names
    private static final String evnt = "events";
    private static final String des = "description";
    private static final String evntid= "event_id";
    private static final String tit= "title";
    private static final String tim= "time";


    JSONArray events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventlist = new ArrayList<HashMap<String, String>>();


        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            description = (TextView) findViewById(R.id.textView25);
            title = (TextView) findViewById(R.id.textView24);
            evn = (TextView) findViewById(R.id.textView55);
            id = (TextView) findViewById(R.id.textView57);


            pDialog = new ProgressDialog(event.this);
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
                events = json.getJSONArray(evnt);
                for (int i = 0; i < events.length(); i++) {
                    JSONObject c = events.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String description = c.getString(des);
                    String event_id = c.getString(evntid);
                    String title = c.getString(tit);
                    String time = c.getString(tim);

                   a=event_id;
                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(des, description);
                    map.put(tit, title );
                    map.put(evntid,event_id);
                    map.put(tim,time);




                    eventlist.add(map);
                    list = (ListView) findViewById(R.id.listView5);


                    ListAdapter adapter = new SimpleAdapter(event.this, eventlist,
                            R.layout.layout4,
                            new String[]{tit, des, tim, evntid}, new int[]{
                            R.id.textView24, R.id.textView25, R.id.textView55, R.id.textView57});
//                    findViewById(R.id.textView57).setVisibility(View.GONE);


                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            String s=null;
                            Intent in = new Intent(getApplicationContext(),notice.class);
                            s=(String) ((TextView) view.findViewById(R.id.textView57)).getText();
                            in.putExtra("event_id",s);
                            startActivity(in);
                        }
                    });
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
