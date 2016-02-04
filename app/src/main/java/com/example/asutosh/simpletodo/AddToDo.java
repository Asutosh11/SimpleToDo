package com.example.asutosh.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddToDo extends Activity {

    private Button button;
    private Button button2;
    private EditText edtxt;
    private DatabaseHandler db;
    private Calendar c;
    private SimpleDateFormat sdf;
    private String strDate;
    private String theText;
    private Intent i, k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        // Database things start
        db = new DatabaseHandler(this);

        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("hh:mm a - dd MMMM, yyyy");
        strDate = sdf.format(c.getTime());

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        edtxt = (EditText) findViewById(R.id.edtxt);

        done();
        goback();

    }

    public void done(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theText = edtxt.getText().toString();
                db.addContact(new Note(theText, strDate));

                i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }


    public void goback(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                k = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(k);
            }
        });

    }


}
