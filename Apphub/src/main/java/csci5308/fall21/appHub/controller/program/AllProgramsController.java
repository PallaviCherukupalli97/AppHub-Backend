package csci5308.fall21.appHub.controller.program;

import csci5308.fall21.appHub.model.program.ProgramModel;
import csci5308.fall21.appHub.service.program.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class AllProgramsController {

    @Autowired
    IProgramService iProgramService;
    /**
     *
     * @author Rishika Bajaj
     * */
    @GetMapping(path = "/api/programs")
    public List<ProgramModel> getPrograms() throws SQLException {
        List<ProgramModel> allPrograms = iProgramService.getAllProgram();
        return allPrograms;
    }

}
