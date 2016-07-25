package com.james.entities;

import javax.persistence.*;

/**
 * Created by user on 7/25/16.
 */
@Entity
@Table(name = "medications")
public class Medication {
    @GeneratedValue
    @Id
    int medId;

    @Column (nullable = false)
    String medName;

    @Column(nullable = false)
    String dose;

    @Column(nullable = false)
    int frequency;

    String instructions;

    public Medication() {
    }

    public Medication(String medName, String dose, int frequency, String instructions) {
        this.medName = medName;
        this.dose = dose;
        this.frequency = frequency;
        this.instructions = instructions;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
