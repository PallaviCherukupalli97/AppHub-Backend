package csci5308.fall21.appHub.service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.database.dao.application.IApplicationDocumentDao;
import csci5308.fall21.appHub.model.ResponseFile;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

@SpringBootTest
public class ApplicationServiceTest {
    private final static String INVALID_ID = "1";
    private final static String VALID_ID = "3";

    @Autowired
    IApplicationService applicationService;

    @MockBean
    IApplicationDocumentDao mockApplicationDocumentDao;

    @MockBean
    IUserDao userDao;


    ResponseFile responseFile;

    private User mockUserModel(){
        return new User("3", "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                "7827827822",
                "what is this qn?", "ans", "applicant", "", "");
    }

    private Application mockApplications(String id) {
        return new Application(id, "approved", "3", "MACS","Dal");
    }

    private MockMultipartFile mockEmptyFile() {

        try {
            String fileContent = "";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "empty-file",
                    "empty-file.pdf",
                    MediaType.APPLICATION_PDF_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MockMultipartFile mockMultipartFile() {

        try {
            String fileContent = "This is content in file.";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "valid-file",
                    "valid-file.pdf",
                    MediaType.APPLICATION_PDF_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MockMultipartFile mockInvalidMultipartFileType() {

        try {
            String fileContent = "This is content in file.";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "invalid-file-type",
                    "invalid-file-type.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    void applicationServiceWorks() throws IOException {
        assertNotNull(applicationService);
    }

    @Test
    void savingEmptyApplicationDocument() throws IOException {
        MultipartFile emptyFile = mockEmptyFile();

        assertEquals(HttpStatus.EXPECTATION_FAILED,
                applicationService.store("Requirement 1", "This is description", emptyFile,
                        "27efdb7d-1997-436e-a0e4-1d5fea58de2b"));
    }

    @Test
    void invalidFileTypeApplicationDocument() throws IOException {
        MultipartFile invalidFileType = mockInvalidMultipartFileType();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
                applicationService.store("Requirement 1", "This is description", invalidFileType,
                        "27efdb7d-1997-436e-a0e4-1d5fea58de2b"));
    }

    @Test
    void savingApplicationDocumentToDatabaseFailure() throws IOException, SQLException {
        MultipartFile validFile = mockMultipartFile();
        when(mockApplicationDocumentDao.addApplicationDocument(any(ApplicationDocument.class))).thenReturn(0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                applicationService.store("Requirement 1", "This is description", validFile, "1"));
    }

    @Test
    void savingApplicationDocumentToDatabase() throws IOException, SQLException {
        MultipartFile validFile = mockMultipartFile();
        when(mockApplicationDocumentDao.addApplicationDocument(any(ApplicationDocument.class))).thenReturn(1);

        assertEquals(HttpStatus.OK,
                applicationService.store("Requirement 1", "This is description", validFile, "1"));
    }

    @Test
    void savingApplicationDocumentToDatabaseException() throws IOException, SQLException {
        MultipartFile validFile = mockMultipartFile();
        when(mockApplicationDocumentDao.addApplicationDocument(any(ApplicationDocument.class)))
                .thenThrow(new SQLException());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                applicationService.store("Requirement 1", "This is description", validFile, "1"));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void getAllApplicationsWithInvalidId() throws SQLException {
        List<User> users = new ArrayList<>();
        User user = mockUserModel();
        users.add(user);
        when(userDao.getAllUsers()).thenReturn(users);
        assertEquals(null, applicationService.getApplications(INVALID_ID));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void getAllApplicationsWithValidId() throws SQLException {
        List<User> users = new ArrayList<>();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));
        User user = mockUserModel();
        users.add(user);
        when(userDao.getAllUsers()).thenReturn(users);
        when(mockApplicationDocumentDao.getApps(VALID_ID)).thenReturn(applications);
        assertEquals(applications, applicationService.getApplications(VALID_ID));
    }



}
