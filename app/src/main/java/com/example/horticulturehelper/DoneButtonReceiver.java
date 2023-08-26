package com.example.horticulturehelper;

import static java.sql.Types.NULL;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
                millis = System.currentTimeMillis() + 60000 * plantFromDb.getWateringPeriodInDays();
                plantFromDb.setWateringDate(new Date(millis));
                eventNumber = 2;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to fertilize":
                millis = System.currentTimeMillis() + 60000 * plantFromDb.getFertilizingPeriodInDays();
                plantFromDb.setFertilizingDate(new Date(millis));
                eventNumber = 3;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to monitor":
                millis = System.currentTimeMillis() + 60000 * plantFromDb.getMonitoringPeriodInDays();
                plantFromDb.setMonitoringDate(new Date(millis));
                eventNumber = 4;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                setSingleReminder();
                break;
            case "to harvest":
//                millis = System.currentTimeMillis() + 60000 * plant.getMonitoringPeriodInDays();
                plantFromDb.setWateringDate(new Date(NULL));
                plantFromDb.setFertilizingDate(new Date(NULL));
                plantFromDb.setMonitoringDate(new Date(NULL));
                plantFromDb.setHarvestingDate(new Date(NULL));
                plantFromDb.setIsPlanted(false);
                eventNumber = 4;
                updatePlantActivity.updateDbEntity(plantFromDb, context1);
                Intent cancelingIntent = new Intent(context1, NotificationCreator.class);

                alarmCanceling(cancelingIntent, context1, plantFromDb);
                Log.d("harvest", plantFromDb.getPlantName());
                break;
        }
    }

    protected void alarmCanceling(Intent cancelingIntent, Context context, Plant plantCancel) {
        Log.d("alarmCanceling", "started");
        PendingIntent pendingIntent;
        AlarmManager alarmManager;

        for (int eventNo = 2; eventNo < 6; eventNo++){
            Log.d("cancelAlarm0", "reqCode: "+" eventNo "+eventNo );

            int reqCode = plantCancel.getId() * 100 + eventNo;
            Log.d("cancelAlarm", "reqCode: "+reqCode+" eventNo "+eventNo );
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

//            setEventsDatesInDb();

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


//    private void setEventsDatesInDb() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                plant.setIsPlanted(true);
//                plantDatabase.plantDao().update(plant);
//
//            }
//        });
//
//    }

//    void setAllRemainingReminders() {
//
//        Intent intent1 = new Intent(context1, NotificationCreator.class);
//        intent1.putExtra("plant", plant);
//        long millis ;
//        int reqCode = 0;
//        for (int i = 2; i < 6; i++){
//            switch(i) {
//                case 2:
//                    intent1.putExtra("eventName", "to water");
//                    reqCode = plant.getId()*100+i;
//                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getWateringPeriodInDays();
//                    setAlarm(intent1, reqCode, millis);
//                    break;
//                case 3:
//                    intent1.putExtra("eventName", "to fertilize");
//                    reqCode = plant.getId()*100+i;
//                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getFertilizingPeriodInDays();
//                    setAlarm(intent1, reqCode, millis);
//                    break;
//                case 4:
//                    intent1.putExtra("eventName", "to monitor");
//                    reqCode = plant.getId()*100+i;
//                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getMonitoringPeriodInDays();
//                    setAlarm(intent1, reqCode, millis);
//                    break;
//                case 5:
//                    intent1.putExtra("eventName", "to harvest");
//                    reqCode = plant.getId()*100+i;
//                    millis = plant.getPlantingDate().getTime() + 60000 * plant.getVegetationPeriodInDays();
//                    setAlarm(intent1, reqCode, millis);
//                    break;
//
//            }
//    }


//        int reqCode = lastPlantId * 100 + 1;
//        Log.d("AddPlantActivity: reqCode = lastPlantId * 100 + 1 = ", String.valueOf(String.valueOf(reqCode)));

//    }
//    void setAlarm(Intent intent, int reqCode, long millis) {
//        PendingIntent pendingIntent;
//
//            pendingIntent = PendingIntent.getBroadcast(context1,
//                    reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//
//
//        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
//
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
//        Log.d("donebutt obj: ", plant.getPlantName() + "    " + millis);
//    }
}
