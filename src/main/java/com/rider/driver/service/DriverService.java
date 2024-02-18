package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.Driver;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.repo.DriverDocumentRepository;
import com.rider.driver.repo.DriverOnboardingRepository;
import com.rider.driver.util.DriverMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DriverService {

    @Autowired
    DriverOnboardingRepository onboardingRepository;
    @Autowired
    DriverDocumentRepository driverDocumentRepository;
    @Autowired
    DriverDatabaseDAO databaseProvider;


    public ResponseEntity<List<Driver>> findAllDriver() {
        log.info("inside findAllDriver");
        List<Driver> driver = new ArrayList<>();
        try {
            driver = databaseProvider.find();
        } catch (Exception e) {
            return new ResponseEntity<>(driver, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    public ResponseEntity<Driver> findDriverById(long id) {
        log.info("inside findAllDriver");
        Driver driver = null;
        try {
            driver = databaseProvider.find(id);
        } catch (Exception e) {
            return new ResponseEntity<>(driver, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateDriverStatus(long driverId, DriverStatus status, String remark) {
        log.info("inside updateDriver {}", status);
        Driver driver = databaseProvider.find(driverId);
        driver.setStatus(status);
        driver.setRemark(remark);
        driver.setTimeUpdated(LocalDateTime.now());

        try {
            databaseProvider.insert(driver);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Driver status updated", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateDriverProfile(long driverId, DriverRequest driverReq) {
        log.info("inside updateDriver {}", driverReq);
        try {
            Driver driver = databaseProvider.find(driverId);
            DriverMapper.driverRequestMapper(driver, driverReq);
            driver.setStatus(DriverStatus.Accepted);
            driver.setRemark("Profile Updated");
            driver.setTimeUpdated(LocalDateTime.now());

            databaseProvider.insert(driver);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Driver updated", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteDriver(long id) {
        log.info("deleting deleteDriver {}", id);
        try {
            databaseProvider.delete(id);
            driverDocumentRepository.deleteByDriverId(id);
            onboardingRepository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Driver deleted", HttpStatus.OK);
    }
}
