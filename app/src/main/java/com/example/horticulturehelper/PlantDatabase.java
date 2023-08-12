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

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            );
        }
    };


    public static Date stringToDate(String dateToparse) throws ParseException {

        Date dateParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateToparse);
        Log.d("taglog1", "dateToparse: "+dateToparse+"  "+ "dateParsed: "+dateParsed);

        return dateParsed;

    }



}
