package csci5308.fall21.appHub.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.user.User;

@SpringBootTest
public class UserServiceTest {
        private final static String INVALID_EMAIL = "notlorem@ipsum.com";
        private final static String INVALID_SECURITY_ANSWER = "whatAnswerIsThis";
        private final static String NEW_PASSWORD = "newPassword";

        @Autowired
        IUserService userService;

        @MockBean
        IUserDao userDao;

        User user;

        private User mockUserNoResetPasswordToken() {
                return new User(UUID.randomUUID().toString(), "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                                "7827827822",
                                "what is this qn?", "ans", "applicant", "", "");
        }

        private User mockUserWithValidResetPasswordToken() {
                Date now = new Date();
                long nowInSeconds = now.getTime() / 1000;
                long lessThanAnHourFromNow = nowInSeconds + 100;
                return new User(UUID.randomUUID().toString(), "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                                "7827827822",
                                "what is this qn?", "ans", "applicant", UUID.randomUUID().toString(),
                                lessThanAnHourFromNow + "");
        }

        private User mockUserWithExpiredResetPasswordToken() {
                Date now = new Date();
                long nowInSeconds = now.getTime() / 1000;
                long moreThanAnHourFromNow = nowInSeconds - 100;
                return new User(UUID.randomUUID().toString(), "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                                "7827827822",
                                "what is this qn?", "ans", "applicant", UUID.randomUUID().toString(),
                                moreThanAnHourFromNow + "");
        }

        @Test
        void userServiceWorks() {
                assertNotNull(userService);
        }

        @Test
        void testGenerateResetTokenHandleSqlException() throws SQLException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.getAllUsers()).thenThrow(new SQLException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.generateResetToken(user.getEmail(), user.getSecurityAnswer()));
        }

        @Test
        void testGenerateResetTokenUserDoesNotExist() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserNoResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.UNAUTHORIZED,
                                userService.generateResetToken(INVALID_EMAIL, user.getSecurityAnswer()));
        }

        @Test
        void testGenerateResetTokenInvalidSecurityAnswer() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserNoResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.UNAUTHORIZED,
                                userService.generateResetToken(user.getEmail(), INVALID_SECURITY_ANSWER));
        }

        @Test
        void testGenerateResetTokenWorks() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserNoResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                when(userDao.updateResetToken(any(User.class))).thenReturn(user);
                assertEquals(HttpStatus.OK,
                                userService.generateResetToken(user.getEmail(), user.getSecurityAnswer()));
        }

        @Test
        void testResetPasswordHandleSqlException() throws SQLException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.getAllUsers()).thenThrow(new SQLException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.resetPassword(user.getEmail(), NEW_PASSWORD,
                                                UUID.randomUUID().toString()));
        }

        @Test
        void testResetPasswordUserDoesNotExist() throws SQLException {
                List<User> users = new ArrayList<>();
                users.add(mockUserNoResetPasswordToken());

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.UNAUTHORIZED,
                                userService.resetPassword(INVALID_EMAIL, NEW_PASSWORD,
                                                UUID.randomUUID().toString()));
        }

        @Test
        void testResetPasswordResetTokenNotGenerated() throws SQLException {
                List<User> users = new ArrayList<>();
                users.add(mockUserNoResetPasswordToken());

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.GONE,
                                userService.resetPassword("lorem@ipsum.com", NEW_PASSWORD,
                                                UUID.randomUUID().toString()));
        }

        @Test
        void testResetPasswordExpiredResetToken() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserWithExpiredResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.GONE,
                                userService.resetPassword(user.getEmail(), NEW_PASSWORD,
                                                user.getPasswordResetToken()));
        }

        @Test
        void testResetPasswordInvalidResetToken() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserWithExpiredResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.GONE,
                                userService.resetPassword(user.getEmail(), NEW_PASSWORD,
                                                UUID.randomUUID().toString()));
        }

        @Test
        void testResetPasswordWorks() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserWithValidResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.OK,
                                userService.resetPassword(user.getEmail(), NEW_PASSWORD,
                                                user.getPasswordResetToken()));
        }

        @Test
        void testUserLoginHandleSqlException() throws SQLException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.getAllUsers()).thenThrow(new SQLException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.userLogin(user.getEmail(), user.getPassword()));
        }

        @Test
        void testUserLoginWorks() throws SQLException {
                List<User> users = new ArrayList<>();
                User user = mockUserNoResetPasswordToken();
                users.add(user);

                when(userDao.getAllUsers()).thenReturn(users);
                assertEquals(HttpStatus.OK,
                                userService.userLogin(user.getEmail(), user.getPassword()));
        }

        @Test
        void testUserRegistrationWorks() throws SQLException, NoSuchAlgorithmException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.userExists(user.getEmail())).thenReturn(false);
                when(userDao.addUser(user)).thenReturn(1);
                assertEquals(HttpStatus.OK,
                                userService.userRegistration(user));
        }

        @Test
        void testUserRegistrationRecordNotInserted() throws SQLException, NoSuchAlgorithmException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.userExists(user.getEmail())).thenReturn(false);
                when(userDao.addUser(user)).thenReturn(0);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.userRegistration(user));
        }

        @Test
        void testUserRegistrationHandleSqlException() throws SQLException, NoSuchAlgorithmException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.userExists(user.getEmail())).thenReturn(false);
                when(userDao.addUser(user)).thenThrow(SQLException.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.userRegistration(user));
        }

        @Test
        void testUserRegistrationHandleNoSuchAlgorithmException() throws SQLException, NoSuchAlgorithmException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.userExists(user.getEmail())).thenReturn(false);
                when(userDao.addUser(user)).thenThrow(NoSuchAlgorithmException.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                userService.userRegistration(user));
        }

        @Test
        void testUserRegistrationExistingEmail() throws SQLException, NoSuchAlgorithmException {
                User user = mockUserNoResetPasswordToken();

                when(userDao.userExists(user.getEmail())).thenReturn(true);
                assertEquals(HttpStatus.CONFLICT,
                                userService.userRegistration(user));
        }

}
