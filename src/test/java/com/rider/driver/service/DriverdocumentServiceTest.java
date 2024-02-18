package com.rider.driver.service;

import com.rider.driver.dao.DriverDatabaseDAO;
import com.rider.driver.entity.Driver;
import com.rider.driver.entity.DriverDocument;
import com.rider.driver.model.FileResponse;
import com.rider.driver.repo.DriverDocumentRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverdocumentServiceTest {
    @Mock
    private DriverDatabaseDAO driverRepository;
    @Mock
    private DriverDocumentRepository driverDocumentRepository;
    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private DriverDocumentService driverDocumentService;

    private long driverId = 123L;
    private String name = "Test Document";
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("testFile", "testFile.txt", "text/plain", "Test File Content".getBytes());
    }

    @Test
    void saveDriverDocument_DriverNotFound_ReturnsNotFound() {
        // Mocking behavior
        when(driverRepository.find(driverId)).thenReturn(null);

        // Invoke the method
        ResponseEntity<FileResponse> responseEntity = driverDocumentService.saveDriverDocument(driverId, name, file);

        // Verify behavior
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void saveDriverDocument_Success() throws IOException {
        // Mocking behavior
        Driver driver = new Driver();
        when(driverRepository.find(driverId)).thenReturn(driver);
        when(fileStorageService.storeFile(anyLong(), anyString(), Mockito.any(MultipartFile.class))).thenReturn("testFile.txt");
        when(driverDocumentRepository.save(Mockito.any())).thenReturn(new DriverDocument());
        doNothing().when(driverRepository).insert(Mockito.any());

        // Invoke the method
        ResponseEntity<FileResponse> responseEntity = driverDocumentService.saveDriverDocument(driverId, name, file);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("testFile.txt", responseEntity.getBody().getFileName());
        assertEquals("text/plain", responseEntity.getBody().getFileType());

        verify(driverRepository).find(driverId);
        verify(fileStorageService).storeFile(driverId, name, file);
        verify(driverDocumentRepository).save(Mockito.any());
        verify(driverRepository).insert(driver);
    }


    @Test
    void getDriverDocument_ResourceFound_Success() throws IOException {
        // Mock data
        long driverId = 123L;
        String fileName = "testfile.txt";
        HttpServletRequest request = mock(HttpServletRequest.class);
        Resource resource = mock(Resource.class);
        File file = mock(File.class);
        when(resource.getFilename()).thenReturn(fileName);
        when(resource.getFile()).thenReturn(file);
        when(file.getAbsolutePath()).thenReturn(fileName);
        when(fileStorageService.getFile(driverId, fileName)).thenReturn(Optional.of(resource));
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getServletContext().getMimeType(any())).thenReturn("text/plain");

        // Invoke the method
        ResponseEntity<Resource> responseEntity = driverDocumentService.getDriverDocument(driverId, fileName, request);

        // Verify behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.parseMediaType("text/plain"), responseEntity.getHeaders().getContentType());
        assertEquals("attachment; filename=\"" + fileName + "\"", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(resource, responseEntity.getBody());

        verify(fileStorageService).getFile(driverId, fileName);
        verify(request.getServletContext()).getMimeType(any());
    }

    @Test
    void getDriverDocument_ResourceNotFound_ReturnsNotFound() throws IOException {
        // Mock data
        long driverId = 123L;
        String fileName = "testfile.txt";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(fileStorageService.getFile(driverId, fileName)).thenReturn(Optional.empty());

        // Invoke the method
        ResponseEntity<Resource> responseEntity = driverDocumentService.getDriverDocument(driverId, fileName, request);

        // Verify behavior
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(fileStorageService).getFile(driverId, fileName);
        verifyNoInteractions(request);
    }

    @Test
    void getDriverDocument_IOError_ReturnsInternalServerError() throws IOException {
        // Mock data
        long driverId = 123L;
        String fileName = "testfile.txt";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(fileStorageService.getFile(driverId, fileName)).thenThrow(new RuntimeException());

        // Invoke the method
        ResponseEntity<Resource> responseEntity = driverDocumentService.getDriverDocument(driverId, fileName, request);

        // Verify behavior
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        verify(fileStorageService).getFile(driverId, fileName);
        verifyNoInteractions(request);
    }
}
