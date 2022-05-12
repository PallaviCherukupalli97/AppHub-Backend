package csci5308.fall21.appHub.database.dao.program;


import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.model.program.ProgramModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgramDao extends JdbcDaoSupport implements IProgramDao{
    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;
    final String TABLE_NAME = "program";
    final String dbName = "CSCI5308_2_DEVINT";


    public ProgramDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    private PreparedStatement createPrepareStatement(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        for (String param : params) {
            preparedStatement.setString(counter, param);
            counter++;
        }
        return preparedStatement;
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Override
    public List<ProgramModel> getProgram(String university_name)throws SQLException{
        String query = "SELECT * FROM " + dbName + "." + TABLE_NAME + " WHERE university_name= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, university_name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProgramModel> program = new ArrayList<ProgramModel>();

        while (resultSet.next()) {
            program.add(new ProgramModel(resultSet.getString("program_name"), resultSet.getString("course"),
                    resultSet.getString("fees"), resultSet.getString("duration"), resultSet.getString("application_fees"),
                    resultSet.getString("deadline"), resultSet.getString("requirements"), resultSet.getString("university_name")));
        }
        return program;
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Override
    public List<ProgramModel> getAllPrograms()throws SQLException{
        String query = "SELECT * FROM " +  dbName + "." + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProgramModel> programs = new ArrayList<ProgramModel>();

        while (resultSet.next()) {
            programs.add(new ProgramModel(resultSet.getString("program_name"), resultSet.getString("course"),
                    resultSet.getString("fees"), resultSet.getString("duration"), resultSet.getString("application_fees"),
                    resultSet.getString("deadline"), resultSet.getString("requirements"), resultSet.getString("university_name")));
        }
        return programs;

    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Override
    public int addProgramInfo(ProgramModel program) throws SQLException, NoSuchAlgorithmException {
        String query = "INSERT INTO " + dbName + "." +TABLE_NAME
                + " (program_name,course,fees,duration,application_fees,deadline,requirements,university_name) VALUES (?, ?, ?, ?, ?, ?, ?,'DALU')";
        PreparedStatement preparedStatement = createPrepareStatement(query, program.getProgramName(), program.getCourse(), program.getFees(),program.getDuration(),program.getApplicationFees(),program.getDeadline(),program.getRequirements());
        return preparedStatement.executeUpdate();
    }



}