package com.gndec.campusconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;


public class notice extends AppCompatActivity {

    TextView t1,t2;
    Button b1,b2;
    String file1,file2;


    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/notification.php?event_id=";

    //JSON Node Names
    private static final String evnt = "events";
    private static final String tit = "title";
    private static final String des = "description";
    private static final String file = "file_attached";





    JSONArray events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Intent in = getIntent();
        String event_id = in.getStringExtra("event_id");
        //Toast.makeText(getApplicationContext(),event_id, Toast.LENGTH_SHORT).show();


        url = url + event_id;

        //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        new JSONParse().execute();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getApplicationContext(),file1,Toast.LENGTH_SHORT).show();
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://campusconnect.netii.net/files/"+file1));
                startActivity(browser);

                           /* WebView webView = (WebView) findViewById(R.id.webview);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setPluginsEnabled(true);
                            webView.loadUrl("https://docs.google.com/viewer?"+pdf_url);*/
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),file1,Toast.LENGTH_SHORT).show();
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://campusconnect.netii.net/files/"+file2));


                startActivity(browser);


            }
        });


    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            t1 = (TextView) findViewById(R.id.textView26);
            t2 = (TextView) findViewById(R.id.textView29);
            b1 = (Button) findViewById(R.id.button2);
            b2 = (Button) findViewById(R.id.button3);


            pDialog = new ProgressDialog(notice.this);
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
                    String title = c.getString(tit);
                    String description = c.getString(des);
                    String file_attached = c.getString(file);

                    //String[] items=file_attached.split(",");


                    t1.setText(title);
                    t2.setText(description);
                    if(file_attached.isEmpty())
                    {
                        b1.setText("");
                        b1.setClickable(false);
                        b2.setText("");
                        b2.setClickable(false);
                    }
                    else if (file_attached.contains(","))
                    {
                        StringTokenizer tokens = new StringTokenizer(file_attached,",");
                        file1=tokens.nextToken();
                        file2=tokens.nextToken();
                        b1.setText(file1);
                        b2.setText(file2);
                    }
                    else
                    {
                        file1=file_attached;
                        b1.setText(file_attached);
                        Log.e("file",file1);
                        b2.setText(null);

                    }


                }
                url="http://campusconnect.netii.net/api_campusconnect/notification.php?event_id=";

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notice, menu);
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
