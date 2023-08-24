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
//    RecyclerView.ViewHolder viewHolder;
    DoneButtonReceiver doneButtonReceiver;



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
Log.d("iddd", plantId +"    ");
                intent.putExtra("plantId", plant);
Log.d("isPlanted", "isPlanted" +plant.getIsPlanted()+"    ");
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
                                                plantViewModel.delete(adapter.getPlant(viewHolder.getAdapterPosition()));
                                Intent cancelingIntent = new Intent(MainActivity.this, NotificationCreator.class);
                                doneButtonReceiver = new DoneButtonReceiver();
                                doneButtonReceiver.alarmCanceling(cancelingIntent, getApplicationContext());

// viewHolder.getAdapterPosition() this code will determine which element we want to delete. But we didn't
// specify any position in Dao class while writing a delete method. We send the plant object direclty.
// We need to detect object in this position and we will write this process in the adapter class.
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


                Intent intent = new Intent(MainActivity.this,AddPlantActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}