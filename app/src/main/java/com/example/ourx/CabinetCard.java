package com.example.ourx;

public class CabinetCard {

    private String name;
    private String dosage;
    //private Boolean takeWithFood;
    //private Boolean takeWithWater;
    private String instructions;
    //private String nextReminder;

    public CabinetCard(String name, String dosage, String instructions) {
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public String getDosage() { return this.dosage; }

    public String getInstructions() { return this.instructions; }

    public String getCabinetName() {
        return this.name;
    }
}
