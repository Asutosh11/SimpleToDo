package com.example.asutosh.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {



    private HashMap<String, String> hm;
    private List<HashMap<String, String>> aList;
    private ImageView button5;
    private DatabaseHandler db2;
    private SimpleAdapter adapter;
    private List<Note> note;
    private ArrayList<String> MY_LIST;
    private String content;
    private int no_of_db_rows;
    private String[] from;
    private int[] to;
    private ListView listView;
    private String PREFS_NAME;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button5 = (ImageView)findViewById(R.id.button5);
        addpage();

        /* db2 is instance of DatabaseHandler class that is the class for SQLite database operations
                like creating database, adding items, etc */

        db2 = new DatabaseHandler(this);

        // Reading all contacts
        note = db2.getAllNotes();
        MY_LIST = new ArrayList<>();
        no_of_db_rows = 0;

        aList = new ArrayList<HashMap<String, String>>();
        for (Note n : note) {

            content = n.getMessage() ;
            hm = new HashMap<String, String>();
            hm.put("notesView", "" + content);
            aList.add(hm);
            MY_LIST.add(content);
            no_of_db_rows++;
        }

        from = new String[]{"notesView"};
        to = new int[]{R.id.notesView};
        adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_view_layout, from, to);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

       // delete a particular item on ListView on LongClick

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                // pos is position of item you click

                for (Note n : note) {

                    if (MY_LIST.get(pos) == n.getMessage())
                    {
                        db2.deleteNote(n);
                        aList.remove(pos);
                        break;
                    }
                }

                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Checks if db has more than 0 rows and Checks if the app is opened for 1st time.
        // If opened for 1st time and has at-least one note/work added, displays a toast.

        if(no_of_db_rows>0){
            PREFS_NAME = "MyPrefsFile";

            settings = getSharedPreferences(PREFS_NAME, 0);

            if (settings.getBoolean("my_first_time", true)) {
                //the app is being launched for first time, do something

                Toast.makeText(getApplicationContext(), "Long Press a note/work to delete it.",
                        Toast.LENGTH_LONG).show();

                // first time task
                // record the fact that the app has been started at least once
                settings.edit().putBoolean("my_first_time", false).commit();
            }
        }

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
