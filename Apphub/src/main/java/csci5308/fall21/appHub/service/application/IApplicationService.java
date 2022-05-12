package csci5308.fall21.appHub.service.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

public interface IApplicationService {

    public HttpStatus store(String title, String description, MultipartFile file, String applicationId)
            throws IOException;

    public ApplicationDocument getDocument(String applicationId, String id) throws SQLException;

    public Stream<ApplicationDocument> getAllDocuments(String applicationId) throws SQLException;

    public List<Application> getApplications(String applicantId) throws SQLException;

    public boolean applicationExists(String email) throws SQLException;
}
