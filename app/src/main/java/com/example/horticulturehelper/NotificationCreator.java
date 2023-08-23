package com.example.horticulturehelper;

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

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationCreator extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1";
//    private MainActivity mainActivity = new MainActivity();
//    PlantDatabase plantDatabase = PlantDatabase.getInstance(mainActivity.getApplicationContext());

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("hzz","setReminder33");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Random r = new Random();
//        int randomNumber = r.nextInt(175 - 18) + 18;
//        String plantName = intent.getStringExtra("plantName");
        Plant plant = (Plant) intent.getSerializableExtra("plant");
        int randomNumber = (int) (Math.random()*10000);
        String eventName = intent.getStringExtra("eventName");
        String plantName = plant.getPlantName();
        Log.d("notif obj","plantobj: "+ plant.getPlantName()+"     "+"plantId"+plant.getId()+"     "+"plantingDate"+plant.getPlantingDate());

        Intent updateIntent = new Intent(context, UpdatePlantActivity.class);
        updateIntent.putExtra("plant", plant);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(context, randomNumber, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent doneIntent = new Intent(context, DoneButtonReceiver.class);
        doneIntent.putExtra("plant", plant);
        doneIntent.putExtra("eventName", eventName);

        PendingIntent donePendingIntent = PendingIntent.getBroadcast(context, randomNumber, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        mainActivity.setAllPlantReminders(plant);

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

        builder.addAction(R.drawable.action_button_icon, "DONE",donePendingIntent);
        builder.addAction(R.drawable.action_button_icon, "Update plant",updatePendingIntent);

        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(randomNumber,builder.build());
        Log.d("hzz","notificatio id: "+ randomNumber+" randomNo: "+randomNumber);

    }

}
