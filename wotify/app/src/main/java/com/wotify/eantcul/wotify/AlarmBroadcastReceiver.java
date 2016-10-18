package com.wotify.eantcul.wotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Don't panic but your time is up!!!!.",
                Toast.LENGTH_LONG).show();

        intent = new Intent(context, CreateAlarmActivity.class);
        context.startService(intent);
    }
}
