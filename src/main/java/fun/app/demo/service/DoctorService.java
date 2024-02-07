package fun.app.demo.service;

import fun.app.demo.dto.DoctorRequestDto;
import fun.app.demo.dto.DoctorResponseDto;
import fun.app.demo.exception.NoDoctorsFoundException;
import fun.app.demo.model.Doctor;
import fun.app.demo.repository.DoctorRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final CacheManager cacheManager;
    private final Cache doctorCache;
    private final DoctorMappingService mappingService;
    private final PatientService patientService;

    public DoctorService(DoctorRepository doctorRepository, CacheManager cacheManager, DoctorMappingService mappingService, PatientService patientService) {
        this.doctorRepository = doctorRepository;
        this.cacheManager = cacheManager;
        this.doctorCache = cacheManager.getCache("doctorCache");
        this.mappingService = mappingService;
        this.patientService = patientService;
    }

    public List<DoctorResponseDto> getAllDoctorsDto() throws NoDoctorsFoundException {
        log.info("Looking for doctors in DB...");
        var doctors = doctorRepository.findAll();
        if(doctors.isEmpty()){
            log.error("No doctors were found in the DB!");
            throw new NoDoctorsFoundException("No doctors were found!");
        }
        log.info(doctors.size() + " doctors were found in DB.");
        return mappingService.mapToResponse(doctors);
    }


    public void deleteById(final Long id) throws NoDoctorsFoundException {
        log.info("Looking for doctor id = " + id);
        var doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            log.error("No doctors were found in the DB!");
            throw new NoDoctorsFoundException("No doctors were found!");
        }
        doctorRepository.deleteById(id);
        log.info("Doctor was deleted by given id = " + id);
    }

    public DoctorResponseDto getById(final Long id) throws NoDoctorsFoundException {
        log.info("Looking for doctor id = " + id);

        if(doctorCache != null && doctorCache.get(id) != null){
            log.info("Doctor was found in the cache. Skipping DB call!");
            return (DoctorResponseDto) doctorCache.get(id).get();
        }

        var doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            log.error("No doctors were found in the DB!");
            throw new NoDoctorsFoundException("No doctors were found!");
        }

        var patients = patientService.getAllPatient(doctor.get());
        DoctorResponseDto doctorResponseDto = mappingService.mapToDoctorResponseDto(doctor.get(), patients);

        doctorCache.put(id, doctorResponseDto);
        log.info("Doctor was found in the DB by given id = " + id);
        return doctorResponseDto;
    }

    public List<DoctorResponseDto> getExperiencedDoctors(final Integer years) throws NoDoctorsFoundException {
        var allDoctors = doctorRepository.findAll();
        var experiencedDoctors = allDoctors.stream()
                .filter(doctor -> doctor.getExperience() >= years)
                .collect(Collectors.toList());
        if(experiencedDoctors.isEmpty()){
            log.error("No doctors were found in the DB!");
            throw new NoDoctorsFoundException("No doctors were found!");
        }
        List<DoctorResponseDto> doctorResponseDtos = new ArrayList<>();
        for (Doctor doctor : experiencedDoctors) {
            var patients = patientService.getAllPatient(doctor);
            doctorResponseDtos.add(mappingService.mapToDoctorResponseDto(doctor, patients));
        }
        return doctorResponseDtos;
    }

    public List<DoctorResponseDto> add(final DoctorRequestDto doctorRequestDto) {

        var doctor = mappingService.mapToEntity(doctorRequestDto);
        doctorRepository.save(doctor);
        log.info("Doctor was saved successfully");
        var allDocotrs = doctorRepository.findAll();
        return mappingService.mapToResponse(allDocotrs);
    }
}
