package csci5308.fall21.appHub.service.user;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;

import csci5308.fall21.appHub.model.user.User;

public interface IUserService {

    public User getUser();

    public HttpStatus generateResetToken(String email, String securityAnswer);

    public HttpStatus resetPassword(String email, String newPassword, String resetPasswordToken);

    public HttpStatus userLogin(String email, String password);

    public HttpStatus userRegistration(User user) throws NoSuchAlgorithmException;
}
