package com.mycompany.hospitalpatientadmissionsystem;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests the main functions of the hospital system
public class HospitalSystemTest {

    // Tests if a patient can be added and searched for
    @Test
    public void testAddAndSearchPatient() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                35,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        boolean added = hospitalSystem.registerPatient(patient);

        Patient foundPatient = hospitalSystem.searchPatient("P001");

        assertTrue(added);
        assertNotNull(foundPatient);
        assertEquals("John", foundPatient.getFirstName());
        assertEquals("Smith", foundPatient.getLastName());
    }

    // Tests if a patient's details can be updated
    @Test
    public void testUpdatePatient() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        Patient patient = new Patient(
                "P002",
                "Sarah",
                "Adams",
                28,
                "Female",
                "Migraine",
                PatientCategory.OUTPATIENT
        );

        hospitalSystem.registerPatient(patient);

        boolean updated = hospitalSystem.updatePatient(
                "P002",
                "Sarah",
                "Adams",
                29,
                "Female",
                "Severe Migraine"
        );

        Patient updatedPatient
                = hospitalSystem.searchPatient("P002");

        assertTrue(updated);
        assertNotNull(updatedPatient);
        assertEquals(29, updatedPatient.getAge());
        assertEquals(
                "Severe Migraine",
                updatedPatient.getMedicalCondition()
        );
    }

    // Tests if a patient can be deleted from the system
    @Test
    public void testDeletePatient() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        Patient patient = new Patient(
                "P003",
                "Mark",
                "Brown",
                42,
                "Male",
                "Chest Pain",
                PatientCategory.EMERGENCY
        );

        hospitalSystem.registerPatient(patient);

        boolean deleted
                = hospitalSystem.deletePatient("P003");

        Patient foundPatient
                = hospitalSystem.searchPatient("P003");

        assertTrue(deleted);
        assertNull(foundPatient);
        assertEquals(0, hospitalSystem.getTotalPatients());
    }

    // Tests if a bed can be allocated when one is available
    @Test
    public void testBedAllocationWhenAvailable() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        Inpatient inpatient = new Inpatient(
                "P004",
                "David",
                "Jones",
                50,
                "Male",
                "Surgery",
                PatientCategory.INPATIENT,
                "Ward 1",
                "Not Allocated"
        );

        hospitalSystem.registerPatient(inpatient);

        boolean allocated
                = hospitalSystem.allocateBed(
                        "P004",
                        "B01"
                );

        assertTrue(allocated);
        assertEquals("B01", inpatient.getBedNumber());
        assertEquals(
                1,
                hospitalSystem.getTotalOccupiedBeds()
        );
    }

    // Tests that another bed cannot be allocated when the ward is full
    @Test
    public void testBedAllocationWhenWardIsFull() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        for (int i = 1; i <= 20; i++) {

            String patientId
                    = String.format("P%03d", i);

            String bedNumber
                    = String.format("B%02d", i);

            Inpatient inpatient = new Inpatient(
                    patientId,
                    "Patient",
                    String.valueOf(i),
                    30,
                    "Male",
                    "Condition",
                    PatientCategory.INPATIENT,
                    "Ward 1",
                    "Not Allocated"
            );

            hospitalSystem.registerPatient(inpatient);

            boolean allocated
                    = hospitalSystem.allocateBed(
                            patientId,
                            bedNumber
                    );

            assertTrue(allocated);
        }

        Inpatient extraPatient = new Inpatient(
                "P999",
                "Extra",
                "Patient",
                40,
                "Female",
                "Condition",
                PatientCategory.INPATIENT,
                "Ward 1",
                "Not Allocated"
        );

        hospitalSystem.registerPatient(extraPatient);

        boolean allocated
                = hospitalSystem.allocateBed(
                        "P999",
                        "B01"
                );

        assertFalse(allocated);
        assertEquals(
                20,
                hospitalSystem.getTotalOccupiedBeds()
        );
    }

    // Tests if patients are sorted correctly by surname
    @Test
    public void testSortPatientsBySurname() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        hospitalSystem.registerPatient(
                new Patient(
                        "P001",
                        "John",
                        "Smith",
                        35,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                )
        );

        hospitalSystem.registerPatient(
                new Patient(
                        "P002",
                        "Sarah",
                        "Adams",
                        28,
                        "Female",
                        "Migraine",
                        PatientCategory.OUTPATIENT
                )
        );

        hospitalSystem.registerPatient(
                new Patient(
                        "P003",
                        "Mark",
                        "Brown",
                        42,
                        "Male",
                        "Chest Pain",
                        PatientCategory.EMERGENCY
                )
        );

        ArrayList<Patient> sortedPatients
                = hospitalSystem.sortPatientsBySurname();

        assertEquals(
                "Adams",
                sortedPatients.get(0).getLastName()
        );

        assertEquals(
                "Brown",
                sortedPatients.get(1).getLastName()
        );

        assertEquals(
                "Smith",
                sortedPatients.get(2).getLastName()
        );
    }

    // Tests if patients are sorted correctly by Patient ID
    @Test
    public void testSortPatientsByPatientId() {

        HospitalSystem hospitalSystem = new HospitalSystem();

        hospitalSystem.registerPatient(
                new Patient(
                        "P003",
                        "Mark",
                        "Brown",
                        42,
                        "Male",
                        "Chest Pain",
                        PatientCategory.EMERGENCY
                )
        );

        hospitalSystem.registerPatient(
                new Patient(
                        "P001",
                        "John",
                        "Smith",
                        35,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                )
        );

        hospitalSystem.registerPatient(
                new Patient(
                        "P002",
                        "Sarah",
                        "Adams",
                        28,
                        "Female",
                        "Migraine",
                        PatientCategory.OUTPATIENT
                )
        );

        ArrayList<Patient> sortedPatients
                = hospitalSystem.sortPatientsByPatientId();

        assertEquals(
                "P001",
                sortedPatients.get(0).getPatientId()
        );

        assertEquals(
                "P002",
                sortedPatients.get(1).getPatientId()
        );

        assertEquals(
                "P003",
                sortedPatients.get(2).getPatientId()
        );
    }
}
