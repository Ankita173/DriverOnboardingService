package com.rider.driver.controller;


import com.rider.driver.entity.Driver;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverControllerTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    @Test
    void testFindAllDriver() {
        // Mock data
        List<Driver> drivers = new ArrayList<>();
        // Populate drivers with some data

        // Mock behavior
        when(driverService.findAllDriver()).thenReturn(ResponseEntity.ok(drivers));

        // Call the controller method
        ResponseEntity<List<Driver>> response = driverController.findAllDriver();

        // Verify the response
        assertEquals(drivers, response.getBody());
    }

    @Test
    void testFindDriverById() {
        // Mock data
        long driverId = 1L;
        Driver driver = Driver.builder()
                .driverId(driverId)
                .username("dummy").build();

        // Mock behavior
        when(driverService.findDriverById(driverId)).thenReturn(ResponseEntity.ok(driver));

        // Call the controller method
        ResponseEntity<Driver> response = driverController.findDriverById(driverId);

        // Verify the response
        assertEquals(driver, response.getBody());
    }

    @Test
    void testUpdateDriverProfile() {
        // Mock data
        long driverId = 1L;
        DriverRequest driverRequest = DriverRequest.builder().build();

        // Mock behavior
        when(driverService.updateDriverProfile(eq(driverId), any(DriverRequest.class)))
                .thenReturn(ResponseEntity.ok("Profile updated successfully"));

        // Call the controller method
        ResponseEntity<String> response = driverController.updateDriverProfile(driverId, driverRequest);

        // Verify the response
        assertEquals("Profile updated successfully", response.getBody());
    }

    @Test
    void testUpdateDriver() {
        // Mock data
        long driverId = 1L;
        DriverStatus status = DriverStatus.Accepted;
        String remark = "Updated status";

        // Mock behavior
        when(driverService.updateDriverStatus(eq(driverId), eq(status), eq(remark)))
                .thenReturn(ResponseEntity.ok("Driver updated successfully"));

        // Call the controller method
        ResponseEntity<String> response = driverController.updateDriver(driverId, status, remark);

        // Verify the response
        assertEquals("Driver updated successfully", response.getBody());
    }

    @Test
    void testDeleteDriver() {
        // Mock data
        long driverId = 1L;

        // Mock behavior
        when(driverService.deleteDriver(driverId)).thenReturn(ResponseEntity.ok("Driver deleted successfully"));

        // Call the controller method
        ResponseEntity<String> response = driverController.deleteDriver(driverId);

        // Verify the response
        assertEquals("Driver deleted successfully", response.getBody());
    }
}
