package csci5308.fall21.appHub.service.program;

import csci5308.fall21.appHub.database.dao.program.IProgramDao;
import csci5308.fall21.appHub.model.program.ProgramModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProgramService implements IProgramService{
    @Autowired
    IProgramDao dao;

    /**
     *
     * @author Rishika Bajaj
     * */
    public List<ProgramModel> getProgram(String university_name) throws SQLException {
        return dao.getProgram(university_name);
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    public List<ProgramModel> getAllProgram() throws SQLException{
        return dao.getAllPrograms();
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    public HttpStatus programRegistration(ProgramModel program) throws NoSuchAlgorithmException {
        try {
            if(dao.addProgramInfo(program) > 0){
                return HttpStatus.OK ;
            } else {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

