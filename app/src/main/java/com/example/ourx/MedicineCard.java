package com.example.ourx;

public class MedicineCard {

    private String name;
    private String timeToTake;
    private boolean taken;
    private boolean skipped;

    MedicineCard(String medName, String time, boolean taken, boolean skipped) {
        this.name = medName;
        this.timeToTake = time;
        this.taken = taken;
        this.skipped = skipped;

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

    public boolean isSkipped() {return skipped;}
}
