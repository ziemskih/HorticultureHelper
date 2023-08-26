package com.example.horticulturehelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewPlantDetailsActivity extends AppCompatActivity {

    private Plant plant;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plant_details);
        getSupportActionBar().setTitle("Plant details");

        textView = findViewById(R.id.textViewViewPlantDetails);
        plant = (Plant) getIntent().getSerializableExtra("plantId");
Log.d("isPlanted in ViewPlantDetailsActivity", "isPlanted" +plant.getIsPlanted()+"    ");


        displayPlantDetails();

    }
    private void displayPlantDetails() {

        textView.setText(Html.fromHtml("<b>Plant name:</b>",Html.FROM_HTML_MODE_LEGACY));
        textView.append("       " + plant.getPlantName()+ "\n");
        String notSet = "not set";
        try {
            textView.append(Html.fromHtml("<b>Planting date:</b>",Html.FROM_HTML_MODE_LEGACY));
            textView.append("       " + dateToString(plant.getPlantingDate())+ "\n");
            textView.append(Html.fromHtml("<b>Watering date:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getWateringDate() == null || plant.getWateringDate().getTime() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("      " + dateToString(plant.getWateringDate()) + "\n");
            textView.append(Html.fromHtml("<b>Watering period:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getWateringPeriodInDays() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("       " + plant.getWateringPeriodInDays() + "\n");

            textView.append(Html.fromHtml("<b>Fertilizing date:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getFertilizingDate() == null || plant.getFertilizingDate().getTime() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("   " + dateToString(plant.getFertilizingDate()) + "\n");
            textView.append(Html.fromHtml("<b>Fertilizing period:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getFertilizingPeriodInDays() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("       " + plant.getFertilizingPeriodInDays() + "\n");

            textView.append(Html.fromHtml("<b>Monitoring date:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getMonitoringDate() == null || plant.getMonitoringDate().getTime() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("  " + dateToString(plant.getMonitoringDate()) + "\n");

            textView.append(Html.fromHtml("<b>Monitoring period:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getMonitoringPeriodInDays() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("       " + plant.getMonitoringPeriodInDays() + "\n");

            textView.append(Html.fromHtml("<b>Harvesting date:<b>"));
            if (plant.getHarvestingDate() == null || plant.getHarvestingDate().getTime() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("  " + dateToString(plant.getHarvestingDate()) + "\n");

            textView.append(Html.fromHtml("<b>Vegetation period:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getVegetationPeriodInDays() == 0)
                textView.append("       " + notSet + "\n");
            else textView.append("       " + plant.getVegetationPeriodInDays() + "\n");

            textView.append(Html.fromHtml("<b>Spring fertilizer:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getSpringFertilizer() != null && !plant.getSpringFertilizer().equals(""))
                textView.append("\n" + "       " + plant.getSpringFertilizer() + "\n");
            else textView.append("       " + notSet + "\n");

            textView.append(Html.fromHtml("<b>Summer fertilizer:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getSummerFertilizer() != null && !plant.getSummerFertilizer().equals(""))
                textView.append("\n" + "       " + plant.getSummerFertilizer() + "\n");
            else textView.append("       " + notSet + "\n");

            textView.append(Html.fromHtml("<b>Protection:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getProtection() != null && !plant.getProtection().equals(""))
                textView.append("\n" + "       " + plant.getProtection() + "\n");
            else textView.append("       " + notSet + "\n");

            textView.append(Html.fromHtml("<b>Bad companion:<b>",Html.FROM_HTML_MODE_LEGACY));
            if (plant.getBadCompanion() != null && !plant.getBadCompanion().equals(""))
                textView.append("\n" + "       " + plant.getBadCompanion() + "\n");
            else textView.append("       " + notSet + "\n");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    protected String dateToString(Date dateToparse) throws ParseException {

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateToparse);

        return date;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_plant_details_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.view_plant_activity_menu:
                Intent intent = new Intent(ViewPlantDetailsActivity.this,UpdatePlantActivity.class);
                intent.putExtra("plant", plant);
                intent.setAction("comesFromViewPlantDetailsActivity");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);



        }
    }

}