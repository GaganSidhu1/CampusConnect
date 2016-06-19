package com.gndec.campusconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class gndec extends AppCompatActivity {

    //URL to get JSON Array
    private static String url = "http://campusconnect.netii.net/api_campusconnect/login.php?roll=";

    //JSON Node Names
    private static final String slogin = "login";
    private static final String sstatus = "status";
    private static final String ssem = "semester";
    private static final String sname = "name";
    private static final String sroll = "college_roll_no";
    private static  int setheight = 0;

    String aaa;

    JSONArray login = null;

    EditText e,e1;
    Button b;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gndec);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height= dm.heightPixels;
        setheight = (int) (height*0.6);
        String d = String.valueOf(setheight);
        //Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();

       /* public static boolean isNetworkAvaliable(Context ctx) {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if ((connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                    || (connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED)) {
                return true;
            } else {
                return false;
            }
        }*/


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
           // Toast.makeText(getApplicationContext(),String.valueOf(connected),Toast.LENGTH_SHORT).show();

        }
        else {
             connected = false;
           //  Toast.makeText(getApplicationContext(),String.valueOf(connected),Toast.LENGTH_SHORT).show();
            /*Dialog dialog = new Dialog(gndec.this);
            dialog.setTitle("Network Error !!!");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.network_dialog_layout);
            dialog.show();
            Window window=dialog.getWindow();
            window.setLayout(450,550);*/
            AlertDialog.Builder dilog=new AlertDialog.Builder(gndec.this);
            LayoutInflater inflater=(LayoutInflater)gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.network_dialog_layout, null);

            dilog.setView(view);


            TextView title=new TextView(gndec.this);
            title.setText("Connection Error !!!");
            title.setBackgroundColor(Color.rgb(202,65,59));

            title.setPadding(10, 10, 10, 10);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.WHITE);
            title.setTextSize(20);

            dilog.setCustomTitle(title);
            final Dialog dialog=dilog.create();
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight );
            final Button dismis=(Button)dialog.findViewById(R.id.button_dismiss);

            dismis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                }
            });
        }

        e = (EditText) findViewById(R.id.editText);
        e1 = (EditText) findViewById(R.id.editText2);
        b = (Button) findViewById(R.id.button);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                    // Toast.makeText(getApplicationContext(),String.valueOf(connected),Toast.LENGTH_SHORT).show();

                    url = url + e.getText() + "&password=" + e1.getText();

                    new JSONParse().execute();
                } else {
                    connected = false;
                    //  Toast.makeText(getApplicationContext(),String.valueOf(connected),Toast.LENGTH_SHORT).show();
            /*Dialog dialog = new Dialog(gndec.this);
            dialog.setTitle("Network Error !!!");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.network_dialog_layout);
            dialog.show();
            Window window=dialog.getWindow();
            window.setLayout(450,550);*/
                    AlertDialog.Builder dilog = new AlertDialog.Builder(gndec.this);
                    LayoutInflater inflater = (LayoutInflater) gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view1 = inflater.inflate(R.layout.network_dialog_layout, null);

                    dilog.setView(view1);


                    TextView title = new TextView(gndec.this);
                    title.setText("Connection Error !!!");
                    title.setBackgroundColor(Color.rgb(202, 65, 59));

                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.WHITE);
                    title.setTextSize(20);

                    dilog.setCustomTitle(title);
                    final Dialog dialog = dilog.create();
                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight);
                    final Button dismis = (Button) dialog.findViewById(R.id.button_dismiss);

                    dismis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });
                }
            }
        });


    }

    class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialog = new ProgressDialog(gndec.this);
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            aaa=String.valueOf(json);
            Log.e("d", aaa);
            return json;
        }


        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();

            if(aaa.equals("null")) {
                /*final Dialog login_dialog2 = new Dialog(gndec.this);
                login_dialog2.setTitle("Login Error !!!");
                login_dialog2.setCanceledOnTouchOutside(false);
                login_dialog2.setContentView(R.layout.login_error_dialog_server);
                login_dialog2.show();
                Window window = login_dialog2.getWindow();
                window.setLayout(450, 550);*/

                AlertDialog.Builder dilog=new AlertDialog.Builder(gndec.this);
                LayoutInflater inflater=(LayoutInflater)gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view=inflater.inflate(R.layout.login_error_dialog_server, null);

                dilog.setView(view);


                TextView title=new TextView(gndec.this);
                title.setText("Login Error !!!");
                title.setBackgroundColor(Color.rgb(202,65,59));

                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(20);

                dilog.setCustomTitle(title);
                final Dialog login_dialog2=dilog.create();
                login_dialog2.show();
                login_dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight);
                Button l = (Button) login_dialog2.findViewById(R.id.button_login_dialog2);
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aaa="";
                        login_dialog2.dismiss();
                    }
                });
            }
                else {

               try {
                    // Getting JSON Array from URL


                    login = json.getJSONArray(slogin);
                    if (login.length() == 1) for (int i = 0; i < login.length(); i++) {
                        JSONObject c = login.getJSONObject(i);

                        // Storing  JSON item in a Variable
                        final String[] status = {c.getString(sstatus)};
                        aaa = status[0];
                        String semester = c.getString(ssem);
                        String name = c.getString(sname);
                        String college_roll_no = c.getString(sroll);

                        Log.i("a", status[0]);


                        if (status[0].equals("1")) {
                            Intent in = new Intent(getApplicationContext(), student.class);
                            in.putExtra("roll", e.getText().toString());
                            in.putExtra("sem", semester);
                            in.putExtra("name", name);
                            in.putExtra("roll", college_roll_no);
                            in.putExtra("password", e1.getText().toString());
                            startActivity(in);
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                            status[0] = "";
                            e.setText("");
                            e1.setText("");
                            finish();
                        }
                        if (status[0].equals("2")) {
                            //Toast.makeText(getApplicationContext(), "pass"+aaa, Toast.LENGTH_SHORT).show();
                            e.setText("");
                            e1.setText("");
                            status[0] = "";
                                /*final Dialog login_dialog = new Dialog(gndec.this);
                                login_dialog.setTitle("Login Error !!!");
                                login_dialog.setCanceledOnTouchOutside(false);
                                login_dialog.setContentView(R.layout.login_error_dialog);
                                login_dialog.show();
                                Window window = login_dialog.getWindow();
                                window.setLayout(450, 550);*/

                            AlertDialog.Builder dilog = new AlertDialog.Builder(gndec.this);
                            LayoutInflater inflater = (LayoutInflater) gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.login_error_dialog, null);

                            dilog.setView(view);


                            TextView title = new TextView(gndec.this);
                            title.setText("Login Error !!!");
                            title.setBackgroundColor(Color.rgb(202, 65, 59));

                            title.setPadding(10, 10, 10, 10);
                            title.setGravity(Gravity.CENTER);
                            title.setTextColor(Color.WHITE);
                            title.setTextSize(20);

                            dilog.setCustomTitle(title);
                            final Dialog login_dialog = dilog.create();
                            login_dialog.show();
                            login_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight);
                            Button l = (Button) login_dialog.findViewById(R.id.button_login_dialog);
                            l.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    aaa = "";
                                    login_dialog.dismiss();
                                }
                            });
                        }
                        if (status[0].equals("3")) {
                            // Toast.makeText(getApplicationContext(), "Login error"+aaa, Toast.LENGTH_SHORT).show();

                            e.setText("");
                            e1.setText("");


                            AlertDialog.Builder dilog = new AlertDialog.Builder(gndec.this);
                            LayoutInflater inflater = (LayoutInflater) gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.login_error_dialog_empty_field, null);

                            dilog.setView(view);


                            TextView title = new TextView(gndec.this);
                            title.setText("Login Error !!!");
                            title.setBackgroundColor(Color.rgb(202, 65, 59));

                            title.setPadding(10, 10, 10, 10);
                            title.setGravity(Gravity.CENTER);
                            title.setTextColor(Color.WHITE);
                            title.setTextSize(20);

                            dilog.setCustomTitle(title);
                            final Dialog login_dialog1 = dilog.create();
                            login_dialog1.show();
                            login_dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight);
                                /*final Dialog login_dialog1 = new Dialog(gndec.this);
                                login_dialog1.setTitle("Login Error !!!");
                                login_dialog1.setCanceledOnTouchOutside(false);
                                login_dialog1.setContentView(R.layout.login_error_dialog_empty_field);
                                login_dialog1.show();
                                Window window = login_dialog1.getWindow();
                                window.setLayout(450, 550);*/
                            Button l = (Button) login_dialog1.findViewById(R.id.button_login_dialog1);
                            l.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    aaa = "";
                                    status[0] ="";
                                    login_dialog1.dismiss();
                                }
                            });
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                    /*final Dialog login_dialog2 = new Dialog(gndec.this);
                    login_dialog2.setTitle("Login Error !!!");

                    login_dialog2.setCanceledOnTouchOutside(false);
                    login_dialog2.setContentView(R.layout.login_error_dialog_server);
                    login_dialog2.show();
                    Window window = login_dialog2.getWindow();
                    window.setLayout(450, 550);*/
                   AlertDialog.Builder dilog=new AlertDialog.Builder(gndec.this);
                   LayoutInflater inflater=(LayoutInflater)gndec.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   View view=inflater.inflate(R.layout.login_error_dialog_server, null);

                   dilog.setView(view);


                   TextView title=new TextView(gndec.this);
                   title.setText("Login Error !!!");
                   title.setBackgroundColor(Color.rgb(202,65,59));

                   title.setPadding(10, 10, 10, 10);
                   title.setGravity(Gravity.CENTER);
                   title.setTextColor(Color.WHITE);
                   title.setTextSize(20);

                   dilog.setCustomTitle(title);
                   final Dialog login_dialog2=dilog.create();
                   login_dialog2.show();
                   login_dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, setheight);
                    Button l = (Button) login_dialog2.findViewById(R.id.button_login_dialog2);
                    l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            aaa="";
                            login_dialog2.dismiss();
                        }
                    });
                }
            }
            }


    }


      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gndec, menu);
        return true;
    }


}
