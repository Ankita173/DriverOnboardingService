package com.rider.driver.controller;

import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.service.DriverOnboardingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverOnboardingControllerTest {

    @Mock
    private DriverOnboardingService driverOnboardingService;

    @InjectMocks
    private DriverOnboardingController driverOnboardingController;

    @Test
    void testRegisterDriver() {
        // Mock data
        DriverOnboardingRequest driver = DriverOnboardingRequest.builder().build();
        String responseMessage = "New Driver added";

        // Mock behavior
        when(driverOnboardingService.registerDriver(any(DriverOnboardingRequest.class)))
                .thenReturn(new ResponseEntity<>(responseMessage, HttpStatus.OK));

        // Call the controller method
        ResponseEntity<String> response = driverOnboardingController.registerDriver(driver);

        // Verify the response
        assertEquals(responseMessage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginDriver() {
        // Mock data
        DriverOnboardingRequest driver = DriverOnboardingRequest.builder().build();
        String responseMessage = "Driver login successfully";

        // Mock behavior
        when(driverOnboardingService.loginDriver(any(DriverOnboardingRequest.class)))
                .thenReturn(new ResponseEntity<>(responseMessage, HttpStatus.OK));

        // Call the controller method
        ResponseEntity<String> response = driverOnboardingController.loginDriver(driver);

        // Verify the response
        assertEquals(responseMessage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
