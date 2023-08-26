package com.example.horticulturehelper;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlantRepository {
    private PlantDao plantDao;
    private LiveData<List<Plant>> plants;
    private LiveData<List<Plant>> plants2;

    ExecutorService executors = Executors.newSingleThreadExecutor();


    public PlantRepository(Application application){

        PlantDatabase database = PlantDatabase.getInstance(application);
        plantDao = database.plantDao();
        plants = plantDao.getCustomPlants();
        plants2 = plantDao.getDefaultPlants();

    }
    public void insert(Plant plant){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                plantDao.insert(plant);
            }
        });
    }
    public void update(Plant plant){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                plantDao.update(plant);
            }
        });

    }
    public void delete(Plant plant){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                plantDao.delete(plant);
            }
        });

    }
    public LiveData<List<Plant>> getDefaultPlants(){
        return plants2;
    }

    public LiveData<List<Plant>> getCustomPlants(){
        return plants;
    }

    public Plant getPlantById(int plantId){return plantDao.getPlantById(plantId);};

    public int getLastPlantId() {return plantDao.getLastPlantId();}

}
