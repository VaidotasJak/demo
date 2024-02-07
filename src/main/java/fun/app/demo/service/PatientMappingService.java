package fun.app.demo.service;

import fun.app.demo.dto.DoctorRequestDto;
import fun.app.demo.dto.DoctorResponseDto;
import fun.app.demo.dto.PatientResponseDto;
import fun.app.demo.model.Doctor;
import fun.app.demo.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientMappingService {

// public Doctor mapToEntity(final DoctorRequestDto doctorRequestDto){
//     return Doctor.builder()
//             .name(doctorRequestDto.getName())
//             .experience(doctorRequestDto.getExperience())
//             .dob(doctorRequestDto.getDob())
//             .build();
// }

 public List<PatientResponseDto> mapToResponse(List<Patient> allPatients){
     List<PatientResponseDto> mappedPatients = new ArrayList<>();
     for (Patient patient : allPatients) {
         PatientResponseDto dto = new PatientResponseDto();
         dto.setId(patient.getId());
         dto.setName(patient.getName());
         dto.setDiagnose(patient.getDiagnose());
         dto.setDob(patient.getDob());
         mappedPatients.add(dto);
     }
     return mappedPatients;
 }

//    public DoctorResponseDto mapToDoctorResponseDto(Doctor doctor){
//            DoctorResponseDto dto = new DoctorResponseDto();
//            dto.setId(doctor.getId());
//            dto.setDoctorFullName(doctor.getName());
//            dto.setDateOfBirth(doctor.getDob());
//            dto.setExperienceInYear(doctor.getExperience());
//            dto.setIsLicenseValid(true);
//        return dto;
//    }
}
