package com.example.ourx;

/* No longer used due to Room database, using MedicineEntity instead. TODO: delete this file */
public class Medication {

    private String name;
    private String dosage;
    private String unit;
    private int withFood;
    private int withWater;
    private String[] times;
    private int sunday;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private String instructions;

    public Medication(String name, String dosage, String unit, int food, int water,
                      String[] times, int sun, int mon, int tue, int wed, int thu,
                      int fri, int sat, String instr) {
        this.name = name;
        this.dosage = dosage;
        this.unit = unit;
        this.withFood = food;
        this.withWater = water;
        this.times = times;
        this.sunday = sun;
        this.monday = mon;
        this.tuesday = tue;
        this.wednesday = wed;
        this.thursday = thu;
        this.friday = fri;
        this.saturday = sat;
        this.instructions = instr;
    }

    public String getName() { return name; }

    public String getDosage() { return dosage; }

    public String getUnit() { return unit; }

    public int getWithFood() { return withFood; }

    public int getWithWater() { return withWater; }

    public String[] getTimes() { return times; }

    public int getSunday() { return sunday; }

    public int getMonday() { return monday; }

    public int getTuesday() { return tuesday; }

    public int getWednesday() { return wednesday; }

    public int getThursday() { return thursday; }

    public int getFriday() { return friday; }

    public int getSaturday() { return saturday; }

    public String getInstructions() { return instructions; }
}
