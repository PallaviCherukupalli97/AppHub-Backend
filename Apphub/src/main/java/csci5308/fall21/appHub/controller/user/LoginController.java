package csci5308.fall21.appHub.controller.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import csci5308.fall21.appHub.service.user.IUserService;
import csci5308.fall21.appHub.util.Utility;

@RestController
@CrossOrigin
public class LoginController {
    static final String SUCCESS_MESSAGE = "Login Successful";
    static final String INVALID_CREDENTIALS_MESSAGE = "Invalid email/password";
    static final String SYSTEM_ERROR_MESSAGE = "System error";

    @Autowired
    IUserService userService;

    /**
     * @param email
     * @param password
     * @return
     */
    @PostMapping(path = "/api/users/login")
    @ResponseBody
    public ResponseEntity<Object> userLogin(@RequestBody Map<String, Object> requestBodyMap) {
        HttpStatus status = null;
        try {
            status = userService.userLogin(requestBodyMap.get("email").toString(),
                    Utility.hashInput(requestBodyMap.get("password").toString()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Map<String, String> response = new HashMap<String, String>();

        if (status == HttpStatus.OK) {
            response.put("message", SUCCESS_MESSAGE);
            response.put("role", userService.getUser().getRole());
            response.put("userToken", userService.getUser().getId());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } else if (status == HttpStatus.UNAUTHORIZED) {
            response.put("message", INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        response.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
