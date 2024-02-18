package com.rider.driver.controller;

import com.rider.driver.entity.Driver;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<Driver>> findAllDriver() {
        return driverService.findAllDriver();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Driver> findDriverById(@PathVariable(name = "id") long id) {
        return driverService.findDriverById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<String> updateDriverProfile(@PathVariable(name = "id") long id,
                                                                    @RequestBody DriverRequest driver) {
        return driverService.updateDriverProfile(id, driver);
    }

    @PutMapping("/{id}/status")
    public @ResponseBody ResponseEntity<String> updateDriver(@PathVariable(name = "id") long id,
                                                             @RequestParam(name = "status") DriverStatus status,
                                                             @RequestParam(name = "remark") String remark) {
        return driverService.updateDriverStatus(id, status, remark);
    }

    @DeleteMapping
    public @ResponseBody ResponseEntity<String> deleteDriver(@RequestParam(name = "id") long id) {
        return driverService.deleteDriver(id);
    }
}
