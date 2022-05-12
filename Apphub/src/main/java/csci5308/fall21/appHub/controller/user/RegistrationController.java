package csci5308.fall21.appHub.controller.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import csci5308.fall21.appHub.model.user.User;
import csci5308.fall21.appHub.service.user.IUserService;

@RestController
@CrossOrigin
public class RegistrationController {
    static final String SUCCESS_MESSAGE = "User created.";
    static final String USER_ALREADY_EXISTS_MESSAGE = "Email already registered. Please try with another email.";
    static final String SYSTEM_ERROR_MESSAGE = "Something went wrong. Please try again later.";

    @Autowired
    IUserService userService;

    /**
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(path = "/api/users/register")
    public ResponseEntity<Object> userRegistration(@RequestBody Map<String, Object> requestBodyMap)
            throws NoSuchAlgorithmException {

        String id = UUID.randomUUID().toString();
        String firstName = requestBodyMap.get("firstName").toString();
        String lastName = requestBodyMap.get("lastName").toString();
        String email = requestBodyMap.get("email").toString();
        String password = requestBodyMap.get("password").toString();
        String phone = requestBodyMap.get("phone").toString();
        String securityQuestion = requestBodyMap.get("securityQuestion").toString();
        String securityAnswer = requestBodyMap.get("securityAnswer").toString();
        String role = requestBodyMap.get("role").toString();
        String passwordResetToken = requestBodyMap.get("passwordResetToken").toString();
        String passwordResetTokenExpiry = requestBodyMap.get("passwordResetTokenExpiry").toString();

        User user = new User(id, firstName, lastName, email, password, phone, securityQuestion, securityAnswer, role,
                passwordResetToken, passwordResetTokenExpiry);
        HttpStatus status = userService.userRegistration(user);

        Map<String, String> response = new HashMap<String, String>();
        if (status == HttpStatus.OK) {
            response.put("message", SUCCESS_MESSAGE);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (status == HttpStatus.CONFLICT) {
            response.put("message", USER_ALREADY_EXISTS_MESSAGE);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        response.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
