package com.example.horticulturehelper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;

public class NotificationCreator extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Plant plant = (Plant) intent.getSerializableExtra("plant");
        //creating randomNumber for notification id
        int randomNumber = (int) (Math.random()*10000);
        String eventName = intent.getStringExtra("eventName");
        String plantName = plant.getPlantName();

        Intent updateIntent = new Intent(context, UpdatePlantActivity.class);
        updateIntent.putExtra("plant", plant);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(context, randomNumber, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent doneIntent = new Intent(context, DoneButtonReceiver.class);
        doneIntent.putExtra("plant", plant);
        doneIntent.putExtra("eventName", eventName);

        PendingIntent donePendingIntent = PendingIntent.getBroadcast(context, randomNumber, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        Notification.Builder builder = new Notification.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText("It's time " + eventName + " " + plantName);

        if (eventName.equals("to fertilize") && calendar.get(Calendar.MONTH) < 7)
        builder.setStyle(new Notification.BigTextStyle().bigText("It's time " + eventName + " " + plantName + ".\nRecommended fertilizer:\n" + plant.getSpringFertilizer()));

        else if (eventName.equals("to fertilize") && calendar.get(Calendar.MONTH) > 6) {
            builder.setStyle(new Notification.BigTextStyle().bigText("It's time " + eventName + " " + plantName + ".\nRecommended fertilizer:\n" + plant.getSummerFertilizer()));
        }
        if (eventName.equals("to monitor")) {
            builder.setStyle(new Notification.BigTextStyle().bigText("It's time " + eventName + " " + plantName + ".\nRecommended protection:\n" + plant.getProtection()));
        }
        if (eventName.equals("to plant"))
            builder.setStyle(new Notification.BigTextStyle().bigText("It's time " + eventName + " " + plantName + ".\nBad companion:\n" + plant.getBadCompanion()));


        builder.addAction(R.drawable.action_button_icon, "DONE",donePendingIntent);
        builder.addAction(R.drawable.action_button_icon, "Update plant",updatePendingIntent);

        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(randomNumber,builder.build());

    }

}
