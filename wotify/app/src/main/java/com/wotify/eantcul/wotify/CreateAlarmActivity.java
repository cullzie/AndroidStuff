package com.wotify.eantcul.wotify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.wotify.eantcul.wotify.db.FeedReaderActions;
import com.wotify.eantcul.wotify.db.FeedReaderDbHelper;

import java.util.Calendar;

public class CreateAlarmActivity extends AppCompatActivity {

    private FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
    private FeedReaderActions dbActions = new FeedReaderActions(mDbHelper);
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TimePicker timePicker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_alarm);

        timePicker = (TimePicker) findViewById(R.id.digitalClock);
        timePicker.setIs24HourView(true);
        timePicker.is24HourView();

        Button set = (Button) findViewById(R.id.setTime);
        Button cancel = (Button) findViewById(R.id.cancelTime);

        set.setOnClickListener(setAlarm);
        cancel.setOnClickListener(cancelIt);

        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

    }

    View.OnClickListener setAlarm = new View.OnClickListener() {
        public void onClick(View view) {
            if (timePicker != null) {
                setA();
                finish();
            }
        }
    };

    View.OnClickListener cancelIt = new View.OnClickListener() {
        public void onClick(View view) {
            System.out.println("CANCELLING");
            finish();
        }
    };

    public void setA() {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        System.out.println("Alarm Time: " + hour + ":" + min);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        dbActions.create_alarm("Alarm", calendar, "MON");
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

}
