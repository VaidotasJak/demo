package fun.app.demo.controller;

import fun.app.demo.dto.DoctorRequestDto;
import fun.app.demo.exception.MandatoryFieldsMissingException;
import fun.app.demo.exception.NoDoctorsFoundException;
import fun.app.demo.service.DoctorService;
import fun.app.demo.service.PatientService;
import fun.app.demo.validator.DoctorRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/doctor")
@Slf4j
@RequiredArgsConstructor
public class DoctorController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DoctorRequestValidator doctorRequestValidator;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        try {
            var doctorResponseDto = doctorService.getAllDoctorsDto();
            return ResponseEntity.status(HttpStatus.OK).body(doctorResponseDto);
        } catch (NoDoctorsFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e){
            log.error("Unexcpected exception happend!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected exception!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id){
            try{
                var doctor = doctorService.getById(id);
                return ResponseEntity.status(HttpStatus.OK).body(doctor);
            } catch (NoDoctorsFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
    }

    @GetMapping("/experience/{years}")
    public ResponseEntity<?> findExperienced(@PathVariable final Integer years){
        try{
            var doctors = doctorService.getExperiencedDoctors(years);
            return ResponseEntity.status(HttpStatus.OK).body(doctors);
        } catch (NoDoctorsFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody final DoctorRequestDto doctorRequestDto){
        try{
            doctorRequestValidator.validateDoctorRequest(doctorRequestDto);
            final var doctors = doctorService.add(doctorRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(doctors);
        } catch(MandatoryFieldsMissingException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id){
        try{
            doctorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Doctor was successfully deleted!");
        }
        catch(NoDoctorsFoundException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
