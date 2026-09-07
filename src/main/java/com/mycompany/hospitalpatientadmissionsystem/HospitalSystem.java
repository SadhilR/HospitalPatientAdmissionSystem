package com.mycompany.hospitalpatientadmissionsystem;

import java.util.ArrayList;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private String[][] beds;
    private String[][] bedOccupants;

    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new String[4][5];
        bedOccupants = new String[4][5];

        initialiseBeds();
    }

    private void initialiseBeds() {
        int bedNumber = 1;

        for (int row = 0; row < beds.length; row++) {
            for (int column = 0; column < beds[row].length; column++) {
                beds[row][column] = String.format("B%02d", bedNumber);
                bedNumber++;
            }
        }
    }

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

    public boolean allocateBed(
            String patientId,
            String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

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

    public int getTotalPatients() {
        return patients.size();
    }

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

    public double getOccupancyPercentage() {

        int occupiedBeds = getTotalOccupiedBeds();
        int totalBeds = beds.length * beds[0].length;

        return ((double) occupiedBeds / totalBeds) * 100;
    }

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