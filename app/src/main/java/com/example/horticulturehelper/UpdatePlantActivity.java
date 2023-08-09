package com.example.horticulturehelper;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdatePlantActivity extends AppCompatActivity {
    Plant plantOutput = null;
    Plant plant;
    int plantId;
    PlantDatabase plantDb = PlantDatabase.getInstance(getApplication());
    ViewPlantDetailsActivity v = new ViewPlantDetailsActivity();


    EditText editTextPlantName;
    TextView textViewSetPlantingDate;
    TextView textViewWateringDate;
    EditText editTextWateringPeriod;
    TextView textViewFertilizingDate;
    EditText editTextFertilizingPeriod;
    TextView textViewMonitoringDate;
    EditText editTextMonitoringPeriod;
    TextView textViewHarvestingDate;
    EditText editTextVegetationPeriod;
    EditText editTextSpringFertilizer;
    EditText editTextSummerFertilizer;
    EditText editTextProtection;
    EditText editTextBadCompanion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plant);
        getSupportActionBar().setTitle("Plant update");

        plant = (Plant) getIntent().getSerializableExtra("plant");
        plantId = plant.getId();

        editTextPlantName = findViewById(R.id.editTextPlantName);
        textViewSetPlantingDate = findViewById(R.id.textViewPlantingDate);
        textViewWateringDate = findViewById(R.id.textViewWateringDate);
        editTextWateringPeriod = findViewById(R.id.editTextWateringPeriod);
        textViewFertilizingDate = findViewById(R.id.textViewFertilizingDate);
        editTextFertilizingPeriod = findViewById(R.id.editTextFertilizingPeriod);
        textViewMonitoringDate = findViewById(R.id.textViewMonitoringDate);
        editTextMonitoringPeriod = findViewById(R.id.editTextMonitoringPeriod);
        textViewHarvestingDate = findViewById(R.id.textViewHarvestingDate);
        editTextVegetationPeriod = findViewById(R.id.editTextVegetationPeriod);
        editTextSpringFertilizer = findViewById(R.id.editTextSpringFertilizer);
        editTextSummerFertilizer = findViewById(R.id.editTextSummerFertilizer);
        editTextProtection = findViewById(R.id.editTextProtection);
        editTextBadCompanion = findViewById(R.id.editTextBadCompanion);
        try {
            setTextViews();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textViewSetPlantingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewSetPlantingDate);
            }
        });
        textViewWateringDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewWateringDate);
            }
        });
        textViewFertilizingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewFertilizingDate);
            }
        });
        textViewMonitoringDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewMonitoringDate);
            }
        });
        textViewHarvestingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewHarvestingDate);
            }
        });




    }
    void setTextViews() throws ParseException {
        ViewPlantDetailsActivity v = new ViewPlantDetailsActivity();
        editTextPlantName.setText(plant.getPlantName());
        textViewSetPlantingDate.setText(v.dateToString(plant.getPlantingDate()));
        if (plant.getWateringDate() != null)
            textViewWateringDate.setText(v.dateToString(plant.getWateringDate()));
        editTextWateringPeriod.setText(String.valueOf(plant.getWateringPeriodInDays()));
        if (plant.getFertilizingDate() != null)
            textViewFertilizingDate.setText(v.dateToString(plant.getFertilizingDate()));
        editTextFertilizingPeriod.setText(String.valueOf(plant.getFertilizingPeriodInDays()));
        if (plant.getMonitoringDate() != null)
            textViewMonitoringDate.setText(v.dateToString(plant.getMonitoringDate()));
        editTextMonitoringPeriod.setText(String.valueOf(plant.getMonitoringPeriodInDays()));
        if (plant.getHarvestingDate() != null)
            textViewHarvestingDate.setText(v.dateToString(plant.getHarvestingDate()));
        editTextVegetationPeriod.setText(String.valueOf(plant.getVegetationPeriodInDays()));

        editTextSpringFertilizer.setText(plant.getSpringFertilizer());
        editTextSummerFertilizer.setText(plant.getSummerFertilizer());
        editTextProtection.setText(plant.getProtection());
        editTextBadCompanion.setText(plant.getBadCompanion());

    }

//    Plant setPlantAttributes() throws ParseException {
//        Plant plantOutput = null;
//        plantOutput.setPlantName(editTextPlantName.getText().toString());
//        Log.d("qwerty", "plantObjct  "+editTextPlantName.getText().toString());
//
//
//        plantOutput.setPlantingDate(plantDb.stringToDate(textViewSetPlantingDate.getText().toString()));
//        if (!textViewWateringDate.getText().toString().isEmpty())
//            plantOutput.setWateringDate(plantDb.stringToDate(textViewWateringDate.getText().toString()));
//        plantOutput.setWateringPeriodInDays(Integer.valueOf(editTextWateringPeriod.getText().toString()));
//        if (!textViewFertilizingDate.getText().toString().isEmpty())
//            plantOutput.setFertilizingDate(plantDb.stringToDate(textViewFertilizingDate.getText().toString()));
//        plantOutput.setFertilizingPeriodInDays(Integer.valueOf(editTextFertilizingPeriod.getText().toString()));
//        if (!textViewMonitoringDate.getText().toString().isEmpty())
//            plantOutput.setMonitoringDate(plantDb.stringToDate(textViewMonitoringDate.getText().toString()));
//        plantOutput.setMonitoringPeriodInDays(Integer.valueOf(editTextFertilizingPeriod.getText().toString()));
//        if (!textViewHarvestingDate.getText().toString().isEmpty())
//            plantOutput.setHarvestingDate(plantDb.stringToDate(textViewHarvestingDate.getText().toString()));
//        plantOutput.setVegetationPeriodInDays(Integer.valueOf(editTextVegetationPeriod.getText().toString()));
//
//        plantOutput.setSpringFertilizer(editTextSpringFertilizer.getText().toString());
//        plantOutput.setSummerFertilizer(editTextSummerFertilizer.getText().toString());
//        plantOutput.setProtection(editTextProtection.getText().toString());
//        plantOutput.setBadCompanion(editTextBadCompanion.getText().toString());
//
//        plantOutput.setId(plant.getId());
//        plantOutput.setStatus("custom");
//
//        return plantOutput;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.update_plant_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.update_plant_activity_menu:
                Date date = null;
                try {
                    date = plantDb.stringToDate(textViewSetPlantingDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                plantOutput = new Plant(editTextPlantName.getText().toString(), "custom", date);
                plantOutput.setId(plantId);
                Log.d("hz","----"+textViewWateringDate.getText());
                if (!textViewWateringDate.getText().toString().isEmpty()) {

                try {
                    Log.d("hz","----11"+plantOutput.getPlantName());
                    plantOutput.setWateringDate(plantDb.stringToDate(textViewWateringDate.getText().toString()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                }
        plantOutput.setWateringPeriodInDays(Integer.valueOf(editTextWateringPeriod.getText().toString()));
        if (!textViewFertilizingDate.getText().toString().isEmpty()) {
            try {
                plantOutput.setFertilizingDate(plantDb.stringToDate(textViewFertilizingDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        plantOutput.setFertilizingPeriodInDays(Integer.valueOf(editTextFertilizingPeriod.getText().toString()));
        if (!textViewMonitoringDate.getText().toString().isEmpty()) {
            try {
                plantOutput.setMonitoringDate(plantDb.stringToDate(textViewMonitoringDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        plantOutput.setMonitoringPeriodInDays(Integer.valueOf(editTextFertilizingPeriod.getText().toString()));
        if (!textViewHarvestingDate.getText().toString().isEmpty()) {
            try {
                plantOutput.setHarvestingDate(plantDb.stringToDate(textViewHarvestingDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        plantOutput.setVegetationPeriodInDays(Integer.valueOf(editTextVegetationPeriod.getText().toString()));

        plantOutput.setSpringFertilizer(editTextSpringFertilizer.getText().toString());
        plantOutput.setSummerFertilizer(editTextSummerFertilizer.getText().toString());
        plantOutput.setProtection(editTextProtection.getText().toString());
        plantOutput.setBadCompanion(editTextBadCompanion.getText().toString());


        Log.d("qwerty", "plantObjct  "+editTextPlantName.getText().toString()+plantOutput.getPlantName());

        if (!plantOutput.getPlantName().equals("")) {

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    plantDb.plantDao().update(plantOutput);
                }
            });
            Toast.makeText(getApplicationContext(), "Plant attributes was updated.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Plant attributes was not updated. Plant name can't be empty.", Toast.LENGTH_LONG).show();
        };
        Intent intent = new Intent(UpdatePlantActivity.this,MainActivity.class);
        startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    public void setDate(TextView textView){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
//        int mHour = Integer.parseInt((textView.getText().toString()).substring(11,13));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                        final Calendar newDate = Calendar.getInstance();

//                        date = date.concat(" 12:00");
                        TimePickerDialog time = new TimePickerDialog(UpdatePlantActivity.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String date = String.format("%d-%02d-%02d %02d:%02d", year,monthOfYear+1,dayOfMonth,hourOfDay,minute);
                                textView.setText(date);

//                                newDate.set(year, monthOfYear, dayOfMonth, hourOfDay, minute, 0);
                            }
                        }, mHour, mMinute, true);
                        time.show();

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
        Log.d("hz", "date:"+textView.getText()+"end");

    }


}