package com.rider.driver.controller;

import com.rider.driver.model.DriverValidationRequest;
import com.rider.driver.model.FileResponse;
import com.rider.driver.service.DriverDocumentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(path = "/driver/document")
public class DriverDocumentController {
    @Autowired
    DriverDocumentService driverDocumentService;

    @PostMapping("/{driverId}")
    public ResponseEntity<FileResponse> uploadFile(@PathVariable long driverId,
                                                   @RequestParam("file") MultipartFile file,
                                                   @RequestParam("filename") String name) {
        return driverDocumentService.saveDriverDocument(driverId, name, file);
    }

    @PutMapping("/{driverId}")
    public @ResponseBody ResponseEntity<String> validateFile(@PathVariable long driverId,
                                                             @RequestBody DriverValidationRequest request) {
        return driverDocumentService.validateDriverDocument(driverId, request);
    }

    @GetMapping("/{driverId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 @PathVariable long driverId,
                                                 HttpServletRequest request) {
        // Load file as Resource
        return driverDocumentService.getDriverDocument(driverId, fileName, request);
    }
}
