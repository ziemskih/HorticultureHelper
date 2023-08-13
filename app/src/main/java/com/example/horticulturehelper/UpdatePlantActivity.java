package com.example.horticulturehelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
    Plant plantInput;
    int plantId;
    PlantDatabase plantDb = PlantDatabase.getInstance(getApplication());
    ViewPlantDetailsActivity v = new ViewPlantDetailsActivity();
    Context mContext;

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
        mContext = getApplication();

        plantInput = (Plant) getIntent().getSerializableExtra("plant");
        plantId = plantInput.getId();
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
        editTextPlantName.setText(plantInput.getPlantName());
        textViewSetPlantingDate.setText(v.dateToString(plantInput.getPlantingDate()));
        if (plantInput.getWateringDate() != null)
            textViewWateringDate.setText(v.dateToString(plantInput.getWateringDate()));
        editTextWateringPeriod.setText(String.valueOf(plantInput.getWateringPeriodInDays()));
        if (plantInput.getFertilizingDate() != null)
            textViewFertilizingDate.setText(v.dateToString(plantInput.getFertilizingDate()));
        editTextFertilizingPeriod.setText(String.valueOf(plantInput.getFertilizingPeriodInDays()));
        if (plantInput.getMonitoringDate() != null)
            textViewMonitoringDate.setText(v.dateToString(plantInput.getMonitoringDate()));
        editTextMonitoringPeriod.setText(String.valueOf(plantInput.getMonitoringPeriodInDays()));
        if (plantInput.getHarvestingDate() != null)
            textViewHarvestingDate.setText(v.dateToString(plantInput.getHarvestingDate()));
        editTextVegetationPeriod.setText(String.valueOf(plantInput.getVegetationPeriodInDays()));

        editTextSpringFertilizer.setText(plantInput.getSpringFertilizer());
        editTextSummerFertilizer.setText(plantInput.getSummerFertilizer());
        editTextProtection.setText(plantInput.getProtection());
        editTextBadCompanion.setText(plantInput.getBadCompanion());

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
                plantOutput.setIsPlanted(plantInput.getIsPlanted());
                plantOutput.setId(plantId);
                Log.d("hz","----"+textViewWateringDate.getText().toString());
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
            plantOutput.setMonitoringPeriodInDays(Integer.valueOf(editTextMonitoringPeriod.getText().toString()));
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
//        if (getIntent().getAction(comesFromViewPlantDetailsActivity)){
//
//            }


            Log.d("qwerty", "plantObjct  "+editTextPlantName.getText().toString()+plantOutput.getPlantName());

            if (!plantOutput.getPlantName().equals("")) {

                updateDbEntity(plantOutput, mContext);
                setAllRemainingReminders(plantOutput, UpdatePlantActivity.this, "updatePlAct");
            }
            else{
                Toast.makeText(getApplicationContext(), "Plant attributes was not updated. Plant name can't be empty.", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(UpdatePlantActivity.this,MainActivity.class);
            startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateDbEntity(Plant plantOut, Context context1) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("isPlanted in UpdatePlantActivity", "isPlanted" +plantOut.getIsPlanted()+"    ");

                plantDb.plantDao().update(plantOut);
            }
        });
        Toast.makeText(context1, "Plant attributes was updated.", Toast.LENGTH_SHORT).show();

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
    public void setAllRemainingReminders(Plant plant, Context context1, String callerClass) {

        Intent intent1 = new Intent(context1, NotificationCreator.class);
        intent1.putExtra("plant", plant);
        long millis ;
        int reqCode = 0;
        if (plant.getIsPlanted() == true) {
            for (int i = 2; i < 6; i++) {
                switch (i) {
                    case 2:
                        intent1.putExtra("eventName", "to water");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 30000L * plant.getWateringPeriodInDays();
                        setAlarm(context1, intent1, reqCode, millis);
                        if (callerClass.equals("doneBtnRec")){
                            plant.setWateringDate(new Date(millis));
                        }
Log.d("update obj: ", "watering millis:    " + millis);

                        break;
                    case 3:
                        intent1.putExtra("eventName", "to fertilize");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() +30000L * plant.getFertilizingPeriodInDays();
                        setAlarm(context1, intent1, reqCode, millis);
                        if (callerClass.equals("doneBtnRec")){
                            plant.setFertilizingDate(new Date(millis));
                        }

                        Log.d("update obj: ", "fertilizing millis:    " + millis);
                        break;
                    case 4:
                        intent1.putExtra("eventName", "to monitor");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 30000L * plant.getMonitoringPeriodInDays();
                        setAlarm(context1, intent1, reqCode, millis);
                        if (callerClass.equals("doneBtnRec")){
                            plant.setMonitoringDate(new Date(millis));
                        }

                        Log.d("update obj: ", "monitoring millis:    " + millis);
                        break;
                    case 5:
                        intent1.putExtra("eventName", "to harvest");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 30000L * plant.getVegetationPeriodInDays();
                        setAlarm(context1, intent1, reqCode, millis);
                        if (callerClass.equals("doneBtnRec")){
                            plant.setHarvestingDate(new Date(millis));
                            updateDbEntity(plant, context1);
                        }

                        Log.d("update obj: ", "vegetation millis:    " + millis);
                        break;

                }
            }
        } else {
            intent1.putExtra("eventName", "to plant");
            reqCode = plant.getId() * 100 + 1;
            millis = plant.getPlantingDate().getTime();
            setAlarm(context1, intent1, reqCode, millis);

        }
    }
    void setAlarm(Context context1, Intent intent, int reqCode, long millis) {
        PendingIntent pendingIntent;

        pendingIntent = PendingIntent.getBroadcast(context1,
                reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);



        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
//        Log.d("update obj: ", plantInput.getPlantName() + "    " + millis);
    }


}