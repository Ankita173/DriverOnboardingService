package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.DriverCredential;
import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.repo.DriverOnboardingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverOnboardingServiceTest {
    @Mock
    private DriverOnboardingRepository onboardingRepository;

    @Mock
    private DriverDatabaseDAO driverProvider;

    @InjectMocks
    private DriverOnboardingService driverService;

    @Test
    void registerDriver_Success() {
        // Mock data
        DriverOnboardingRequest request = DriverOnboardingRequest.builder()
                .username("dummy")
                .password("abc").build();

        // Mock behavior
        when(onboardingRepository.save(any())).thenReturn(mock(DriverCredential.class));
        doNothing().when(driverProvider).insert(any());

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.registerDriver(request);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New Driver added", responseEntity.getBody());

        verify(onboardingRepository).save(any());
        verify(driverProvider).insert(any());
    }

    @Test
    void registerDriver_Failure() {
        // Mock data
        DriverOnboardingRequest request = driverOnboardingrequest();

        // Mock behavior
        when(onboardingRepository.save(any())).thenThrow(new RuntimeException("Some error occurred"));

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.registerDriver(request);

        // Verify behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Some error occurred", responseEntity.getBody());

        verify(onboardingRepository).save(any());
        verifyNoInteractions(driverProvider);
    }

    @Test
    void loginDriver_Success() {
        // Mock data
        DriverOnboardingRequest request = driverOnboardingrequest();
        DriverCredential driverCredential = DriverCredential.builder()
                .username("dummy")
                .password("YWJj").build();

        // Mock behavior
        when(onboardingRepository.getByUsername(request.getUsername())).thenReturn(driverCredential);

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.loginDriver(request);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Driver login successfully", responseEntity.getBody());

        verify(onboardingRepository).getByUsername(request.getUsername());
    }

    @Test
    void loginDriver_NoCredential() {
        // Mock data
        DriverOnboardingRequest request = driverOnboardingrequest();

        // Mock behavior
        when(onboardingRepository.getByUsername(request.getUsername())).thenReturn(null);

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.loginDriver(request);

        // Verify behavior
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Login Denied", responseEntity.getBody());

        verify(onboardingRepository).getByUsername(request.getUsername());
    }

    @Test
    void loginDriver_WrongPassword() {
        // Mock data
        DriverOnboardingRequest request = driverOnboardingrequest();
        DriverCredential driverCredential = DriverCredential.builder()
                .username("dummy")
                .password("def").build();

        // Mock behavior
        when(onboardingRepository.getByUsername(request.getUsername())).thenReturn(driverCredential);

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.loginDriver(request);

        // Verify behavior
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Login Denied", responseEntity.getBody());

        verify(onboardingRepository).getByUsername(request.getUsername());
    }

    private DriverOnboardingRequest driverOnboardingrequest(){
        return DriverOnboardingRequest.builder()
                .username("dummy")
                .password("abc").build();
    }

}
