// Code Attributions: HospitalSystem

// Code attribution:
// Title: List Implementations
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/collections/implementations/list.html

// Code attribution:
// Title: Arrays
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html

// Code attribution:
// Title: Inheritance
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html

// Code attribution:
// Title: Comparing Strings and Portions of Strings
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/data/comparestrings.html

package com.mycompany.hospitalpatientadmissionsystem;

import java.util.ArrayList;

// Handles patient records, bed management and hospital reports
public class HospitalSystem {

    // Stores all patients registered in the system
    private ArrayList<Patient> patients;
    
    // 2D arrays used for the 20 hospital beds and their occupants
    private String[][] beds;
    private String[][] bedOccupants;

    // Creates the patient list and sets up the 4 x 5 ward layout
    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new String[4][5];
        bedOccupants = new String[4][5];

        initialiseBeds();
    }

    // Gives each bed a number from B01 to B20
    private void initialiseBeds() {
        int bedNumber = 1;

        for (int row = 0; row < beds.length; row++) {
            for (int column = 0; column < beds[row].length; column++) {
                beds[row][column] = String.format("B%02d", bedNumber);
                bedNumber++;
            }
        }
    }

    // Adds a patient if their Patient ID is not already registered
    public boolean registerPatient(Patient patient) {

        if (patient == null) {
            return false;
        }

        if (patient.getPatientId() == null
                || patient.getPatientId().trim().isEmpty()) {
            return false;
        }

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    // Searches for a patient using their Patient ID
    public Patient searchPatient(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            return null;
        }

        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

        return null;
    }

    // Updates the details of an existing patient
    public boolean updatePatient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (age <= 0) {
            return false;
        }

        if (firstName == null || firstName.trim().isEmpty()
                || lastName == null || lastName.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || medicalCondition == null
                || medicalCondition.trim().isEmpty()) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);

        return true;
    }

    // Deletes a patient and releases their bed if they have one
    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;

            if (inpatient.getBedNumber() != null
                    && !inpatient.getBedNumber()
                            .equalsIgnoreCase("Not Allocated")) {

                releaseBed(patientId);
            }
        }

        patients.remove(patient);
        return true;
    }

    // Displays the details of every registered patient
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println(
                    "No patients are currently registered."
            );
            return;
        }

        System.out.println(
                "\n===== REGISTERED PATIENTS ====="
        );

        for (Patient patient : patients) {
            patient.displayDetails();
            System.out.println(
                    "------------------------------"
            );
        }
    }

    // Allocates an available bed to an inpatient
    public boolean allocateBed(
            String patientId,
            String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Only inpatients are allowed to receive a bed
        if (!(patient instanceof Inpatient)) {
            return false;
        }

        if (bedNumber == null || bedNumber.trim().isEmpty()) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        if (inpatient.getBedNumber() != null
                && !inpatient.getBedNumber()
                        .equalsIgnoreCase("Not Allocated")) {

            return false;
        }

        // Searches the ward for the bed entered by the user
        for (int row = 0; row < beds.length; row++) {
            for (int column = 0;
                    column < beds[row].length;
                    column++) {

                if (beds[row][column]
                        .equalsIgnoreCase(bedNumber)) {

                    if (bedOccupants[row][column] != null) {
                        return false;
                    }

                    bedOccupants[row][column] = patientId;
                    inpatient.setBedNumber(
                            beds[row][column]
                    );

                    return true;
                }
            }
        }

        return false;
    }

    // Releases the bed currently assigned to an inpatient
    public boolean releaseBed(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;
        String bedNumber = inpatient.getBedNumber();

        if (bedNumber == null
                || bedNumber.equalsIgnoreCase(
                        "Not Allocated")) {

            return false;
        }

        for (int row = 0; row < beds.length; row++) {
            for (int column = 0;
                    column < beds[row].length;
                    column++) {

                if (beds[row][column]
                        .equalsIgnoreCase(bedNumber)
                        && patientId.equalsIgnoreCase(
                                bedOccupants[row][column])) {

                    bedOccupants[row][column] = null;
                    inpatient.setBedNumber(
                            "Not Allocated"
                    );

                    return true;
                }
            }
        }

        return false;
    }

    // Shows all 20 beds and whether each one is available or occupied
    public void displayWardLayout() {

        System.out.println(
                "\n===== WARD BED LAYOUT ====="
        );

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0;
                    column < beds[row].length;
                    column++) {

                if (bedOccupants[row][column] == null) {
                    System.out.print(
                            beds[row][column]
                            + " [Available]\t"
                    );
                } else {
                    System.out.print(
                            beds[row][column]
                            + " [Occupied]\t"
                    );
                }
            }

            System.out.println();
        }
    }

    // Displays all beds that are currently available
    public void displayAvailableBeds() {

        System.out.println(
                "\n===== AVAILABLE BEDS ====="
        );

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0;
                    column < beds[row].length;
                    column++) {

                if (bedOccupants[row][column] == null) {
                    System.out.println(
                            beds[row][column]
                    );
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println(
                    "No beds are currently available."
            );
        }
    }

    // Displays occupied beds together with the patient's ID
    public void displayOccupiedBeds() {

        System.out.println(
                "\n===== OCCUPIED BEDS ====="
        );

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0;
                    column < beds[row].length;
                    column++) {

                if (bedOccupants[row][column] != null) {

                    System.out.println(
                            beds[row][column]
                            + " - Patient ID: "
                            + bedOccupants[row][column]
                    );

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println(
                    "No beds are currently occupied."
            );
        }
    }

    // Returns the total number of registered patients
    public int getTotalPatients() {
        return patients.size();
    }

    // Counts how many beds are currently occupied
    public int getTotalOccupiedBeds() {

        int occupiedBeds = 0;

        for (int row = 0;
                row < bedOccupants.length;
                row++) {

            for (int column = 0;
                    column < bedOccupants[row].length;
                    column++) {

                if (bedOccupants[row][column] != null) {
                    occupiedBeds++;
                }
            }
        }

        return occupiedBeds;
    }

    // Calculates the percentage of beds currently occupied
    public double getOccupancyPercentage() {

        int occupiedBeds = getTotalOccupiedBeds();
        int totalBeds = beds.length * beds[0].length;

        return ((double) occupiedBeds / totalBeds) * 100;
    }

    // Uses Bubble Sort to arrange patients alphabetically by surname
    public ArrayList<Patient> sortPatientsBySurname() {

        ArrayList<Patient> sortedPatients
                = new ArrayList<>(patients);

        for (int i = 0;
                i < sortedPatients.size() - 1;
                i++) {

            for (int j = 0;
                    j < sortedPatients.size() - 1 - i;
                    j++) {

                if (sortedPatients.get(j)
                        .getLastName()
                        .compareToIgnoreCase(
                                sortedPatients.get(j + 1)
                                        .getLastName()) > 0) {

                    Patient temp
                            = sortedPatients.get(j);

                    sortedPatients.set(
                            j,
                            sortedPatients.get(j + 1)
                    );

                    sortedPatients.set(
                            j + 1,
                            temp
                    );
                }
            }
        }

        return sortedPatients;
    }

    // Uses Bubble Sort to arrange patients by Patient ID
    public ArrayList<Patient> sortPatientsByPatientId() {

        ArrayList<Patient> sortedPatients
                = new ArrayList<>(patients);

        for (int i = 0;
                i < sortedPatients.size() - 1;
                i++) {

            for (int j = 0;
                    j < sortedPatients.size() - 1 - i;
                    j++) {

                if (sortedPatients.get(j)
                        .getPatientId()
                        .compareToIgnoreCase(
                                sortedPatients.get(j + 1)
                                        .getPatientId()) > 0) {

                    // Swaps the patients if they are in the wrong order
                    Patient temp
                            = sortedPatients.get(j);

                    sortedPatients.set(
                            j,
                            sortedPatients.get(j + 1)
                    );

                    sortedPatients.set(
                            j + 1,
                            temp
                    );
                }
            }
        }

        return sortedPatients;
    }

    // Displays patients after sorting them by surname
    public void displayPatientsSortedBySurname() {

        ArrayList<Patient> sortedPatients
                = sortPatientsBySurname();

        if (sortedPatients.isEmpty()) {
            System.out.println(
                    "No patients are currently registered."
            );
            return;
        }

        System.out.println(
                "\n===== PATIENTS SORTED BY SURNAME ====="
        );

        for (Patient patient : sortedPatients) {
            patient.displayDetails();
            System.out.println(
                    "------------------------------"
            );
        }
    }

    // Displays patients after sorting them by Patient ID
    public void displayPatientsSortedByPatientId() {

        ArrayList<Patient> sortedPatients
                = sortPatientsByPatientId();

        if (sortedPatients.isEmpty()) {
            System.out.println(
                    "No patients are currently registered."
            );
            return;
        }

        System.out.println(
                "\n===== PATIENTS SORTED BY PATIENT ID ====="
        );

        for (Patient patient : sortedPatients) {
            patient.displayDetails();
            System.out.println(
                    "------------------------------"
            );
        }
    }

    // Displays a summary of the patients and current ward usage
    public void displayWardReport() {

        System.out.println(
                "\n===== WARD REPORT ====="
        );

        displayAllPatients();

        System.out.println(
                "Total Registered Patients: "
                + getTotalPatients()
        );

        System.out.println(
                "Total Occupied Beds: "
                + getTotalOccupiedBeds()
        );

        System.out.printf(
                "Ward Occupancy Percentage: %.2f%%%n",
                getOccupancyPercentage()
        );

        displayAvailableBeds();
        displayOccupiedBeds();
    }
}
