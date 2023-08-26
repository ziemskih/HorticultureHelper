package com.example.horticulturehelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Date;
import java.util.List;

public class AddPlantActivity extends AppCompatActivity {

    private PlantViewModel plantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        getSupportActionBar().setTitle("Add Plant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlantAdapter adapter = new PlantAdapter();
        recyclerView.setAdapter(adapter);

        plantViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(PlantViewModel.class);
        plantViewModel.getDefaultPlants().observe(this, new Observer<List<Plant>>() {

            @Override
            public void onChanged(List<Plant> plants) {
                adapter.setPlants(plants);
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

                                        plant.setStatus("custom");
                                        plant.setId(plantViewModel.getLastPlantId()+1);
                                        plant.setIsPlanted(false);
                                        plantViewModel.insert(plant);
                                        setReminder(plant);

                                Toast.makeText(getApplicationContext(),"Plant was added",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button

                            }
                        });
                //Creating dialog box
                builder.create();
                builder.show();
            }
        });

    }
    private void setReminder(Plant plant) {
        Intent intent = new Intent(this,NotificationCreator.class);
        intent.putExtra("eventName", "to plant");
        intent.putExtra("plant", plant);

        PendingIntent pendingIntent;
        int reqCode = plant.getId() * 100 + 1;

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long millis = plant.getPlantingDate().getTime();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
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
