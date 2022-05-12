package csci5308.fall21.appHub.service.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.database.dao.application.IApplicationDocumentDao;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

@Service
public class ApplicationServiceImpl implements IApplicationService {
    private final static List<String> ALLOWED_FILE_TYPES = new ArrayList<String>() {
        {
            add("image/jpg");
            add("image/jpeg");
            add("image/png");
            add("application/pdf");
        }
    };

    @Autowired
    private IApplicationDocumentDao applicationDocumentDao;

    @Autowired
    IUserDao userDao;

    @Override
    public HttpStatus store(String title, String description, MultipartFile file, String applicationId)
            throws IOException {

        if (file.getSize() == 0) {
            return HttpStatus.EXPECTATION_FAILED;
        }
        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String id = UUID.randomUUID().toString();
        ApplicationDocument applicationDocument = new ApplicationDocument(id, applicationId, title, description,
                fileName,
                file.getContentType(),
                file.getBytes());

        try {
            if (!applicationExists(applicationId)) {
                return HttpStatus.NOT_FOUND;
            }
            if (applicationDocumentDao.addApplicationDocument(applicationDocument) > 0) {
                return HttpStatus.OK;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApplicationDocument getDocument(String applicationId, String id) throws SQLException {
        return applicationDocumentDao.getApplicationDocument(applicationId, id);
    }

    @Override
    public Stream<ApplicationDocument> getAllDocuments(String applicationId) throws SQLException {
        return applicationDocumentDao.getAllApplicationDocuments(applicationId).stream();
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public boolean applicationExists(String email) throws SQLException {
        // List<User> users = userDao.getAllUsers();
        // Optional<User> applicationExists = users.stream().filter(user ->
        // user.getEmail().equalsIgnoreCase(email)).findFirst();
        // return applicationExists.isPresent();

        // WIP: Depends on Applications module
        return true;
    }

    @Override
    public List<Application> getApplications(String applicantId) throws SQLException {
            List<User> users = userDao.getAllUsers();
            Optional<User> applicantExists = users.stream().filter(appli -> appli.getId().equalsIgnoreCase(applicantId)).findFirst();
            if(applicantExists.isPresent()) {
                return applicationDocumentDao.getApps(applicantId);
            }
            return null;
    }

}