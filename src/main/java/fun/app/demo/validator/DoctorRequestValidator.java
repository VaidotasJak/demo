package fun.app.demo.validator;

import fun.app.demo.dto.DoctorRequestDto;
import fun.app.demo.exception.MandatoryFieldsMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorRequestValidator {
    public void validateDoctorRequest(final DoctorRequestDto doctorRequestDto) throws MandatoryFieldsMissingException {
        if(doctorRequestDto == null || doctorRequestDto.getName() == null || doctorRequestDto.getName().isBlank()){
            log.error("Request was empty or mandatory NAME field was missing");
            throw new MandatoryFieldsMissingException("Mandatory name field was missing.");
        }
    }
}
