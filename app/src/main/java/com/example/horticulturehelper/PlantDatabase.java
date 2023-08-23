package com.example.horticulturehelper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plant.class},version = 1)
//annotation as DB
@TypeConverters(DateConverter.class)
public abstract class PlantDatabase extends RoomDatabase {

    private static PlantDatabase instance;
// static DB object instance can used anywhere in the app
    public abstract PlantDao plantDao();
//abstract method plantDao mustn't have body and argument, because this will handle RoomDB
    public static synchronized PlantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                            ,PlantDatabase.class, "plant_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();
            Log.d("oncr", "build executed");

        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            PlantDao plantDao = instance.plantDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String currentYear = String.valueOf(Year.now().getValue());
                        plantDao.insert(new Plant("Pomidorai", "default", stringToDate(currentYear + "-03-05 12:00")));
                        plantDao.insert(new Plant("Agurkai", "default", stringToDate(currentYear + "-03-15 12:00")));
                        plantDao.insert(new Plant("Ridikeliai", "default", stringToDate(currentYear + "-03-25 12:00")));
                        plantDao.insert(new Plant("Svogūnai", "default", stringToDate(currentYear + "-04-26 12:00")));
                        plantDao.insert(new Plant("Arbuzai", "default", stringToDate(currentYear + "-03-10 12:00")));

                        for(int id=1; id<6; id++){
                            Plant plantOut = addPlantAttributes(plantDao.getPlantById(id));
                            plantDao.update(plantOut);
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
            );
        }
    };

    public static Plant addPlantAttributes(Plant plant){
        switch (plant.getId()){

            case 1:
                plant.setWateringPeriodInDays(8);
                plant.setFertilizingPeriodInDays(30);
                plant.setMonitoringPeriodInDays(12);
                plant.setVegetationPeriodInDays(120);
                plant.setSpringFertilizer("springFertilizer plant1");
                plant.setSummerFertilizer("summerFertilizer plant1");
                plant.setProtection("protection plant1");
                plant.setBadCompanion("badCompanion plant1");
                break;

            case 2:
                plant.setWateringPeriodInDays(10);
                plant.setFertilizingPeriodInDays(28);
                plant.setMonitoringPeriodInDays(14);
                plant.setVegetationPeriodInDays(110);
                plant.setSpringFertilizer("springFertilizer plant2");
                plant.setSummerFertilizer("summerFertilizer plant2");
                plant.setProtection("protection plant2");
                plant.setBadCompanion("badCompanion plant2");
                break;

            case 3:
                plant.setWateringPeriodInDays(9);
                plant.setFertilizingPeriodInDays(24);
                plant.setMonitoringPeriodInDays(16);
                plant.setVegetationPeriodInDays(130);
                plant.setSpringFertilizer("springFertilizer plant3");
                plant.setSummerFertilizer("summerFertilizer plant3");
                plant.setProtection("protection plant3");
                plant.setBadCompanion("badCompanion plant3");
                break;

            case 4:
                plant.setWateringPeriodInDays(11);
                plant.setFertilizingPeriodInDays(25);
                plant.setMonitoringPeriodInDays(18);
                plant.setVegetationPeriodInDays(115);
                plant.setSpringFertilizer("springFertilizer plant4");
                plant.setSummerFertilizer("summerFertilizer plant4");
                plant.setProtection("protection plant4");
                plant.setBadCompanion("badCompanion plant4");
                break;

            case 5:
                plant.setWateringPeriodInDays(13);
                plant.setFertilizingPeriodInDays(33);
                plant.setMonitoringPeriodInDays(12);
                plant.setVegetationPeriodInDays(111);
                plant.setSpringFertilizer("springFertilizer plant5");
                plant.setSummerFertilizer("summerFertilizer plant5");
                plant.setProtection("protection plant5");
                plant.setBadCompanion("badCompanion plant5");
                break;

        }
        return plant;

    }

    public static Date stringToDate(String dateToparse) throws ParseException {

        Date dateParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateToparse);
        Log.d("taglog1", "dateToparse: "+dateToparse+"  "+ "dateParsed: "+dateParsed);

        return dateParsed;

    }



}
