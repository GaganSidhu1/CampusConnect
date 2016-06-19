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

public class sessional extends AppCompatActivity {

    ListView list;
    TextView subject_title;
    TextView marks_obtained;


    ArrayList<HashMap<String, String>> sessionallist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/result.php?roll=";

    //JSON Node Names
    private static final String sess = "result";
    private static final String sname = "subject_title";
    private static final String smarks= "marks_obtained";


    JSONArray result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessional);



        Intent in=getIntent();
        String roll=in.getStringExtra("roll");
        String id=in.getStringExtra("id");
        String sem=in.getStringExtra("sem");
        //Toast.makeText(getApplicationContext(),roll+id,Toast.LENGTH_SHORT).show();


        sessionallist = new ArrayList<HashMap<String, String>>();

        url=url+roll+"&id="+id+"&sem="+sem;

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            subject_title = (TextView) findViewById(R.id.textView10);
            marks_obtained = (TextView) findViewById(R.id.textView12);


            pDialog = new ProgressDialog(sessional.this);
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
                result = json.getJSONArray(sess);
                for (int i = 0; i < result.length(); i++) {
                    JSONObject c = result.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String subject_title = c.getString(sname);
                    String marks_obtained = c.getString(smarks);


                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(sname, subject_title);
                    map.put(smarks, marks_obtained );



                    sessionallist.add(map);
                    list = (ListView) findViewById(R.id.listView3);


                    ListAdapter adapter = new SimpleAdapter(sessional.this, sessionallist,
                            R.layout.layout2,
                            new String[]{sname, smarks}, new int[]{
                            R.id.textView10, R.id.textView12});

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(getApplicationContext(), "You Clicked at " + sessionallist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

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
        getMenuInflater().inflate(R.menu.menu_sessional, menu);
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
