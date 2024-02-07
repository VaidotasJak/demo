package fun.app.demo.util;

import fun.app.demo.model.Doctor;
import fun.app.demo.model.Patient;
import fun.app.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataLoader implements CommandLineRunner {
    private final DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating test data...");
        var firstDoctor = Doctor.builder().name("Tom Jones").experience(5).dob(LocalDate.of(1980, 1, 15)).patients(new ArrayList<>()).build();
        var secondDoctor = Doctor.builder().name("Jane Doe").experience(10).dob(LocalDate.of(1976, 6, 12)).patients(new ArrayList<>()).build();
        var thirdDoctor = Doctor.builder().name("Rick Fox").experience(24).dob(LocalDate.of(1966, 3, 10)).patients(new ArrayList<>()).build();

        var patient1 = Patient.builder().name("Rick Grimes").diagnose("Fever").dob(LocalDate.of(1990, 1, 1)).doctor(firstDoctor).build();
        var patient2 = Patient.builder().name("Michael Scott").diagnose("Herpes").dob(LocalDate.of(1986, 4, 18)).doctor(firstDoctor).build();
        var patient3 = Patient.builder().name("Dwight Schrute").diagnose("Pneumonia").dob(LocalDate.of(1999, 7, 6)).doctor(secondDoctor).build();
        var patient4 = Patient.builder().name("Pam Beesly").diagnose("Diabetes").dob(LocalDate.of(1970, 12, 25)).doctor(secondDoctor).build();
        var patient5 = Patient.builder().name("Jim Halpert").diagnose("Morning sickness").dob(LocalDate.of(1964, 1, 30)).doctor(thirdDoctor).build();
        var patient6 = Patient.builder().name("Kelly Kapoor").diagnose("Cold").dob(LocalDate.of(1956, 6, 15)).doctor(thirdDoctor).build();

        List<Patient> patientsForTom = new ArrayList<>();
        patientsForTom.add(patient1);
        patientsForTom.add(patient2);
        List<Patient> patientsForJane = new ArrayList<>();
        patientsForJane.add(patient3);
        patientsForJane.add(patient4);
        List<Patient> patientsForRick = new ArrayList<>();
        patientsForRick.add(patient5);
        patientsForRick.add(patient6);

        firstDoctor.setPatients(patientsForTom);
        secondDoctor.setPatients(patientsForJane);
        thirdDoctor.setPatients(patientsForRick);

        doctorRepository.saveAll(Arrays.asList(firstDoctor, secondDoctor, thirdDoctor));
        log.info("Data creation complete");
    }
}
