package fun.app.demo.service;

import fun.app.demo.dto.PatientResponseDto;
import fun.app.demo.exception.NoDoctorsFoundException;
import fun.app.demo.model.Doctor;
import fun.app.demo.repository.PatientRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMappingService patientMappingService;

    public List<PatientResponseDto> getAllPatient(Doctor doctor) {
        log.info("Looking for doctors in DB...");
        var patients = patientRepository.findByDoctor(doctor);
        if(patients.isEmpty()){
            log.error("No patients were found in the DB!");
            patients = Collections.emptyList();
        }
        log.info(patients.size() + " patiens were found in DB.");
        return patientMappingService.mapToResponse(patients);
    }

}
