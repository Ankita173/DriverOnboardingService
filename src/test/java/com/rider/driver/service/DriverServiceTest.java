package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.Driver;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.repo.DriverDocumentRepository;
import com.rider.driver.repo.DriverOnboardingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverDatabaseDAO databaseProvider;
    @Mock
    DriverOnboardingRepository onboardingRepository;
    @Mock
    DriverDocumentRepository driverDocumentRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void findAllDriver_Success() {
        // Mock data
        List<Driver> drivers = new ArrayList<>();
        // Populate drivers with some data

        // Mock behavior
        when(databaseProvider.find()).thenReturn(drivers);

        // Invoke the method
        ResponseEntity<List<Driver>> responseEntity = driverService.findAllDriver();

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(drivers, responseEntity.getBody());

        verify(databaseProvider).find();
    }

    @Test
    void findAllDriver_Failure() {
        // Mock behavior
        when(databaseProvider.find()).thenThrow(new RuntimeException("Some error occurred"));

        // Invoke the method
        ResponseEntity<List<Driver>> responseEntity = driverService.findAllDriver();

        // Verify behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(new ArrayList<>(), responseEntity.getBody());

        verify(databaseProvider).find();
    }

    @Test
    void findDriverById_Success() {
        // Mock data
        long driverId = 123L;
        Driver driver = new Driver(/* Fill with required parameters */);

        // Mock behavior
        when(databaseProvider.find(driverId)).thenReturn(driver);

        // Invoke the method
        ResponseEntity<Driver> responseEntity = driverService.findDriverById(driverId);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(driver, responseEntity.getBody());

        verify(databaseProvider).find(driverId);
    }

    @Test
    void findDriverById_Failure() {
        // Mock data
        long driverId = 123L;

        // Mock behavior
        when(databaseProvider.find(driverId)).thenThrow(new RuntimeException("Some error occurred"));

        // Invoke the method
        ResponseEntity<Driver> responseEntity = driverService.findDriverById(driverId);

        // Verify behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        verify(databaseProvider).find(driverId);
    }

    @Test
    void updateDriverStatus_Success() {
        // Mock data
        long driverId = 123L;
        DriverStatus status = DriverStatus.Accepted;
        String remark = "Updated status";

        // Mock behavior
        when(databaseProvider.find(driverId)).thenReturn(new Driver());
        doNothing().when(databaseProvider).insert(any());

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.updateDriverStatus(driverId, status, remark);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Driver status updated", responseEntity.getBody());

        verify(databaseProvider).find(driverId);
        verify(databaseProvider).insert(any());
    }

    @Test
    void updateDriverStatus_Failure() {
        // Mock data
        long driverId = 123L;
        DriverStatus status = DriverStatus.Accepted;
        String remark = "Updated status";

        // Mock behavior
        when(databaseProvider.find(driverId)).thenReturn(new Driver());
        doThrow(new RuntimeException("Some error occurred")).when(databaseProvider).insert(any());

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.updateDriverStatus(driverId, status, remark);

        // Verify behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Some error occurred", responseEntity.getBody());

        verify(databaseProvider).find(driverId);
        verify(databaseProvider).insert(any());
    }

    @Test
    void updateDriverProfile_Success() {
        // Mock data
        long driverId = 123L;
        DriverRequest driverReq = driverRequest();

        // Mock behavior
        when(databaseProvider.find(driverId)).thenReturn(new Driver());
        doNothing().when(databaseProvider).insert(any());

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.updateDriverProfile(driverId, driverReq);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Driver updated", responseEntity.getBody());

        verify(databaseProvider).find(driverId);
        verify(databaseProvider).insert(any());
    }

    @Test
    void updateDriverProfile_Failure() {
        // Mock data
        long driverId = 123L;
        DriverRequest driverReq = driverRequest();

        // Mock behavior
        when(databaseProvider.find(driverId)).thenReturn(new Driver());
        doThrow(new RuntimeException("Some error occurred")).when(databaseProvider).insert(any());

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.updateDriverProfile(driverId, driverReq);

        // Verify behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Some error occurred", responseEntity.getBody());

        verify(databaseProvider).find(driverId);
        verify(databaseProvider).insert(any());
    }

    @Test
    void deleteDriver_Success() {
        // Mock data
        long driverId = 123L;

        // Mock behavior
        doNothing().when(databaseProvider).delete(driverId);
        doNothing().when(onboardingRepository).deleteById(driverId);
        doNothing().when(driverDocumentRepository).deleteByDriverId(driverId);

        // Invoke the method
        ResponseEntity<String> responseEntity = driverService.deleteDriver(driverId);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Driver deleted", responseEntity.getBody());

        verify(databaseProvider).delete(driverId);
    }

    private DriverRequest driverRequest() {
        return DriverRequest.builder()
                .address("blr")
                .zipCode(223434)
                .phoneNo(235676542)
                .firstName("dummy")
                .lastName("surnmae").build();
    }
}
