package com.example.horticulturehelper;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddPlantActivity extends AppCompatActivity {

    private PlantViewModel plantViewModel;
    PlantDatabase plantDb = PlantDatabase.getInstance(getApplication());
    PlantDao plantDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        getSupportActionBar().setTitle("Add Plant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
// Standard code for recyclerView

        PlantAdapter adapter = new PlantAdapter();
        recyclerView.setAdapter(adapter);
//assigning adapter object to RecyclerView

        plantViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(PlantViewModel.class);
        plantViewModel.getDefaultPlants().observe(this, new Observer<List<Plant>>() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPlantActivity.this);

                builder.setTitle("Adding plant")
                        .setMessage("Do you want to add this plant?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                               finish();

                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        plant.setStatus("custom");
                                        plant.setId(setNextId()+1);
                                        plant.setIsPlanted(false);
                                        plantDb.plantDao().insert(plant);
                                        setReminder(plant);

                                    }
                                });

                                Toast.makeText(getApplicationContext(),"Plant was added",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
//                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                builder.create();
                builder.show();
            }
        });

    }
    void setReminder(Plant plant) {
//        Date date = new Date();
        Intent intent = new Intent(this,NotificationCreator.class);
        int lastPlantId = plantDb.plantDao().getLastPlantId();
//        intent.putExtra("plantId", lastPlantId);
//        intent.putExtra("plantName", plant.getPlantName());
        intent.putExtra("eventName", "to plant");
        intent.putExtra("plant", plant);

        Log.d("hzz","setReminder0"+plant.getPlantName()+intent.getStringExtra("plantName")+intent.getIntExtra("plantId", -1));
        Log.d("hzzz","plantname: "+plant.getPlantName()+plant.getPlantingDate()+plant.getId());
        PendingIntent pendingIntent;
        int reqCode = plant.getId() * 100 + 1;
        Log.d("AddPlantActivity: reqCode = plant.getId() * 100 + 1 = ", String.valueOf(reqCode));

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        Log.d("hzz","setReminder1");
        long millis = plant.getPlantingDate().getTime();

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Log.d("hzz","setReminder2");

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
        Log.d("hzz","setReminder3 "  +millis+ new Date(millis));
    }

    public int setNextId(){
        int nextId = plantDb.plantDao().getLastPlantId() + 1;
        return nextId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_plant_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_plant_activity_menu:
                loadFragment(new AddCustomPlantFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new AddCustomPlantFragment())
                .addToBackStack("fragment")
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}
