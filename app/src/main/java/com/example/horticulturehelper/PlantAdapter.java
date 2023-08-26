package com.example.horticulturehelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantHolder> {

    private List<Plant> plants = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,parent,false);

        return new PlantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantHolder holder, int position) {

        Plant currentPlant = plants.get(position);
        holder.textViewPlantName.setText(currentPlant.getPlantName());
        if (currentPlant.getStatus().equals("custom")) {
            if (currentPlant.getIsPlanted() == null || !currentPlant.getIsPlanted()) {
                holder.textViewUpcoming.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentPlant.getPlantingDate()));
            } else
                holder.textViewUpcoming.setText(getSmallestDate(currentPlant));
        }
    }

    private String getSmallestDate(Plant plant) {

        long wateringDateInMillis;
        long fertilizingDateInMillis;
        long monitoringDateInMillis;
        long harvestingDateInMillis;
        long largeNumber = System.currentTimeMillis() + 1577846300000L;
        // setting currentTime+50years to largeNumber

        if (plant.getWateringDate() == null || plant.getWateringDate().getTime() == 0)
            wateringDateInMillis = largeNumber;
        else wateringDateInMillis = plant.getWateringDate().getTime();

        if (plant.getFertilizingDate() == null || plant.getFertilizingDate().getTime() == 0)
            fertilizingDateInMillis = largeNumber;
        else fertilizingDateInMillis = plant.getFertilizingDate().getTime();

        if (plant.getMonitoringDate() == null || plant.getMonitoringDate().getTime() == 0)
            monitoringDateInMillis = largeNumber;
        else monitoringDateInMillis = plant.getMonitoringDate().getTime();

        if (plant.getHarvestingDate() == null || plant.getHarvestingDate().getTime() == 0)
            harvestingDateInMillis = largeNumber;
        else harvestingDateInMillis = plant.getHarvestingDate().getTime();

        long smallestDate = Math.min(wateringDateInMillis,Math.min(monitoringDateInMillis,Math.min(fertilizingDateInMillis,harvestingDateInMillis)));

        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(smallestDate);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public void setPlants(List<Plant> plants){
        this.plants = plants;
        notifyDataSetChanged();

    }

    public Plant getPlant(int position){
        return plants.get(position);

    }

    class PlantHolder extends RecyclerView.ViewHolder{

        TextView textViewPlantName;
        TextView textViewUpcoming;

        public PlantHolder(@NonNull View itemView) {
            super(itemView);

            textViewPlantName = itemView.findViewById(R.id.textViewPlantName);
            textViewUpcoming = itemView.findViewById(R.id.textViewUpcoming);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(plants.get(position));
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{

        void onItemClick(Plant plant);

    }

    public void setOnClickListener(OnItemClickListener listener){

        this.listener = listener;

    }

}
