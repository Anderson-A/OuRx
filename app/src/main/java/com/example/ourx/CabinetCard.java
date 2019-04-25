package com.example.ourx;

/* CabinetCardAdapter uses MedicineEntity directly, this class no longer used TODO:delete this file */
public class CabinetCard {

    private String name;
    private String dosage;
    //private Boolean takeWithFood;
    //private Boolean takeWithWater;
    private String instructions;
    private String unit;
    private String timeToTake;
    private boolean taken;
    //private String nextReminder;

    public CabinetCard(String name, String dosage, String units, String instructions/*, String time*/) {
        this.name = name;
        this.dosage = dosage;
        this.unit = units;
        this.instructions = instructions;
        //this.taken = false;
        //this.timeToTake = time;
    }

    public String getDosage() { return this.dosage; }

    public String getInstructions() { return this.instructions; }

    public String getCabinetName() {
        return this.name;
    }

    public String getUnit() { return this.unit; }

    //public Boolean getTakeWithFood() { return this.takeWithFood; }

    //public Boolean getTakeWithWater() { return this.takeWithWater; }

    //public String getTimeToTake { return this.timeToTake; }

    //public boolean isTaken() { return taken; }
}
