package com.example.horticulturehelper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "plant_table")

public class Plant implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String plantName;
    private Date plantingDate;
    private Date wateringDate;
    private int wateringPeriodInDays;
    private Date fertilizingDate;
    private int fertilizingPeriodInDays;
    private Date monitoringDate;
    private int monitoringPeriodInDays;
    private int vegetationPeriodInDays;
    private Date harvestingDate;
    private String springFertilizer;
    private String summerFertilizer;
    private String protection;
    private String badCompanion;
    private String status;
    private Boolean isPlanted;

    public Plant(String plantName, String status, Date plantingDate) {
        this.plantName = plantName;
        this.plantingDate = plantingDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIsPlanted() {
        return isPlanted;
    }

    public void setIsPlanted(Boolean planted) {
        isPlanted = planted;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Date getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(Date plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getWateringDate() { return wateringDate; }

    public void setWateringDate(Date wateringDate) {
        this.wateringDate = wateringDate;
    }

    public int getWateringPeriodInDays() {
        return wateringPeriodInDays;
    }

    public void setWateringPeriodInDays(int wateringPeriodInDays) {
        this.wateringPeriodInDays = wateringPeriodInDays;
    }

    public Date getFertilizingDate() {
        return fertilizingDate;
    }

    public void setFertilizingDate(Date fertilizingDate) {
        this.fertilizingDate = fertilizingDate;
    }

    public int getFertilizingPeriodInDays() {
        return fertilizingPeriodInDays;
    }

    public void setFertilizingPeriodInDays(int fertilizingPeriodInDays) {
        this.fertilizingPeriodInDays = fertilizingPeriodInDays;
    }

    public Date getMonitoringDate() {
        return monitoringDate;
    }

    public void setMonitoringDate(Date monitoringDate) {
        this.monitoringDate = monitoringDate;
    }

    public int getMonitoringPeriodInDays() {
        return monitoringPeriodInDays;
    }

    public void setMonitoringPeriodInDays(int monitoringPeriodInDays) {
        this.monitoringPeriodInDays = monitoringPeriodInDays;
    }

    public int getVegetationPeriodInDays() {
        return vegetationPeriodInDays;
    }

    public void setVegetationPeriodInDays(int vegetationPeriodInDays) {
        this.vegetationPeriodInDays = vegetationPeriodInDays;
    }

    public Date getHarvestingDate() {
        return harvestingDate;
    }

    public void setHarvestingDate(Date harvestingDate) {
        this.harvestingDate = harvestingDate;
    }

    public String getSpringFertilizer() {
        return springFertilizer;
    }

    public void setSpringFertilizer(String springFertilizer) {
        this.springFertilizer = springFertilizer;
    }

    public String getSummerFertilizer() {
        return summerFertilizer;
    }

    public void setSummerFertilizer(String summerFertilizer) {
        this.summerFertilizer = summerFertilizer;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

    public String getBadCompanion() {
        return badCompanion;
    }

    public void setBadCompanion(String badCompanion) {
        this.badCompanion = badCompanion;
    }
}


