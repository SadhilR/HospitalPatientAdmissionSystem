// Code Attributions: Patient

// Code attribution:
// Title: Declaring Member Variables
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/javaOO/variables.html

// Code attribution:
// Title: Providing Constructors for Your Classes
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html

// Code attribution:
// Title: Controlling Access to Members of a Class
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html

// Code attribution:
// Title: Defining Methods
// Author: Oracle
// Date: 7 September 2026
// Version: 1
// Availability: https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html

package com.mycompany.hospitalpatientadmissionsystem;

// Stores the basic information for a patient
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory patientCategory;

    // Creates a patient with all their personal and medical details
    public Patient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition,
            PatientCategory patientCategory) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.patientCategory = patientCategory;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public PatientCategory getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(
            PatientCategory patientCategory) {

        this.patientCategory = patientCategory;
    }

    // Displays all the patient's details
    public void displayDetails() {
        System.out.println(
                "Patient ID: " + patientId
        );
        System.out.println(
                "Name: " + firstName + " " + lastName
        );
        System.out.println(
                "Age: " + age
        );
        System.out.println(
                "Gender: " + gender
        );
        System.out.println(
                "Medical Condition: " + medicalCondition
        );
        System.out.println(
                "Patient Category: " + patientCategory
        );
    }
}
