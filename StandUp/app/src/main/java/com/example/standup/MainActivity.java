package com.example.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private NotificationManager mNotificationManager;

    private AlarmManager alarmManager;

    private PendingIntent notifyPendingIntent;

    private static final int NOTIFICATION_ID = 0;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private long triggerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);
        alarmToggle.setOnCheckedChangeListener(this);

        findViewById(R.id.show_button).setOnClickListener(this);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String toastMessage;
        if (isChecked) {
            toastMessage = "Stand Up Alarm On!";
            long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval, notifyPendingIntent);
            }
        } else {
            toastMessage = "Stand Up Alarm Off!";
            mNotificationManager.cancelAll();
            if (alarmManager != null) {
                alarmManager.cancel(notifyPendingIntent);
            }
        }
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel =
                new NotificationChannel(PRIMARY_CHANNEL_ID,
                        "Stand up notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.show_button) {
            LocalDateTime localDateTime =
                    LocalDateTime.ofEpochSecond(triggerTime / 1000,
                            0, ZoneOffset.ofHours(8));
            Toast.makeText(MainActivity.this, localDateTime.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}