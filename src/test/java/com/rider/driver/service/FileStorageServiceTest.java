package com.rider.driver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FileStorageServiceTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    void storeFileAndRetrieve_Success() throws IOException {
        // Mock data
        long driverId = 123L;
        String fileName = "test.txt";
        String fileContent = "This is a test file content";
        MockMultipartFile mockFile = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes());

        // Store the file
        String storedFileName = fileStorageService.storeFile(driverId, fileName, mockFile);

        // Verify stored file
        Path storedFilePath = Path.of("rider/uploads").resolve(storedFileName);
        assertEquals(fileName, storedFilePath.getFileName().toString());

        // Retrieve the file
        Resource retrievedResource = fileStorageService.getFile(driverId, fileName).orElse(null);

        // Verify retrieved file
        assertNotNull(retrievedResource);
        assertTrue(retrievedResource.exists());
        assertEquals(fileContent.length(), retrievedResource.contentLength());
    }

    @Test
    void storeFile_InvalidFileName_ExceptionThrown() {
        // Mock data
        long driverId = 123L;
        String fileName = "../test.txt";
        MockMultipartFile mockFile = new MockMultipartFile("file", fileName, "text/plain", "Test Content".getBytes());

        // Verify exception is thrown for invalid file name
        assertThrows(UncheckedIOException.class, () -> fileStorageService.storeFile(driverId, fileName, mockFile));
    }

}
