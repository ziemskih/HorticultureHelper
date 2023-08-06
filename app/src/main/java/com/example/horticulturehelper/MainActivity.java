package com.example.horticulturehelper;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private PlantViewModel plantViewModel;
    RecyclerView.ViewHolder viewHolder;
    PlantRepository plantRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("My Plants");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
// Standard code for recyclerView

        PlantAdapter adapter = new PlantAdapter();
        recyclerView.setAdapter(adapter);
//assigning adapter object to RecyclerView

        plantViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(PlantViewModel.class);
        plantViewModel.getCustomPlants().observe(this, new Observer<List<Plant>>() {
            //This is LiveData method and it will observe changes in the DB
            @Override
            public void onChanged(List<Plant> plants) {
// This method will observe the changes

                adapter.setPlants(plants);
//updating RecyclerView
//Whenever the onChanged method works corresponding table should be updated and
// the adapter should be updated in the same way and the plants should be updated
//in the array and then transfered to RecyclerView
            }
        });

        adapter.setOnClickListener(new PlantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                Intent intent = new Intent(MainActivity.this, ViewPlantDetailsActivity.class);
                int plantId = plant.getId();
                intent.putExtra("plantId", plant);
//                intent.putExtra("plantId", plantId);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//This method activates slide to the right or left on RecyclerView
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
  //onMove is used for drag and drop. Holding item with a finger and drag it to trash can
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Deleting plant")
                        .setMessage("Do you want to delete this plant?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {

                                                plantViewModel.delete(adapter.getPlant(viewHolder.getAdapterPosition()));
// viewHolder.getAdapterPosition() this code will determine which element we want to delete. But we didn't
// specify any position in Dao class while writing a delete method. We send the plant object direclty.
// We need to detect object in this position and we will write this process in the adapter class.
                                            }
                                });
                                Toast.makeText(getApplicationContext(), "Plant has been deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                //dialog.cancel();
                                adapter.notifyDataSetChanged();
                            }
                        });
                //Creating dialog box
                builder.create();
                builder.show();
            }
        }).attachToRecyclerView(recyclerView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_activity_menu:

                updateDefaultPlantsWithData();

                Intent intent = new Intent(MainActivity.this,AddPlantActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDefaultPlantsWithData() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                plantRepository = new PlantRepository(getApplication());
                Plant plant1 = plantRepository.getPlantById(1);

                if (!plant1.getPlantName().isEmpty()) {

                    Plant plant2 = plantRepository.getPlantById(2);
                    Plant plant3 = plantRepository.getPlantById(3);
                    Plant plant4 = plantRepository.getPlantById(4);
                    Plant plant5 = plantRepository.getPlantById(5);

                    plant1.setWateringPeriodInDays(8);
                    plant1.setFertilizingPeriodInDays(30);
                    plant1.setMonitoringPeriodInDays(12);
                    plant1.setVegetationPeriodInDays(120);
                    plant1.setSpringFertilizer("springFertilizer plant1");
                    plant1.setSummerFertilizer("summerFertilizer plant1");
                    plant1.setProtection("protection plant1");
                    plant1.setBadCompanion("badCompanion plant1");
                    plantRepository.update(plant1);

                    plant2.setWateringPeriodInDays(10);
                    plant2.setFertilizingPeriodInDays(28);
                    plant2.setMonitoringPeriodInDays(14);
                    plant2.setVegetationPeriodInDays(110);
                    plant2.setSpringFertilizer("springFertilizer plant2");
                    plant2.setSummerFertilizer("summerFertilizer plant2");
                    plant2.setProtection("protection plant2");
                    plant2.setBadCompanion("badCompanion plant2");
                    plantRepository.update(plant2);

                    plant3.setWateringPeriodInDays(9);
                    plant3.setFertilizingPeriodInDays(24);
                    plant3.setMonitoringPeriodInDays(16);
                    plant3.setVegetationPeriodInDays(130);
                    plant3.setSpringFertilizer("springFertilizer plant3");
                    plant3.setSummerFertilizer("summerFertilizer plant3");
                    plant3.setProtection("protection plant3");
                    plant3.setBadCompanion("badCompanion plant3");
                    plantRepository.update(plant3);

                    plant4.setWateringPeriodInDays(11);
                    plant4.setFertilizingPeriodInDays(25);
                    plant4.setMonitoringPeriodInDays(18);
                    plant4.setVegetationPeriodInDays(115);
                    plant4.setSpringFertilizer("springFertilizer plant4");
                    plant4.setSummerFertilizer("summerFertilizer plant4");
                    plant4.setProtection("protection plant4");
                    plant4.setBadCompanion("badCompanion plant4");
                    plantRepository.update(plant4);

                    plant5.setWateringPeriodInDays(13);
                    plant5.setFertilizingPeriodInDays(33);
                    plant5.setMonitoringPeriodInDays(12);
                    plant5.setVegetationPeriodInDays(111);
                    plant5.setSpringFertilizer("springFertilizer plant5");
                    plant5.setSummerFertilizer("summerFertilizer plant5");
                    plant5.setProtection("protection plant5");
                    plant5.setBadCompanion("badCompanion plant5");
                    plantRepository.update(plant5);

                }
            }
        });
    }

}