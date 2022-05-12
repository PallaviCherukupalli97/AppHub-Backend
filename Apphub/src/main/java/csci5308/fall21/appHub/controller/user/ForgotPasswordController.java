package csci5308.fall21.appHub.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import csci5308.fall21.appHub.service.user.IUserService;

@RestController
@CrossOrigin
public class ForgotPasswordController {

    @Autowired
    IUserService userService;

    static final String RESET_LINK_SUCCESS_MESSAGE = "Password reset link sent";
    static final String SUCCESS_MESSAGE = "Password updated successfully";
    static final String INVALID_CREDENTIALS_MESSAGE = "Account not registered or security criteria not met";
    static final String BAD_REQUEST_MESSAGE = "Bad Request";
    static final String SYSTEM_ERROR_MESSAGE = "System error";
    static final String INVALID_RESET_TOKEN = "Reset link invalid/expired";

    /**
     * @param email
     * @param password
     * @return
     */
    @PostMapping(path = "/api/users/forgot-password")
    @ResponseBody
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, Object> requestBodyMap) {
        Map<String, String> response = new HashMap<String, String>();
        Object emailObj = requestBodyMap.get("email");
        Object securityAnswerObj = requestBodyMap.get("securityAnswer");
        if (emailObj == null || securityAnswerObj == null) {
            response.put("message", BAD_REQUEST_MESSAGE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String email = emailObj.toString();
        String securityAnswer = securityAnswerObj.toString();
        HttpStatus status = userService.generateResetToken(email, securityAnswer);

        if (status == HttpStatus.OK) {
            response.put("message", RESET_LINK_SUCCESS_MESSAGE);
            response.put("resetPasswordToken", userService.getUser().getPasswordResetToken());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (status == HttpStatus.UNAUTHORIZED) {
            response.put("message", INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        response.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * @param email
     * @param password
     * @return
     */
    @PutMapping(path = "/api/users/reset-password/{resetPasswordToken}")
    @ResponseBody
    public ResponseEntity<Object> resetPassword(@PathVariable String resetPasswordToken,
            @RequestBody Map<String, Object> requestBodyMap) {
        Map<String, String> response = new HashMap<String, String>();
        Object emailObj = requestBodyMap.get("email").toString();
        Object newPasswordObj = requestBodyMap.get("newPassword").toString();

        if (emailObj.equals(null) || newPasswordObj.equals(null)) {
            response.put("message", BAD_REQUEST_MESSAGE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String email = emailObj.toString();
        String newPassword = newPasswordObj.toString();

        HttpStatus status = userService.resetPassword(email, newPassword, resetPasswordToken);

        if (status == HttpStatus.OK) {
            response.put("message", SUCCESS_MESSAGE);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (status == HttpStatus.UNAUTHORIZED) {
            response.put("message", INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else if (status == HttpStatus.GONE) {
            response.put("message", INVALID_RESET_TOKEN);
            return ResponseEntity.status(HttpStatus.GONE).body(response);
        }
        response.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
