package com.example.horticulturehelper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlantViewModel extends AndroidViewModel {

    private PlantRepository repository;
    private LiveData<List<Plant>> plants;
    private LiveData<List<Plant>> plants2;
    private int id;

    public PlantViewModel(@NonNull Application application) {
        super(application);

        repository = new PlantRepository(application);
        plants = repository.getCustomPlants();
        plants2 = repository.getDefaultPlants();
        id = repository.getLastPlantId();

    }
    public void insert(Plant plant){
        repository.insert(plant);
    }

    public void update(Plant plant){
        repository.update(plant);
    }

    public void delete(Plant plant){
        repository.delete(plant);
    }

    public LiveData<List<Plant>> getDefaultPlants(){
        return plants2;
    }

    public LiveData<List<Plant>> getCustomPlants(){
        return plants;
    }

    public int getLastPlantId() {return id;}

}
