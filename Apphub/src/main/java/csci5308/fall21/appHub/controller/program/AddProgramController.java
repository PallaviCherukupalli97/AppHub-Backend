package csci5308.fall21.appHub.controller.program;

import csci5308.fall21.appHub.model.program.ProgramModel;
import csci5308.fall21.appHub.service.program.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AddProgramController {
    static final String SUCCESS_MESSAGE = "Program created.";
    static final String SYSTEM_ERROR_MESSAGE = "Something went wrong. Please try again later.";


    @Autowired
    IProgramService iProgramService;

    /**
     *
     * @author Rishika Bajaj
     * */
    @PostMapping(path = "/api/programs/register")
    public ResponseEntity<Object> programRegistration(@RequestBody Map<String, Object> requestBodyMap, @RequestParam String university_name)
            throws NoSuchAlgorithmException {

        String programName = requestBodyMap.get("program_name").toString();
        String course = requestBodyMap.get("course").toString();
        String fees = requestBodyMap.get("fees").toString();
        String duration = requestBodyMap.get("duration").toString();
        String applicationFees = requestBodyMap.get("application_fees").toString();
        String deadline = requestBodyMap.get("deadline").toString();
        String requirements = requestBodyMap.get("requirements").toString();


        ProgramModel program = new ProgramModel(programName, course, fees, duration, applicationFees, deadline, requirements,university_name);
        HttpStatus status = iProgramService.programRegistration(program);

        Map<String, String> response = new HashMap<String, String>();
        if (status == HttpStatus.OK) {
            response.put("message", SUCCESS_MESSAGE);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else
            response.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}


