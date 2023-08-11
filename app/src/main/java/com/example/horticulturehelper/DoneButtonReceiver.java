package com.example.horticulturehelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DoneButtonReceiver extends BroadcastReceiver {
    Plant plant;
    String eventName;
    PlantDatabase plantDatabase;
    Context context1;
    String str;
    @Override
    public void onReceive(Context context, Intent intent) {
        context1 = context;
        plant = (Plant) intent.getSerializableExtra("plant");
        eventName = intent.getStringExtra("eventName");
        plantDatabase = PlantDatabase.getInstance(context);

//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                str = String.valueOf(plantDatabase.plantDao().getLastPlantId());
//                Log.d("hzdb", str);
//            }
//        });
//
//        if (eventName.equals("to plant")) {
            setAllRemainingReminders();
//            updatePlantEventDates();
//        } else
//            updatePlantCurrentEventDate();
//    }
//
//    private void updatePlantCurrentEventDate() {
//    }
//
//    private void updatePlantEventDates() {
//
    }

    void setAllRemainingReminders() {

        Intent intent1 = new Intent(context1, NotificationCreator.class);
        intent1.putExtra("plant", plant);
        long millis ;
        int reqCode = 0;
        for (int i = 2; i < 6; i++){
            switch(i) {
                case 2:
                    intent1.putExtra("eventName", "to water");
                    reqCode = plant.getId()*100+i;
                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getWateringPeriodInDays();
                    setAlarm(intent1, reqCode, millis);
                    break;
                case 3:
                    intent1.putExtra("eventName", "to fertilize");
                    reqCode = plant.getId()*100+i;
                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getFertilizingPeriodInDays();
                    setAlarm(intent1, reqCode, millis);
                    break;
                case 4:
                    intent1.putExtra("eventName", "to monitor");
                    reqCode = plant.getId()*100+i;
                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getMonitoringPeriodInDays();
                    setAlarm(intent1, reqCode, millis);
                    break;
                case 5:
                    intent1.putExtra("eventName", "to harvest");
                    reqCode = plant.getId()*100+i;
                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getVegetationPeriodInDays();
                    setAlarm(intent1, reqCode, millis);
                    break;

                default:
                // code block
            }
    }


//        int reqCode = lastPlantId * 100 + 1;
//        Log.d("AddPlantActivity: reqCode = lastPlantId * 100 + 1 = ", String.valueOf(String.valueOf(reqCode)));

    }
    void setAlarm(Intent intent, int reqCode, long millis) {
        PendingIntent pendingIntent;

            pendingIntent = PendingIntent.getBroadcast(context1,
                    reqCode, intent, PendingIntent.FLAG_IMMUTABLE);



        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        Log.d("donebutt obj: ", plant.getPlantName() + "    " + millis);
    }
}
