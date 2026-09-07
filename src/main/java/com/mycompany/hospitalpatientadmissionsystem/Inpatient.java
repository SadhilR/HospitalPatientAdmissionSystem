package com.mycompany.hospitalpatientadmissionsystem;

// Represents an inpatient who can be assigned to a ward and bed
public class Inpatient extends Patient {

    private String wardNumber;
    private String bedNumber;

    // Creates an inpatient with their patient, ward and bed details
    public Inpatient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition,
            PatientCategory patientCategory,
            String wardNumber,
            String bedNumber) {

        super(
                patientId,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                patientCategory
        );

        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    // Displays the patient details together with their ward and bed
    @Override
    public void displayDetails() {
        super.displayDetails();

        System.out.println(
                "Ward Number: " + wardNumber
        );

        System.out.println(
                "Bed Number: " + bedNumber
        );
    }
}
