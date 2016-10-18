package com.wotify.eantcul.wotify.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class FeedReaderActions {
    FeedReaderDbHelper dbConnection;

    public FeedReaderActions(FeedReaderDbHelper mDbHelper){
        this.dbConnection = mDbHelper;
    }

    public void create_alarm(String name, Calendar calendar, String day){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        String uniqueID = UUID.randomUUID().toString();

        values.put(FeedReaderSchema.FeedEntry.ALARM_NAME, name + " " + uniqueID);
        values.put(FeedReaderSchema.FeedEntry.ALARM_TIME, (new SimpleDateFormat("HH:mm")).format(calendar.getTime()));
        values.put(FeedReaderSchema.FeedEntry.ALARM_DAYS, day);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderSchema.FeedEntry.TABLE_NAME, null,
                values);
        Log.d("Added alarm", "Added alarm to db: " + newRowId);
        db.close();
    }

    public void delete_alarm(String name){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderSchema.FeedEntry.ALARM_NAME, name);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderSchema.FeedEntry.TABLE_NAME, null,
                values);
        Log.d("Added alarm", "Added alarm to db: " + newRowId);
        db.close();
    }

    public ArrayList<String> get_alarms(){

        SQLiteDatabase db = dbConnection.getReadableDatabase();

        String selection = "";
        String[] selectionArgs = new String[] {};

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderSchema.FeedEntry.ALARM_NAME,
                FeedReaderSchema.FeedEntry.ALARM_TIME,
                FeedReaderSchema.FeedEntry.ALARM_DAYS
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderSchema.FeedEntry.ALARM_TIME + " DESC";

        Cursor c = db.query(
                FeedReaderSchema.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<String> values = new ArrayList<>();
        Log.d("COUNT: ", Integer.toString(c.getCount()));
        if (!c.moveToFirst())
            c.moveToFirst();

        while(c.moveToNext()){
            String time = c.getString(c.getColumnIndex(FeedReaderSchema.FeedEntry.ALARM_TIME));
            String day = c.getString(c.getColumnIndex(FeedReaderSchema.FeedEntry.ALARM_DAYS));
            String name =  c.getString(c.getColumnIndex(FeedReaderSchema.FeedEntry.ALARM_NAME));
            String alarm = name + "||"+ time + "||" + day;
            values.add(alarm);
        }

        c.close();

        return values;
    }

}
