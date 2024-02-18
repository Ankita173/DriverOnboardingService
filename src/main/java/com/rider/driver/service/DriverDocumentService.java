package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.Driver;
import com.rider.driver.entity.DriverDocument;
import com.rider.driver.entity.DriverDocumentId;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.model.DriverValidationRequest;
import com.rider.driver.model.FileResponse;
import com.rider.driver.repo.DriverDocumentRepository;
import com.rider.driver.util.DriverMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class DriverDocumentService {

    @Autowired
    DriverDocumentRepository driverDocRepo;

    @Autowired
    DriverDatabaseDAO driverRepo;

    @Autowired
    FileStorageService fileStorageService;

    @Transactional
    public ResponseEntity<FileResponse> saveDriverDocument(long driverId, String name, MultipartFile file) {
        Driver driver = driverRepo.find(driverId);
        if (Objects.isNull(driver)) {
            log.error("Driver {} not found", driverId);
            return ResponseEntity.notFound().build();
        }
        String fileName = fileStorageService.storeFile(driverId, name, file);


        DriverDocument driverDocument = DriverDocument.builder()
                .driverId(driverId)
                .docuPath(fileName)
                .docuName(name)
                .build();

        driverDocRepo.save(driverDocument);

        driver.setStatus(DriverStatus.documentCollection);
        driver.setRemark("Documentation collection");
        driverRepo.insert(driver);

        return ResponseEntity.ok().body(new FileResponse(fileName,
                file.getContentType()));
    }

    public ResponseEntity<Resource> getDriverDocument(long driverId, String fileName, HttpServletRequest request) {
        try {
            // Load file as Resource
            Optional<Resource> resourceOpt = fileStorageService.getFile(driverId, fileName);
            if (resourceOpt.isPresent()) {
                Resource resource = resourceOpt.get();

                // Try to determine file's content type
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

                // Fallback to the default content type if type could not be determined
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    public ResponseEntity<String> validateDriverDocument(long driverId, DriverValidationRequest request) {
        try {
            // find Document record
            Optional<DriverDocument> docOpt = driverDocRepo.findById(DriverDocumentId.builder()
                    .driverId(driverId)
                    .docuName(request.getDocuName()).build());
            if (docOpt.isPresent()) {
                DriverDocument driverDocument = docOpt.get();

                // Try to determine file's content type
                DriverMapper.driverValidateDocumentMapper(driverDocument, request);

                return new ResponseEntity<>("New Driver added", HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
