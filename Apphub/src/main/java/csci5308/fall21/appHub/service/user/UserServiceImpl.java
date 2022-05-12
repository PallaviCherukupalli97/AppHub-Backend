package csci5308.fall21.appHub.service.user;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.user.User;
import csci5308.fall21.appHub.util.Utility;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;
    User user;

    public User getUser() {
        return user;
    }

    /**
     * @param email
     * @param password
     * @return
     */
    public HttpStatus generateResetToken(String email, String securityAnswer) {
        try {
            List<User> users = userDao.getAllUsers();
            Optional<User> authenticatedUser = users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email)
                            && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer))
                    .findFirst();

            if (!authenticatedUser.isPresent()) {
                return HttpStatus.UNAUTHORIZED;
            }

            user = authenticatedUser.get();
            user = userDao.updateResetToken(user);
            return HttpStatus.OK;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * @param email
     * @param password
     * @return
     */
    public HttpStatus resetPassword(String email, String newPassword, String resetPasswordToken) {
        try {
            List<User> users = userDao.getAllUsers();
            Optional<User> authenticatedUser = users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .findFirst();

            if (!authenticatedUser.isPresent()) {
                return HttpStatus.UNAUTHORIZED;
            }

            user = authenticatedUser.get();
            if (user.getPasswordResetToken() == "") {
                return HttpStatus.GONE;
            }

            Date now = new Date();
            long nowInSeconds = now.getTime() / 1000;
            boolean isRequestTokenValid = (Long.parseLong(user.getPasswordResetTokenExpiry()) - nowInSeconds) > 0;
            if (!user.getPasswordResetToken().equals(resetPasswordToken) || !isRequestTokenValid) {
                return HttpStatus.GONE;
            }

            user.setPassword(Utility.hashInput(newPassword));
            user = userDao.updatePassword(user);
            return HttpStatus.OK;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * @param email
     * @param password
     * @return
     */
    public HttpStatus userLogin(String email, String password) {
        try {
            List<User> users = userDao.getAllUsers();
            Optional<User> authenticatedUser = users.stream().filter(
                    user -> user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password))
                    .findFirst();

            if (authenticatedUser.isPresent()) {
                user = authenticatedUser.get();
                return HttpStatus.OK;
            }
            return HttpStatus.UNAUTHORIZED;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     */
    public HttpStatus userRegistration(User user) {
        try {
            if (!userDao.userExists(user.getEmail())) {
                return userDao.addUser(user) > 0 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            } else {
                return HttpStatus.CONFLICT;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
