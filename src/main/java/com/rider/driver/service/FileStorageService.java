package com.rider.driver.service;

import com.rider.driver.config.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Slf4j
@Service
public class FileStorageService {

    private final Path fileStorageLocation;


//    public String storeFile(long driverId, String fileName, MultipartFile file) {
//        try {
//            // Check if the file's name contains invalid characters
//            if (fileName.contains("..")) {
//                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(getFilename(driverId, fileName));
//            log.info("File getting stores in the location {}", targetLocation);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new UncheckedIOException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new UncheckedIOException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public String storeFile(long driverId, String fileName, MultipartFile file) {
        try {
            validateFileName(fileName);

            Path targetLocation = resolveTargetLocation(driverId, fileName);
            log.info("File being stored at location: {}", targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new UncheckedIOException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private void validateFileName(String fileName) throws IOException {
        if (fileName.contains("..")) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }
    }

    private Path resolveTargetLocation(long driverId, String fileName) {
        return this.fileStorageLocation.resolve(getFilename(driverId, fileName));
    }
    public Optional<Resource> getFile(long driverId, String fileName) {
        try {
            Path filePath = resolveFilePath(driverId, fileName);
            log.info("Path: {}", filePath.toAbsolutePath());

            Resource resource = new UrlResource(filePath.toUri());
            return resource.exists() ? Optional.of(resource) : Optional.empty();
        } catch (IOException ex) {
            throw new RuntimeException("File not found: " + fileName, ex);
        }
    }

    private Path resolveFilePath(long driverId, String fileName) {
        return this.fileStorageLocation.resolve(getFilename(driverId, fileName)).normalize();
    }

    private String getFilename(long driverId, String fileName) {
        return String.format("%s.%s", driverId, fileName);
    }


    //    public Resource getFile(long driverId, String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(getFilename(driverId, fileName)).normalize();
//            log.info("Path {}", filePath.toAbsolutePath());
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                log.error("File not found {}", fileName);
//                return null;
//            }
//        } catch (MalformedURLException ex) {
//            throw new UncheckedIOException("File not found " + fileName, ex);
//        }
//    }

}
