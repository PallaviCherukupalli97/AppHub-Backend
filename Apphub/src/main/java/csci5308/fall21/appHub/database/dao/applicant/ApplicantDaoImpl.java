package csci5308.fall21.appHub.database.dao.applicant;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.model.applicant.ApplicantModel;

@Repository
public class ApplicantDaoImpl extends JdbcDaoSupport implements IApplicantDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;
    final String TABLE_NAME = "applicant_profile";

    public ApplicantDaoImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    /**
     *
     * @author Pallavi Cherukupalli
     * */
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     *
     * @author Pallavi Cherukupalli
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
     * @author Pallavi Cherukupalli
     * */

    @Override
    public List<ApplicantModel> getAllApplicants() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ApplicantModel> applicants = new ArrayList<ApplicantModel>();

        while (resultSet.next()) {
            applicants.add(new ApplicantModel(resultSet.getString("user_id"), resultSet.getString("country"),
                    resultSet.getString("passport_number"), resultSet.getString("address"),
                    resultSet.getString("highest_level_education"),
                    resultSet.getString("grade"), resultSet.getString("ielts"),
                    resultSet.getString("gre"), resultSet.getString("gmat")));
        }
        return applicants;
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @Override
    public int addApplicantInfo(ApplicantModel applicant) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME
                + " (user_id, country, passport_number, address, highest_level_education, grade, ielts, gre, gmat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicant.getId(), applicant.getCountry(),
                applicant.getPassportNumber(), applicant.getAddress(), applicant.getHighestLevelEducation(),
                applicant.getGrade(), applicant.getIelts(), applicant.getGre(), applicant.getGmat());
        return preparedStatement.executeUpdate();
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @Override
    public ApplicantModel updateApplicantInfo(ApplicantModel applicant) throws SQLException {
        String query = "UPDATE " + TABLE_NAME
                + " SET country= ?, passport_number= ?, address= ?, highest_level_education=?, grade=?, ielts=?, gre=?, gmat=? WHERE user_id = ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicant.getCountry(),
                applicant.getPassportNumber(), applicant.getAddress(), applicant.getHighestLevelEducation(),
                applicant.getGrade(), applicant.getIelts(), applicant.getGre(), applicant.getGmat(), applicant.getId());
        preparedStatement.executeUpdate();
        return getApplicant(applicant.getId());
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @Override
    public ApplicantModel getApplicant(String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ApplicantModel applicant = null;
        while (resultSet.next()) {
            applicant = new ApplicantModel(resultSet.getString("user_id"), resultSet.getString("country"),
                    resultSet.getString("passport_number"), resultSet.getString("address"),
                    resultSet.getString("highest_level_education"),
                    resultSet.getString("grade"), resultSet.getString("ielts"),
                    resultSet.getString("gre"), resultSet.getString("gmat"));
        }
        return applicant;
    }

}
