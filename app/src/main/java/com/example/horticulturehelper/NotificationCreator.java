package com.example.horticulturehelper;

import static android.content.Intent.getIntent;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationCreator extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("hzz","setReminder33");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

//        Intent contentIntent = new Intent(context, AddPlantActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE);

//        Intent updateIntent = new Intent(context, UpdatePlantActivity.class);
//        updateIntent.putExtra("plant", plant);
//        PendingIntent updatePendingIntent = PendingIntent.getActivity(context, 0, updateIntent, PendingIntent.FLAG_IMMUTABLE);

        int plantId = intent.getIntExtra("plantId",-1);
        String plantName = intent.getStringExtra("plantName");
        Notification.Builder builder = new Notification.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText("It's time to plant " + plantName);
//        builder.addAction(R.drawable.action_button_icon, "Action button name",updatePendingIntent);


        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(1,builder.build());
        Log.d("hzz","setReminder34");

    }
}
