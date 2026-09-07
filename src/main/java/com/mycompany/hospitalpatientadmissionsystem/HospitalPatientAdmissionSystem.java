package com.mycompany.hospitalpatientadmissionsystem;

import java.util.Scanner;

// Main class for running the hospital patient admission system
public class HospitalPatientAdmissionSystem {

    // Starts the program and displays the menu to the user
    public static void main(String[] args) {

        // Scanner is used to get input from the user
        Scanner scanner = new Scanner(System.in);
        HospitalSystem hospitalSystem = new HospitalSystem();

        int choice;

        // Keeps displaying the menu until the user chooses to exit
        do {
            System.out.println("\n===== HOSPITAL PATIENT ADMISSION SYSTEM =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Allocate Bed");
            System.out.println("6. Release Bed");
            System.out.println("7. Display Ward Layout");
            System.out.println("8. Display Available Beds");
            System.out.println("9. Display Occupied Beds");
            System.out.println("10. Display All Patients");
            System.out.println("11. Display Ward Report");
            System.out.println("12. Sort Patients by Surname");
            System.out.println("13. Sort Patients by Patient ID");
            System.out.println("0. Exit");

            choice = readInteger(
                    scanner,
                    "Enter your choice: "
            );

            // Runs the option selected by the user
            switch (choice) {

                // Register a new patient
                case 1: {
                    System.out.println(
                            "\n===== REGISTER PATIENT ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId = scanner.nextLine().trim();

                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine().trim();

                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine().trim();

                    int age = readInteger(
                            scanner,
                            "Enter Age: "
                    );

                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine().trim();

                    System.out.print("Enter Medical Condition: ");
                    String medicalCondition = scanner.nextLine().trim();

                    if (patientId.isEmpty()
                            || firstName.isEmpty()
                            || lastName.isEmpty()
                            || gender.isEmpty()
                            || medicalCondition.isEmpty()) {

                        System.out.println(
                                "Patient details cannot be empty."
                        );
                        break;
                    }

                    if (age <= 0) {
                        System.out.println(
                                "Invalid age. Age must be greater than 0."
                        );
                        break;
                    }

                    System.out.println(
                            "\nSelect Patient Category:"
                    );
                    System.out.println("1. Inpatient");
                    System.out.println("2. Outpatient");
                    System.out.println("3. Emergency");

                    int categoryChoice = readInteger(
                            scanner,
                            "Enter category: "
                    );

                    PatientCategory patientCategory = null;

                    switch (categoryChoice) {
                        case 1:
                            patientCategory
                                    = PatientCategory.INPATIENT;
                            break;

                        case 2:
                            patientCategory
                                    = PatientCategory.OUTPATIENT;
                            break;

                        case 3:
                            patientCategory
                                    = PatientCategory.EMERGENCY;
                            break;

                        default:
                            System.out.println(
                                    "Invalid patient category."
                            );
                            break;
                    }

                    if (patientCategory == null) {
                        break;
                    }

                    Patient newPatient;

                    if (patientCategory
                            == PatientCategory.INPATIENT) {

                        newPatient = new Inpatient(
                                patientId,
                                firstName,
                                lastName,
                                age,
                                gender,
                                medicalCondition,
                                patientCategory,
                                "Ward 1",
                                "Not Allocated"
                        );

                    } else {

                        newPatient = new Patient(
                                patientId,
                                firstName,
                                lastName,
                                age,
                                gender,
                                medicalCondition,
                                patientCategory
                        );
                    }

                    if (hospitalSystem.registerPatient(
                            newPatient)) {

                        System.out.println(
                                "Patient registered successfully."
                        );

                    } else {

                        System.out.println(
                                "Patient registration failed. "
                                + "Patient ID may already exist."
                        );
                    }

                    break;
                }

                // Search for a patient using their ID
                case 2: {
                    System.out.println(
                            "\n===== SEARCH PATIENT ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId = scanner.nextLine().trim();

                    Patient patient
                            = hospitalSystem.searchPatient(
                                    patientId
                            );

                    if (patient == null) {
                        System.out.println(
                                "Patient not found."
                        );
                    } else {
                        System.out.println(
                                "\nPatient found:"
                        );
                        patient.displayDetails();
                    }

                    break;
                }

                // Update an existing patient's details
                case 3: {
                    System.out.println(
                            "\n===== UPDATE PATIENT ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId = scanner.nextLine().trim();

                    Patient patient
                            = hospitalSystem.searchPatient(
                                    patientId
                            );

                    if (patient == null) {
                        System.out.println(
                                "Patient not found."
                        );
                        break;
                    }

                    System.out.print(
                            "Enter New First Name: "
                    );
                    String firstName
                            = scanner.nextLine().trim();

                    System.out.print(
                            "Enter New Last Name: "
                    );
                    String lastName
                            = scanner.nextLine().trim();

                    int age = readInteger(
                            scanner,
                            "Enter New Age: "
                    );

                    System.out.print(
                            "Enter New Gender: "
                    );
                    String gender
                            = scanner.nextLine().trim();

                    System.out.print(
                            "Enter New Medical Condition: "
                    );
                    String medicalCondition
                            = scanner.nextLine().trim();

                    if (firstName.isEmpty()
                            || lastName.isEmpty()
                            || gender.isEmpty()
                            || medicalCondition.isEmpty()) {

                        System.out.println(
                                "Patient details cannot be empty."
                        );
                        break;
                    }

                    if (age <= 0) {
                        System.out.println(
                                "Invalid age. Age must be greater than 0."
                        );
                        break;
                    }

                    boolean updated
                            = hospitalSystem.updatePatient(
                                    patientId,
                                    firstName,
                                    lastName,
                                    age,
                                    gender,
                                    medicalCondition
                            );

                    if (updated) {
                        System.out.println(
                                "Patient updated successfully."
                        );
                    } else {
                        System.out.println(
                                "Patient update failed."
                        );
                    }

                    break;
                }

                // Delete a patient from the system
                case 4: {
                    System.out.println(
                            "\n===== DELETE PATIENT ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId
                            = scanner.nextLine().trim();

                    boolean deleted
                            = hospitalSystem.deletePatient(
                                    patientId
                            );

                    if (deleted) {
                        System.out.println(
                                "Patient deleted successfully."
                        );
                    } else {
                        System.out.println(
                                "Patient not found."
                        );
                    }

                    break;
                }

                // Allocate a bed to an inpatient
                case 5: {
                    System.out.println(
                            "\n===== ALLOCATE BED ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId
                            = scanner.nextLine().trim();

                    System.out.print(
                            "Enter Bed Number (B01 - B20): "
                    );
                    String bedNumber
                            = scanner.nextLine().trim();

                    boolean allocated
                            = hospitalSystem.allocateBed(
                                    patientId,
                                    bedNumber
                            );

                    if (allocated) {
                        System.out.println(
                                "Bed allocated successfully."
                        );
                    } else {
                        System.out.println(
                                "Bed allocation failed. "
                                + "Check the Patient ID, "
                                + "patient category, "
                                + "bed number, "
                                + "or bed availability."
                        );
                    }

                    break;
                }

                // Release a bed from an inpatient
                case 6: {
                    System.out.println(
                            "\n===== RELEASE BED ====="
                    );

                    System.out.print("Enter Patient ID: ");
                    String patientId
                            = scanner.nextLine().trim();

                    boolean released
                            = hospitalSystem.releaseBed(
                                    patientId
                            );

                    if (released) {
                        System.out.println(
                                "Bed released successfully."
                        );
                    } else {
                        System.out.println(
                                "Bed release failed. "
                                + "Check the Patient ID "
                                + "or whether the patient "
                                + "currently has a bed."
                        );
                    }

                    break;
                }

                case 7: {
                    hospitalSystem.displayWardLayout();
                    break;
                }

                case 8: {
                    hospitalSystem.displayAvailableBeds();
                    break;
                }

                case 9: {
                    hospitalSystem.displayOccupiedBeds();
                    break;
                }

                case 10: {
                    hospitalSystem.displayAllPatients();
                    break;
                }

                case 11: {
                    hospitalSystem.displayWardReport();
                    break;
                }

                case 12: {
                    hospitalSystem
                            .displayPatientsSortedBySurname();
                    break;
                }

                case 13: {
                    hospitalSystem
                            .displayPatientsSortedByPatientId();
                    break;
                }

                case 0: {
                    System.out.println(
                            "Exiting Hospital Patient "
                            + "Admission System."
                    );
                    break;
                }

                default: {
                    System.out.println(
                            "Invalid option. "
                            + "Please try again."
                    );
                    break;
                }
            }

        } while (choice != 0);

        scanner.close();
    }

    // Makes sure the user enters a valid whole number
    private static int readInteger(
            Scanner scanner,
            String prompt) {

        while (true) {

            try {
                System.out.print(prompt);
                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                // Displays an error if the user enters something that is not a number
                System.out.println(
                        "Invalid input. "
                        + "Please enter a whole number."
                );
            }
        }
    }
}