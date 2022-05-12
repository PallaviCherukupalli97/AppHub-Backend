package csci5308.fall21.appHub.database.dao.program;

import csci5308.fall21.appHub.model.program.ProgramModel;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IProgramDao {
    public List<ProgramModel> getProgram(String university_name)throws SQLException;

    public List<ProgramModel> getAllPrograms()throws SQLException;

    public int addProgramInfo(ProgramModel program) throws SQLException, NoSuchAlgorithmException;
}
