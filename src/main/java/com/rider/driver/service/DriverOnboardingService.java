package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.DriverCredential;
import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.repo.DriverOnboardingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static com.rider.driver.util.DriverMapper.*;

@Slf4j
@Service
public class DriverOnboardingService {

    @Autowired
    DriverOnboardingRepository onboardingRepository;

    @Autowired
    DriverDatabaseDAO driverProvider;

    @Transactional
    public ResponseEntity<String> registerDriver(DriverOnboardingRequest request) {
        log.info("inside registerDriver");
        try {
            DriverCredential driverCredential = driverOnboardingMapper(request);
            onboardingRepository.save(driverCredential);
            driverProvider.insert(driverProfileMapper(driverCredential));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("New Driver added", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> loginDriver(DriverOnboardingRequest request) {
        log.info("inside loginDriver");
        try {
            DriverCredential driverCredential = onboardingRepository.getByUsername(request.getUsername());
            if (driverCredential != null &&
                    driverCredential.getPassword().equals(getEncryptedText(request.getPassword())))
                return new ResponseEntity<>("Driver login successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Login Denied", HttpStatus.UNAUTHORIZED);
    }
}
