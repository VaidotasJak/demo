package fun.app.demo.service;

import fun.app.demo.dto.DoctorRequestDto;
import fun.app.demo.dto.DoctorResponseDto;
import fun.app.demo.dto.PatientResponseDto;
import fun.app.demo.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorMappingService {

 public Doctor mapToEntity(final DoctorRequestDto doctorRequestDto){
     return Doctor.builder()
             .name(doctorRequestDto.getName())
             .experience(doctorRequestDto.getExperience())
             .dob(doctorRequestDto.getDob())
             .build();
 }

 public List<DoctorResponseDto> mapToResponse(List<Doctor> allDocotrs){
     List<DoctorResponseDto> mappedDoctors = new ArrayList<>();
     for (Doctor doctor : allDocotrs){
         DoctorResponseDto dto = new DoctorResponseDto();
         dto.setId(doctor.getId());
         dto.setDoctorFullName(doctor.getName());
         dto.setDateOfBirth(doctor.getDob());
         dto.setExperienceInYear(doctor.getExperience());
         dto.setIsLicenseValid(true);
         mappedDoctors.add(dto);
     }
     return mappedDoctors;
 }

    public DoctorResponseDto mapToDoctorResponseDto(Doctor doctor, List<PatientResponseDto> patientResponseDtos){
            DoctorResponseDto dto = new DoctorResponseDto();
            dto.setId(doctor.getId());
            dto.setDoctorFullName(doctor.getName());
            dto.setDateOfBirth(doctor.getDob());
            dto.setExperienceInYear(doctor.getExperience());
            dto.setIsLicenseValid(true);
            dto.setPatients(patientResponseDtos);
        return dto;
    }
}
