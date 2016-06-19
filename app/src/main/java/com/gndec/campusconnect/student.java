package com.gndec.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class student extends AppCompatActivity {
    ImageButton i1,i2,i3,i4,i5,i6;
    TextView t1,t2;

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
            setContentView(R.layout.activity_student);
        }
        else if (screenInches>5.5)
        {
            setContentView(R.layout.student_l);
        }


        Intent in=getIntent();
        final String roll=in.getStringExtra("roll");
        final String roll_no=in.getStringExtra("roll");
        final String sem=in.getStringExtra("sem");
        final String password=in.getStringExtra("password");
        String name=in.getStringExtra("name");
        String sroll=in.getStringExtra("roll");
        //Toast.makeText(getApplicationContext(),roll,Toast.LENGTH_SHORT).show();

        t1=(TextView)findViewById(R.id.textView71);
        t2=(TextView)findViewById(R.id.textView73);
        i1=(ImageButton)findViewById(R.id.imageButton);
        i2=(ImageButton)findViewById(R.id.imageButton2);
        i3=(ImageButton)findViewById(R.id.imageButton3);
        i4=(ImageButton)findViewById(R.id.imageButton4);
        i5=(ImageButton)findViewById(R.id.imageButton5);
        i6=(ImageButton)findViewById(R.id.imageButton6);

        t1.setText(name);
        t2.setText(sroll);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),attandence.class);
                in.putExtra("roll",roll);
                in.putExtra("sem",sem);
                startActivity(in);

            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(student.this,i2);
                popup.getMenuInflater().inflate(R.menu.pop1,popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Theory")) {
                            Intent in = new Intent(getApplicationContext(), theory.class);
                            in.putExtra("sem",sem);
                            in.putExtra("roll", roll);
                            startActivity(in);
                        }

                        if (menuItem.getTitle().equals("Practical")) {
                            Intent in = new Intent(getApplicationContext(), practical.class);
                            in.putExtra("roll", roll);
                            in.putExtra("sem",sem);
                            startActivity(in);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(student.this,i3);
                popup.getMenuInflater().inflate(R.menu.pop,popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Sessional 1")) {
                            Intent in = new Intent(getApplicationContext(), sessional.class);
                            in.putExtra("roll", roll);
                            in.putExtra("id","1");
                            in.putExtra("sem",sem);
                            startActivity(in);
                        }

                        else if (menuItem.getTitle().equals("Sessional 2")) {
                            Intent in = new Intent(getApplicationContext(), sessional.class);
                            in.putExtra("roll", roll);
                            in.putExtra("sem",sem);
                            in.putExtra("id","2");
                            startActivity(in);
                        }
                        else if (menuItem.getTitle().equals("Sessional 3")) {
                            Intent in = new Intent(getApplicationContext(), sessional.class);
                            in.putExtra("roll", roll);
                            in.putExtra("sem",sem);
                            in.putExtra("id","3");
                            startActivity(in);
                        }
                        return true;
                    }

                });
                popup.show();
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),info.class);
                in.putExtra("roll_no",roll_no);
                in.putExtra("password",password);
                startActivity(in);
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), event.class);
                startActivity(in);

            }
        });

        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(),gndec.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student, menu);
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
