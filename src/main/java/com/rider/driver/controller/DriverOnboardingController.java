package com.rider.driver.controller;

import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.service.DriverOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/driver")
public class DriverOnboardingController {

    @Autowired
    DriverOnboardingService driverOnboardingService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<String> registerDriver(@RequestBody DriverOnboardingRequest driver) {
        return driverOnboardingService.registerDriver(driver);
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<String> loginDriver(DriverOnboardingRequest driver) {
        return driverOnboardingService.loginDriver(driver);
    }
}
