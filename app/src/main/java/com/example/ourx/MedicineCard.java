package com.example.ourx;

public class MedicineCard {

    private String name;
    private String timeToTake;
    private boolean taken;

    MedicineCard(String medName, String time) {
        this.name = medName;
        this.timeToTake = time;
        this.taken = false;
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

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isTaken() {
        return taken;
    }
}
