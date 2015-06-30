package com.example.asutosh.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    Button button5;
    DatabaseHandler db2;
    ArrayAdapter<String> adapter;
    int iid;
    List<Note> note;
    ArrayList<String> MY_LIST;
    String content;
    int no_of_db_rows; // used to count number of rows in database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button5 = (Button)findViewById(R.id.button5);
        addpage();

        /* db2 is instance of DatabaseHandler class that is the class for SQLite database operations
                like creating database, adding items, etc */

        db2 = new DatabaseHandler(this);

        // Reading all contacts

       note = db2.getAllNotes();
        MY_LIST = new ArrayList<>();

        no_of_db_rows = 0;

        for (Note n : note) {

            content = n.getMessage() ;
            MY_LIST.add(content);

            no_of_db_rows++;
        }

        // <add MY_LIST to ListView adapter start>

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, MY_LIST);


        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        // < /add MY_LIST to ListView adapter over >



        // < delete a particular item on ListView on LongClick start >

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                // pos is position of item you click

                for (Note n : note) {

                    if (MY_LIST.get(pos) == n.getMessage())
                    {
                        db2.deleteNote(n);
                        MY_LIST.remove(pos);
                        break;
                    }

                }

                adapter.notifyDataSetChanged();

                return true;
            }
        });

        // < /delete a particular item on ListView on LongClick over >



        // < Checks if db has more than 0 rows and Checks if the app is opened for 1st time.>


        // If opened for 1st time and has at-least one note/work added, displays a toast.

        if(no_of_db_rows>0){
            final String PREFS_NAME = "MyPrefsFile";

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

            if (settings.getBoolean("my_first_time", true)) {
                //the app is being launched for first time, do something

                Toast.makeText(getApplicationContext(), "Long Press a note/work to delete it.",
                        Toast.LENGTH_LONG).show();

                // first time task

                // record the fact that the app has been started at least once
                settings.edit().putBoolean("my_first_time", false).commit();
            }
        }

        // < /Checks if db has more than 0 rows and Checks if the app is opened for 1st time.>

    }


    public void addpage(){
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(),AddToDo.class);
                startActivity(j);
            }
        });
    }

}
