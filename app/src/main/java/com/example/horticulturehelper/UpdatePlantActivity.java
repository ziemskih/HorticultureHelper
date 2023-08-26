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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdatePlantActivity extends AppCompatActivity {
    private Plant plantOutput;
    private Plant plantInput;
    private int plantId;
    private PlantRepository plantRepository;
    private Context appContext;

    private EditText editTextPlantName;
    private TextView textViewSetPlantingDate;
    private TextView textViewWateringDate;
    private EditText editTextWateringPeriod;
    private TextView textViewFertilizingDate;
    private EditText editTextFertilizingPeriod;
    private TextView textViewMonitoringDate;
    private EditText editTextMonitoringPeriod;
    private TextView textViewHarvestingDate;
    private EditText editTextVegetationPeriod;
    private EditText editTextSpringFertilizer;
    private EditText editTextSummerFertilizer;
    private EditText editTextProtection;
    private EditText editTextBadCompanion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plant);
        getSupportActionBar().setTitle("Plant update");
        appContext = getApplication();
        plantRepository = new PlantRepository(getApplication());
        plantInput = (Plant) getIntent().getSerializableExtra("plant");
        plantId = plantInput.getId();
        plantInput = plantRepository.getPlantById(plantId);

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
                if (plantInput.getIsPlanted() == false) {
                    setDate(textViewSetPlantingDate, UpdatePlantActivity.this);
                }
            }
        });
        textViewWateringDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewWateringDate, UpdatePlantActivity.this);
            }
        });
        textViewFertilizingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewFertilizingDate, UpdatePlantActivity.this);
            }
        });
        textViewMonitoringDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewMonitoringDate, UpdatePlantActivity.this);
            }
        });
        textViewHarvestingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textViewHarvestingDate, UpdatePlantActivity.this);
            }
        });


    }

    private void setTextViews() throws ParseException {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.update_plant_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        switch (item.getItemId()) {
            case R.id.update_plant_activity_menu:
                plantOutput = plantInput;
                if (!editTextPlantName.getText().toString().isEmpty()) {
                    plantOutput.setPlantName(editTextPlantName.getText().toString());
                }

                if (!textViewSetPlantingDate.getText().toString().isEmpty()) {
                    try {
                        plantOutput.setPlantingDate(simpleDateFormat.parse(textViewSetPlantingDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (!textViewWateringDate.getText().toString().isEmpty()) {
                    try {
                        plantOutput.setWateringDate(simpleDateFormat.parse(textViewWateringDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                plantOutput.setWateringPeriodInDays(Integer.valueOf(editTextWateringPeriod.getText().toString()));

                if (!textViewFertilizingDate.getText().toString().isEmpty()) {
                    try {
                        plantOutput.setFertilizingDate(simpleDateFormat.parse(textViewFertilizingDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                plantOutput.setFertilizingPeriodInDays(Integer.valueOf(editTextFertilizingPeriod.getText().toString()));

                if (!textViewMonitoringDate.getText().toString().isEmpty()) {
                    try {
                        plantOutput.setMonitoringDate(simpleDateFormat.parse(textViewMonitoringDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                plantOutput.setMonitoringPeriodInDays(Integer.valueOf(editTextMonitoringPeriod.getText().toString()));

                if (!textViewHarvestingDate.getText().toString().isEmpty()) {
                    try {
                        plantOutput.setHarvestingDate(simpleDateFormat.parse(textViewHarvestingDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                plantOutput.setVegetationPeriodInDays(Integer.valueOf(editTextVegetationPeriod.getText().toString()));
                plantOutput.setSpringFertilizer(editTextSpringFertilizer.getText().toString());
                plantOutput.setSummerFertilizer(editTextSummerFertilizer.getText().toString());
                plantOutput.setProtection(editTextProtection.getText().toString());
                plantOutput.setBadCompanion(editTextBadCompanion.getText().toString());

                if (!plantOutput.getPlantName().equals("")) {

                    setAllRemainingReminders(plantOutput, UpdatePlantActivity.this, "updatePlAct");
                    updateDbEntity(plantOutput, appContext);
                } else {
                    Toast.makeText(getApplicationContext(), "Plant attributes was not updated. Plant name can't be empty.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(UpdatePlantActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void updateDbEntity(Plant plantOut, Context context1) {

        plantRepository = new PlantRepository(getApplication());
        plantRepository.update(plantOut);
        Toast.makeText(context1, "Plant attributes was updated.", Toast.LENGTH_SHORT).show();

    }


    protected void setDate(TextView textView, Context context1){
        // Getting Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context1,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        TimePickerDialog time = new TimePickerDialog(context1, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String date = String.format("%d-%02d-%02d %02d:%02d", year,monthOfYear+1,dayOfMonth,hourOfDay,minute);
                                textView.setText(date);

                            }
                        }, mHour, mMinute, true);
                        time.show();

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();

    }

    protected void setAllRemainingReminders(Plant plant, Context context1, String callerClass) {

        Intent intent1 = new Intent(context1, NotificationCreator.class);
        intent1.putExtra("plant", plant);
        long millis;
        int reqCode = 0;
        if (plant.getIsPlanted() == true) {
            for (int i = 2; i < 6; i++) {
                switch (i) {
                    case 2:
                        intent1.putExtra("eventName", "to water");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 86400000L * plant.getWateringPeriodInDays();
                        if (callerClass.equals("doneBtnRec")){
                            plant.setWateringDate(new Date(millis));
                        }
                        if (callerClass.equals("updatePlAct")){
                            millis = plant.getWateringDate().getTime();
                        }
                        setAlarm(context1, intent1, reqCode, millis);

                        break;
                    case 3:
                        intent1.putExtra("eventName", "to fertilize");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 86400000L * plant.getFertilizingPeriodInDays();
                        if (callerClass.equals("doneBtnRec")){
                            plant.setFertilizingDate(new Date(millis));
                        }
                        if (callerClass.equals("updatePlAct")){
                            millis = plant.getFertilizingDate().getTime();
                        }
                        setAlarm(context1, intent1, reqCode, millis);

                        break;
                    case 4:
                        intent1.putExtra("eventName", "to monitor");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 86400000L * plant.getMonitoringPeriodInDays();
                        if (callerClass.equals("doneBtnRec")){
                            plant.setMonitoringDate(new Date(millis));
                        }
                        if (callerClass.equals("updatePlAct")){
                            millis = plant.getMonitoringDate().getTime();
                        }
                        setAlarm(context1, intent1, reqCode, millis);

                        break;
                    case 5:
                        intent1.putExtra("eventName", "to harvest");
                        reqCode = plant.getId() * 100 + i;
                        millis = plant.getPlantingDate().getTime() + 86400000L * plant.getVegetationPeriodInDays();
                        if (callerClass.equals("doneBtnRec")){
                            plant.setHarvestingDate(new Date(millis));
                            updateDbEntity(plant, context1);
                        }
                        if (callerClass.equals("updatePlAct")){
                            millis = plant.getHarvestingDate().getTime();
                        }
                        setAlarm(context1, intent1, reqCode, millis);

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
    protected void setAlarm(Context context1, Intent intent, int reqCode, long millis) {
        PendingIntent pendingIntent;

        pendingIntent = PendingIntent.getBroadcast(context1,
                reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
    }

}