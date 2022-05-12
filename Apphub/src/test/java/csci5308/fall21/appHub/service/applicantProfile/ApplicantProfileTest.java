package csci5308.fall21.appHub.service.applicantProfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import csci5308.fall21.appHub.database.dao.applicant.IApplicantDao;
import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.applicant.ApplicantModel;
import csci5308.fall21.appHub.model.user.User;
import csci5308.fall21.appHub.service.applicant.IApplicantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ApplicantProfileTest {
    private final static String INVALID_ID = "1";
    private final static String VALID_ID = "2";
    private final static String VALID_ID2 = "3";

    @Autowired
    IApplicantService applicantProfileService;

    @MockBean
    IUserDao userDao;

    @MockBean
    IApplicantDao applicantDao;

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    private ApplicantModel mockApplicantModel(String id) {
        return new ApplicantModel(id, "India", "1234", "Halifax", "12th Grade",
                "A+",
                "7.5", "340", "760");
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    private User mockUserModel(){
        return new User("3", "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                "7827827822",
                "what is this qn?", "ans", "applicant", "", "");

    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void applicantServiceWorks() {
        assertNotNull(applicantProfileService);
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void applicantDoesNotExist() throws SQLException {
        List<User> users = new ArrayList<>();
        ApplicantModel applicant = mockApplicantModel(INVALID_ID);
        User user = mockUserModel();
        users.add(user);
        when(userDao.getAllUsers()).thenReturn(users);
        assertEquals(HttpStatus.BAD_REQUEST, applicantProfileService.editProfile(applicant));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void applicantExistsAddCase() throws SQLException, NoSuchAlgorithmException {
        List<User> users = new ArrayList<>();
        List<ApplicantModel> applicants = new ArrayList<>();
        ApplicantModel applicant = mockApplicantModel(VALID_ID);
        applicants.add(applicant);
        users.add(mockUserModel());
        when(userDao.getAllUsers()).thenReturn(users);
        when(applicantDao.getAllApplicants()).thenReturn(applicants);
        when(applicantDao.addApplicantInfo(applicant)).thenReturn(Integer.valueOf(VALID_ID2));
        assertEquals(HttpStatus.OK, applicantProfileService.editProfile(mockApplicantModel(VALID_ID2)));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void applicantExistsUpdateCase() throws SQLException {
        List<User> users = new ArrayList<>();
        List<ApplicantModel> applicants = new ArrayList<>();
        ApplicantModel applicant = mockApplicantModel(VALID_ID2);
        applicants.add(applicant);
        users.add(mockUserModel());
        when(userDao.getAllUsers()).thenReturn(users);
        when(applicantDao.getAllApplicants()).thenReturn(applicants);
        when(applicantDao.updateApplicantInfo(applicant)).thenReturn(applicant);
        assertEquals(HttpStatus.OK, applicantProfileService.editProfile(mockApplicantModel(VALID_ID2)));
    }

}
