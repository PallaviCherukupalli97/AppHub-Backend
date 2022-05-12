package csci5308.fall21.appHub.controller.application;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import csci5308.fall21.appHub.model.ResponseFile;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;
import csci5308.fall21.appHub.service.application.IApplicationService;

@Controller
@CrossOrigin
public class ApplicationController {

    @Autowired
    private IApplicationService applicationDocumentService;

    @PostMapping("/api/application/{applicationId}/document")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title, @RequestParam("description") String description,
            @PathVariable String applicationId) {

        Map<String, String> response = new HashMap<String, String>();

        try {
            HttpStatus httpStatus = applicationDocumentService.store(title, description, file, applicationId);

            if (httpStatus == HttpStatus.EXPECTATION_FAILED) {
                response.put("message", "File missing");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
            } else if (httpStatus == HttpStatus.UNPROCESSABLE_ENTITY) {
                response.put("message", "Document types supported: PDF, JPG/JPEG, PNG");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
            }
            response.put("message", "File uploaded: " + file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.put("message", "Could not upload file: " + file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/api/application/{applicationId}/documents")
    public ResponseEntity<Object> getListFiles(@PathVariable String applicationId) {
        List<ResponseFile> files = null;
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            files = applicationDocumentService.getAllDocuments(applicationId).map(dbFile -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/application/" + applicationId + "/documents/")
                        .path(dbFile.getId())
                        .toUriString();

                return new ResponseFile(
                        dbFile.getDocumentName(),
                        fileDownloadUri,
                        dbFile.getType(),
                        dbFile.getData().length);
            }).collect(Collectors.toList());

            response.put("message", "Files fetched");
            response.put("files", files);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.put("message", "Could not fetch files");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/api/application/{applicationId}/documents/{id}")
    public ResponseEntity<byte[]> getDocument(@PathVariable String applicationId, @PathVariable String id) throws NullPointerException{
        ApplicationDocument applicationDocument = null;
        try {
            applicationDocument = applicationDocumentService.getDocument(applicationId, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        applicationDocument.getDocumentName() + "\"")
                .body(applicationDocument.getData());

    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @GetMapping(path = "/api/applications")
    public ResponseEntity<Object> getApplications(@RequestParam String applicantId) throws SQLException {
        List<Application> allApplications = applicationDocumentService.getApplications(applicantId);
        Map<String, String> response = new HashMap<>();
        if (allApplications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Applications not found");
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(allApplications);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }
}
