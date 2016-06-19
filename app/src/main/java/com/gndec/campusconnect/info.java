package com.gndec.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class info extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;


    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/information.php?roll_no=";

    //JSON Node Names
    private static final String pro = "profile";
    private static final String sname = "name";
    private static final String sfname = "father_name";
    private static final String smname = "mother_name";
    private static final String croll = "college_roll_no";
    private static final String uroll = "university_roll_no";
    private static final String ccode = "course_code";
    private static final String bcode = "branch_code";
    private static final String add = "address";
    private static final String em = "email";
    private static final String pnum = "phone_number";
    private static final String sem = "semester";
    private static final String fee = "fee_status";




    JSONArray profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent in = getIntent();
        String roll_no = in.getStringExtra("roll_no");
        String password = in.getStringExtra("password");
        //Toast.makeText(getApplicationContext(),roll_no+password , Toast.LENGTH_SHORT).show();


        url = url + roll_no + "&password=" + password;

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            t1 = (TextView) findViewById(R.id.textView31);
            t2 = (TextView) findViewById(R.id.textView32);
            t3 = (TextView) findViewById(R.id.textView33);
            t4 = (TextView) findViewById(R.id.textView34);
            t5 = (TextView) findViewById(R.id.textView35);
            t6 = (TextView) findViewById(R.id.textView36);
            t7 = (TextView) findViewById(R.id.textView37);
            t8 = (TextView) findViewById(R.id.textView38);
            t9 = (TextView) findViewById(R.id.textView39);
            t10 = (TextView) findViewById(R.id.textView40);
            t11= (TextView) findViewById(R.id.textView41);
            t12= (TextView) findViewById(R.id.textView42);


            pDialog = new ProgressDialog(info.this);
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
                profile = json.getJSONArray(pro);
                for (int i = 0; i < profile.length(); i++) {
                    JSONObject c = profile.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String name = c.getString(sname);
                    String father_name = c.getString(sfname);
                    String mother_name = c.getString(smname);
                    String college_roll_no = c.getString(croll);
                    String university_roll_no = c.getString(uroll);
                    String course_code = c.getString(ccode);
                    String branch_code = c.getString(bcode);
                    String address = c.getString(add);
                    String email = c.getString(em);
                    String phone_number = c.getString(pnum);
                    String semester = c.getString(sem);
                    String fee_status = c.getString(fee);

                    t1.setText(name);
                    t2.setText(father_name);
                    t3.setText(mother_name);
                    t4.setText(college_roll_no);
                    t5.setText(university_roll_no);
                    t6.setText(course_code);
                    t7.setText(branch_code);
                    t8.setText(address);
                    t9.setText(email);
                    t10.setText(phone_number);
                    t11.setText(semester);
                    t12.setText(fee_status);






                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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
