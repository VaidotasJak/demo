package fun.app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponseDto {

    private Long id;
    private String doctorFullName;
    private Integer experienceInYear;
    private Boolean isLicenseValid;
    private LocalDate dateOfBirth;
    private List<PatientResponseDto> patients;

}
