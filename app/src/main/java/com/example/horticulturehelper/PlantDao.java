package com.example.horticulturehelper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
//annotation for RoomDB
public interface PlantDao {

    @Insert
//Annotation for RoomDB. We mustn't specify what should it do, because RoomDB does it after your
//annotation
    void insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plant_table WHERE status = 'custom' ORDER BY id ASC")
    LiveData<List<Plant>> getCustomPlants();

    @Query("SELECT * FROM plant_table WHERE status = 'default' ORDER BY id ASC")
    LiveData<List<Plant>> getDefaultPlants();

    @Query("SELECT id FROM plant_table ORDER BY id DESC LIMIT 1")
    int getLastPlantId();

    @Query("SELECT * FROM plant_table WHERE id = :plantId LIMIT 1")
    Plant getPlantById(int plantId);


// LiveData will observe live data in DB and then reflect it to RecyclerView if there is a change


}
