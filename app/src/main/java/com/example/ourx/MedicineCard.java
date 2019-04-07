package com.example.ourx;

import java.sql.Time;

public class MedicineCard {

    private String name;
    private String timeToTake;

    MedicineCard(String medName, String time) {
        this.name = medName;
        this.timeToTake = time;
    }

    public String getName() {
        return name;
    }

    public String getTimeToTake() {
        return timeToTake;
    }

    public void setTimeToTake(String timeToTake) {
        this.timeToTake = timeToTake;
    }
}
