package com.rider.driver.dao;

import com.rider.driver.entity.Driver;
import com.rider.driver.repo.DriverRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Component
public class DriverDatabaseDAO {
    @Autowired
    DriverRepository driverRepo;
    public void insert(Driver driver) {
        log.info("Driver is getting inserted");
        try {
            driverRepo.save(driver);
        } catch (Exception e) {
            log.error("Error occurred while creating a driver ", e);
        }
    }
    public Driver find(long id) {
        try {
            Optional<Driver> driver = driverRepo.findById(id);
            return driver.get();
        } catch (NoSuchElementException e) {
            log.warn("No driver Found ", e);
        } catch (Exception e) {
            log.error("Error occurred while looking a driver ", e);
        }
        return null;
    }

    public List<Driver> find() {
        try {
            return driverRepo.findAll();
        } catch (Exception e) {
            log.error("Error occurred while looking a driver ", e);
        }
        return new ArrayList<>();
    }
    public void delete(long id) {
        try {
            driverRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Error occurred while deleting a driver ", e);
        }
    }
}
