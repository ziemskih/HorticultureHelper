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
//provide connection between data source (DB) and a view (RecyclerView)


    private List<Plant> plants = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,parent,false);
//describing specially designed layout named plant_item that will be shown on RecyclerView
//ViewGroup parent is RecyclerView
        return new PlantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantHolder holder, int position) {

        Plant currentPlant = plants.get(position);
        holder.textViewPlantName.setText(currentPlant.getPlantName());
        holder.textViewUpcoming.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentPlant.getPlantingDate()));

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }
    public void setPlants(List<Plant> plants){
// observe changes with LiveData
        this.plants = plants;
        notifyDataSetChanged();
//this method will alert the adapter if there is a change in the DB

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
//this method will determine a position of plant clicked on
                    if (listener != null && position != RecyclerView.NO_POSITION) {
//checking list is null or not and checking whether RecyclerView is sending empty position or not

                        listener.onItemClick(plants.get(position));

                    }
                }
            });

        }


    }
    public  interface OnItemClickListener{

        void onItemClick(Plant plant);

    }

    public void setOnClickListener(OnItemClickListener listener){

        this.listener = listener;

    }

}
