package com.example.horticulturehelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AddCustomPlantFragment extends Fragment {

    private EditText editTextPlantName;
    private TextView textViewSetPlantingDate;
    private EditText editTextWateringPeriod;
    private EditText editTextFertilizingPeriod;
    private EditText editTextMonitoringPeriod;
    private EditText editTextVegetationPeriod;
    private EditText editTextSpringFertilizer;
    private EditText editTextSummerFertilizer;
    private EditText editTextProtection;
    private EditText editTextBadCompanion;
    private Button buttonCancel, buttonSave;

    private PlantViewModel plantViewModel;
    private Plant plant;
    private UpdatePlantActivity updatePlantActivity = new UpdatePlantActivity();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_custom_plant, container, false);
        plantViewModel = new ViewModelProvider(getActivity()).get(PlantViewModel.class);

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

                updatePlantActivity.setDate(textViewSetPlantingDate, getContext());
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
    private void savePlant() throws ParseException {
        createPlantObject();
        if (!plant.getPlantName().isEmpty() && textViewSetPlantingDate.getText().toString().length()>1) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    plant.setStatus("custom");
                    plant.setId(plantViewModel.getLastPlantId() + 1);
                    plant.setIsPlanted(false);
                    plantViewModel.insert(plant);
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
    }

    private void createPlantObject(){
        String plantNameStr = editTextPlantName.getText().toString();
        String plantStatus = "custom";
        String plantingDateStr = (textViewSetPlantingDate.getText().toString());

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
    }

    private void setPlantingDateReminder() throws ParseException {
        Intent intent = new Intent(getContext(),NotificationCreator.class);
        intent.putExtra("eventName", "to plant");
        intent.putExtra("plant", plant);

        int reqCode = plant.getId() * 100 + 1;
        PendingIntent pendingIntent;

        pendingIntent = PendingIntent.getBroadcast(getContext(),
                    reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long millis = plant.getPlantingDate().getTime();
        AlarmManager alarmManager = (AlarmManager) requireActivity().getApplication().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
    }

}