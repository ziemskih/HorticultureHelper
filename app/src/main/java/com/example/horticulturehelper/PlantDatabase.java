package com.example.horticulturehelper;

import android.content.Context;
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
@TypeConverters(DateConverter.class)
public abstract class PlantDatabase extends RoomDatabase {

    private static PlantDatabase instance;
    public abstract PlantDao plantDao();
    public static synchronized PlantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                            ,PlantDatabase.class, "plant_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();

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
                        plantDao.insert(new Plant("Pomidorai", "default", stringToDate(currentYear + "-03-15 12:00")));
                        plantDao.insert(new Plant("Agurkai", "default", stringToDate(currentYear + "-04-01 12:00")));
                        plantDao.insert(new Plant("Ridikėliai", "default", stringToDate(currentYear + "-03-01 12:00")));
                        plantDao.insert(new Plant("Svogūnai", "default", stringToDate(currentYear + "-04-01 12:00")));
                        plantDao.insert(new Plant("Arbūzai", "default", stringToDate(currentYear + "-04-15 12:00")));

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

    private static Plant addPlantAttributes(Plant plant){
        switch (plant.getId()){

            case 1:
                plant.setWateringPeriodInDays(7);
                plant.setFertilizingPeriodInDays(30);
                plant.setMonitoringPeriodInDays(7);
                plant.setVegetationPeriodInDays(110);
                plant.setSpringFertilizer("Vištų mėšlas");
                plant.setSummerFertilizer("Kiaulių mėšlas");
                plant.setProtection("Tabako lapų nuoviras, barstymas pelenais, muilo tirpalas");
                plant.setBadCompanion("Žirniai, bulvės, kukurūzai, paprikos");
                break;

            case 2:
                plant.setWateringPeriodInDays(4);
                plant.setFertilizingPeriodInDays(15);
                plant.setMonitoringPeriodInDays(15);
                plant.setVegetationPeriodInDays(80);
                plant.setSpringFertilizer("Vištų mėšlas");
                plant.setSummerFertilizer("Pelenai");
                plant.setProtection("Tabako lapų nuoviras, barstymas pelenais, muilo tirpalas");
                plant.setBadCompanion("Bulvės, ridikėliai, moliūgai");
                break;

            case 3:
                plant.setWateringPeriodInDays(9);
                plant.setFertilizingPeriodInDays(24);
                plant.setMonitoringPeriodInDays(16);
                plant.setVegetationPeriodInDays(130);
                plant.setSpringFertilizer("Vištų mėšlas");
                plant.setSummerFertilizer("Kiaulių mėšlas");
                plant.setProtection("Tabako lapų nuoviras, barstymas pelenais, muilo tirpalas");
                plant.setBadCompanion("Brokoliai, kaliafiorai, agurkai, svogūnai");
                break;

            case 4:
                plant.setWateringPeriodInDays(4);
                plant.setFertilizingPeriodInDays(15);
                plant.setMonitoringPeriodInDays(15);
                plant.setVegetationPeriodInDays(30);
                plant.setSpringFertilizer("Vištų mėšlas");
                plant.setSummerFertilizer("Kiaulių mėšlas");
                plant.setProtection("Tabako lapų nuoviras, barstymas pelenais, muilo tirpalas");
                plant.setBadCompanion("Ridikėliai, brokoliai, žirniai, bulvės");
                break;

            case 5:
                plant.setWateringPeriodInDays(5);
                plant.setFertilizingPeriodInDays(30);
                plant.setMonitoringPeriodInDays(7);
                plant.setVegetationPeriodInDays(70);
                plant.setSpringFertilizer("Vištų mėšlas");
                plant.setSummerFertilizer("Kiaulių mėšlas");
                plant.setProtection("Tabako lapų nuoviras, barstymas pelenais, muilo tirpalas");
                plant.setBadCompanion("Pomidorai, paprikos, bulvės");
                break;

        }
        return plant;

    }

    private static Date stringToDate(String dateToparse) throws ParseException {

        Date dateParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateToparse);

        return dateParsed;

    }



}
