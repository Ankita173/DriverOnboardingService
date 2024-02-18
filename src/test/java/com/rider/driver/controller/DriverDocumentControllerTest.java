package com.rider.driver.controller;

import com.rider.driver.model.FileResponse;
import com.rider.driver.service.DriverDocumentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverDocumentControllerTest {
    @Mock
    private DriverDocumentService driverDocumentService;
    @InjectMocks
    private DriverDocumentController driverDocumentController;
    @Test
    public void uploadFileTest() throws Exception {
        // Mock data
        long driverId = 123L;
        String filename = "testfile.txt";
        MultipartFile multipartFile = new MockMultipartFile("file", "testfile.txt", "text/plain", "test data".getBytes());
        ResponseEntity<FileResponse> expectedResult = ResponseEntity.ok(Mockito.mock(FileResponse.class));

        // Mock service behavior
        when(driverDocumentService.saveDriverDocument(anyLong(), anyString(), any(MultipartFile.class)))
                .thenReturn(expectedResult);

        // Perform the test
        ResponseEntity<FileResponse> actualResult = driverDocumentController.uploadFile(driverId, multipartFile, filename);

        // Assertions
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
    }


    @Test
    public void downloadFileTest() throws IOException {
        // Mock data
        long driverId = 123L;
        String fileName = "testfile.txt";
        HttpServletRequest request = new MockHttpServletRequest();

        // Mock service behavior
        Resource mockResource = Mockito.mock(Resource.class);
        when(driverDocumentService.getDriverDocument(anyLong(), anyString(), any(HttpServletRequest.class)))
                .thenReturn(ResponseEntity.ok().body(mockResource));

        // Perform the test
        ResponseEntity<Resource> responseEntity = driverDocumentController.downloadFile(fileName, driverId, request);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add further assertions based on your service behavior and expectations
    }


}
