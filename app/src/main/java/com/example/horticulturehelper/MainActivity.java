package com.example.horticulturehelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private PlantViewModel plantViewModel;
    private DoneButtonReceiver doneButtonReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("My Plants");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlantAdapter adapter = new PlantAdapter();
        recyclerView.setAdapter(adapter);

        plantViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(PlantViewModel.class);
        plantViewModel.getCustomPlants().observe(this, new Observer<List<Plant>>() {

            @Override
            public void onChanged(List<Plant> plants) {

                adapter.setPlants(plants);
            }
        });

        adapter.setOnClickListener(new PlantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                Intent intent = new Intent(MainActivity.this, ViewPlantDetailsActivity.class);
                intent.putExtra("plantId", plant);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
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
                                Plant plantToDelete = adapter.getPlant(viewHolder.getAdapterPosition());
                                                plantViewModel.delete(plantToDelete);
                                Intent cancelingIntent = new Intent(MainActivity.this, NotificationCreator.class);
                                doneButtonReceiver = new DoneButtonReceiver();
                                doneButtonReceiver.alarmCanceling(cancelingIntent, getApplicationContext(), plantToDelete);

                                Toast.makeText(getApplicationContext(), "Plant has been deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
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

                Intent intent = new Intent(MainActivity.this,AddPlantActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}