package com.example.horticulturehelper;

import static androidx.core.content.ContextCompat.getSystemService;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCustomPlantFragment extends Fragment {

    EditText editTextPlantName;
    TextView textViewSetPlantingDate;
    EditText editTextWateringPeriod;
    EditText editTextFertilizingPeriod;
    EditText editTextMonitoringPeriod;
    EditText editTextVegetationPeriod;
    EditText editTextSpringFertilizer;
    EditText editTextSummerFertilizer;
    EditText editTextProtection;
    EditText editTextBadCompanion;

    Button buttonCancel, buttonSave;
    PlantDatabase plantDb = PlantDatabase.getInstance(getContext());
    AddPlantActivity addPlantActivity = new AddPlantActivity();
    Plant plant = null;
    String reminderTime = " 12:00";
    UpdatePlantActivity updatePlantActivity = new UpdatePlantActivity();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_custom_plant, container, false);

        editTextPlantName = view.findViewById(R.id.editTextPlantName);
        textViewSetPlantingDate = view.findViewById(R.id.textViewSetPlantingDate);
        editTextWateringPeriod = view.findViewById(R.id.editTextWateringPeriod);
        editTextFertilizingPeriod = view.findViewById(R.id.editTextFertilizingPeriod);
        editTextMonitoringPeriod = view.findViewById(R.id.editTextMonitoringPeriod);
        editTextVegetationPeriod = view.findViewById(R.id.editTextVegetationPeriod);
        editTextSpringFertilizer = view.findViewById(R.id.editTextSpringFertilizer);
        editTextSummerFertilizer = view.findViewById(R.id.editTextSummerFertilizer);
        editTextProtection = view.findViewById(R.id.editTextProtection);
        editTextBadCompanion = view.findViewById(R.id.editTextBadCompanion);

        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);

        textViewSetPlantingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlantingDate(textViewSetPlantingDate);
            }

        });

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Adding plant")
                        .setMessage("Do you want to add this plant?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                               finish();

                                try {
                                    savePlant();

                                } catch (ParseException e) {
                                    e.printStackTrace();

                                }
                                getActivity().getSupportFragmentManager().popBackStack();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                builder.create();
                builder.show();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            });

        return view;
    }
    public void savePlant() throws ParseException {
        createPlantObject();
        if (!plant.getPlantName().isEmpty() && textViewSetPlantingDate.getText().toString().length()>1) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    plantDb.plantDao().insert(plant);
                    try {
                        setPlantingDateReminder();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            Toast.makeText(getContext(), "Plant was added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Plant wasn't added. Plant name and planting date input is required.", Toast.LENGTH_LONG).show();
        }



//        finish();
    }
    public Plant createPlantObject(){
        String plantNameStr = editTextPlantName.getText().toString();
        String plantStatus = "custom";
        String plantingDateStr = (textViewSetPlantingDate.getText().toString()).concat(reminderTime);
Log.d("hzz", plantingDateStr+".     ."+textViewSetPlantingDate.getText().toString());
        Date dateParsed = null;
        try {
            dateParsed = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(plantingDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        plant = new Plant(plantNameStr,plantStatus, dateParsed);
        plant.setIsPlanted(false);

        String plantWateringPeriodInt = editTextWateringPeriod.getText().toString();
        String plantFertilizingPeriodInt = editTextFertilizingPeriod.getText().toString();
        String plantMonitoringPeriodInt = editTextMonitoringPeriod.getText().toString();
        String plantVegetationPeriodInt = editTextVegetationPeriod.getText().toString();
        String plantSpringFertilizerStr = editTextSpringFertilizer.getText().toString();
        String plantSummerFertilizerStr = editTextSummerFertilizer.getText().toString();
        String plantProtectionStr = editTextProtection.getText().toString();
        String plantBadCompanionStr = editTextBadCompanion.getText().toString();

        if (!plantWateringPeriodInt.isEmpty())
            plant.setWateringPeriodInDays(Integer.valueOf(plantWateringPeriodInt));
        if (!plantFertilizingPeriodInt.isEmpty())
            plant.setFertilizingPeriodInDays(Integer.valueOf(plantFertilizingPeriodInt));
        if (!plantMonitoringPeriodInt.isEmpty())
            plant.setMonitoringPeriodInDays(Integer.valueOf(plantMonitoringPeriodInt));
        if (!plantVegetationPeriodInt.isEmpty())
            plant.setVegetationPeriodInDays(Integer.valueOf(plantVegetationPeriodInt));
        if (!plantSpringFertilizerStr.isEmpty())
            plant.setSpringFertilizer(plantSpringFertilizerStr);
        if (!plantSummerFertilizerStr.isEmpty())
            plant.setSummerFertilizer(plantSummerFertilizerStr);
        if (!plantProtectionStr.isEmpty())
            plant.setProtection(plantProtectionStr);
        if (!plantBadCompanionStr.isEmpty())
            plant.setBadCompanion(plantBadCompanionStr);

        return plant;
    }


//    public Date stringToDate (String dateToparse) throws ParseException {
//
//        Date dateParsed = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateToparse);
//
//        return dateParsed;
//
//    }



    public void setPlantingDate(TextView textView){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
//        int mHour = Integer.parseInt((textView.getText().toString()).substring(11,13));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                        final Calendar newDate = Calendar.getInstance();

//                        date = date.concat(" 12:00");
                        TimePickerDialog time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

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

    }
    void setPlantingDateReminder() throws ParseException {
        Log.d("hzz","setPlantingDateReminder started: ");
//        Date date = new Date();
        Intent intent = new Intent(getContext(),NotificationCreator.class);
//        int reqCode = (addPlantActivity.setNextId()+1)*100+1;
        //requestCode setting with plantId*100+1
        int lastPlantId = plantDb.plantDao().getLastPlantId() + 1;
        plant.setId(lastPlantId);
        intent.putExtra("plantId", lastPlantId);
        intent.putExtra("plantName", editTextPlantName.getText().toString());
        intent.putExtra("eventName", "to plant");
        intent.putExtra("plant", plant);

        Log.d("hzz","setReminder0 fra from intent: "+ intent.getStringExtra("plantName"));
        int reqCode = lastPlantId * 100 + 1;
        Log.d("AddCustomPlantFragment: reqCode = lastPlantId * 100 + 1 = ", String.valueOf(reqCode));
        PendingIntent pendingIntent;

            pendingIntent = PendingIntent.getBroadcast(getContext(),
                    reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Log.d("hzz","setReminder1 textViewSetPlantingDate: "+textViewSetPlantingDate.getText().toString());

        long millis = PlantDatabase.stringToDate((textViewSetPlantingDate.getText().toString()).concat(reminderTime)).getTime();
        Log.d("hzz","setReminder1.1");
        AlarmManager alarmManager = (AlarmManager) requireActivity().getApplication().getSystemService(Context.ALARM_SERVICE);
        Log.d("hzz","setReminder2");
 //       long millis = 1684798800000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
        Log.d("hzz","setReminder3 "  +millis+ new Date(millis));
    }



}