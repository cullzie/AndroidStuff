package com.wotify.eantcul.wotify;

import com.wotify.eantcul.wotify.db.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
    private FeedReaderActions dbActions = new FeedReaderActions(mDbHelper);

    private ArrayList<String> listItems=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateAlarmView();

    }

    public void updateAlarmView(){
        ArrayList alarms = dbActions.get_alarms();
        ListView listview = (ListView) findViewById(R.id.alarmList);

        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listview.setAdapter(adapter);

        for(Object alarm: alarms){
            Log.d("DB STRINGS: ",  (String) alarm);
            listItems.add((String) alarm);
        }

        adapter.notifyDataSetChanged();
    }

    public void addAlarm(View view) {
        Intent intent = new Intent(this, CreateAlarmActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
