package com.example.horticulturehelper;

import static java.sql.Types.NULL;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Date;


public class DoneButtonReceiver extends BroadcastReceiver {
    private static Plant plantFromIntent;
    private static Plant plantFromDb;
    private String eventName;
    private Context context1;
    private long millis;
    private int eventNumber;
    private UpdatePlantActivity updatePlantActivity = new UpdatePlantActivity();
    private PlantRepository plantRepository;


    @Override
    public void onReceive(Context context, Intent intent) {
        context1 = context;
        plantFromIntent = (Plant) intent.getSerializableExtra("plant");
        eventName = intent.getStringExtra("eventName");
        plantRepository = new PlantRepository(updatePlantActivity.getApplication());
        plantFromDb = plantRepository.getPlantById(plantFromIntent.getId());
        //getting plant object from DB by ID, because it needs updated attributes

        switch (eventName) {
            case "to plant":
                plantFromIntent.setIsPlanted(true);
                updatePlantActivity.setAllRemainingReminders(plantFromIntent, context1, "doneBtnRec");
                updatePlantActivity.updateDbEntity(plantFromIntent, context1);
                break;
            case "to water":
                millis = System.currentTimeMillis() + 86400000L * plantFromDb.getWateringPeriodInDays();
                plantFromDb.setWateringDate(new Date(millis));
                eventNumber = 2;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to fertilize":
                millis = System.currentTimeMillis() + 86400000L * plantFromDb.getFertilizingPeriodInDays();
                plantFromDb.setFertilizingDate(new Date(millis));
                eventNumber = 3;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to monitor":
                millis = System.currentTimeMillis() + 86400000L * plantFromDb.getMonitoringPeriodInDays();
                plantFromDb.setMonitoringDate(new Date(millis));
                eventNumber = 4;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to harvest":
                plantFromDb.setWateringDate(new Date(NULL));
                plantFromDb.setFertilizingDate(new Date(NULL));
                plantFromDb.setMonitoringDate(new Date(NULL));
                plantFromDb.setHarvestingDate(new Date(NULL));
                plantFromDb.setIsPlanted(false);
                eventNumber = 4;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);

                Intent cancelingIntent = new Intent(context1, NotificationCreator.class);
                alarmCanceling(cancelingIntent, context1, plantFromDb);
                break;
        }
    }

    protected void alarmCanceling(Intent cancelingIntent, Context context, Plant plantCancel) {
        PendingIntent pendingIntent;
        AlarmManager alarmManager;

        for (int eventNo = 2; eventNo < 6; eventNo++){

            int reqCode = plantCancel.getId() * 100 + eventNo;
            pendingIntent = PendingIntent.getBroadcast(context,
                    reqCode, cancelingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setSingleReminder(){
        Intent intent1 = new Intent(context1, NotificationCreator.class);
        intent1.putExtra("plant", plantFromIntent);
        intent1.putExtra("eventName", eventName);
        int reqCode = plantFromIntent.getId() * 100 + eventNumber;
        updatePlantActivity.setAlarm(context1, intent1, reqCode, millis);
        }

}
