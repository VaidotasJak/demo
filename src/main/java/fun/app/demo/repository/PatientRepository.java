package fun.app.demo.repository;

import fun.app.demo.model.Doctor;
import fun.app.demo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctor(Doctor parent);
}
