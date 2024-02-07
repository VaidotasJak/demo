package fun.app.demo.service;

import fun.app.demo.exception.NoDoctorsFoundException;
import fun.app.demo.model.Doctor;
import fun.app.demo.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void getExperiencedDoctors() throws NoDoctorsFoundException {
        //Mock datas
        Doctor doc1 = new Doctor(1L, "Tom", 15, null);
        Doctor doc2 = new Doctor(2L, "Ann", 8, null);
        Doctor doc3 = new Doctor(3L, "Cuz", 20, null);

        var doctors = Arrays.asList(doc1, doc2, doc3);

        //Lets mock now
        Mockito.when(doctorRepository.findAll()).thenReturn(doctors);

        var experiencedDoctors = doctorService.getExperiencedDoctors(10);
        //Then
        assertEquals(2, experiencedDoctors.size());

    }
}