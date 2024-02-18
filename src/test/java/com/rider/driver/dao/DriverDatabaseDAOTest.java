package com.rider.driver.dao;

import com.rider.driver.entity.Driver;
import com.rider.driver.repo.DriverRepository;
import com.rider.driver.service.DriverService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DriverDatabaseDAOTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverDatabaseDAO driverService;

    @Test
    void testInsert() {
        // Mock data
        Driver driver = new Driver(/* Populate with required data */);

        // Mock behavior
        when(driverRepository.save(any())).thenReturn(driver);

        // Call the service method
        driverService.insert(driver);

        // Verify that save method is called
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testFindById() {
        // Mock data
        long id = 1L;
        Driver driver = new Driver(/* Populate with required data */);

        // Mock behavior
        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));

        // Call the service method
        Driver result = driverService.find(id);

        // Verify the returned value
        assertNotNull(result);
        assertEquals(driver, result);
    }

    @Test
    void testFindAll() {
        // Mock data
        List<Driver> drivers = new ArrayList<>();
        // Populate drivers with some data

        // Mock behavior
        when(driverRepository.findAll()).thenReturn(drivers);

        // Call the service method
        List<Driver> result = driverService.find();

        // Verify the returned value
        assertNotNull(result);
        assertEquals(drivers.size(), result.size());
    }

    @Test
    void testDelete() {
        // Mock data
        long id = 1L;

        // Mock behavior
        doNothing().when(driverRepository).deleteById(id);

        // Call the service method
        driverService.delete(id);

        // Verify that deleteById method is called
        verify(driverRepository, times(1)).deleteById(id);
    }
}
