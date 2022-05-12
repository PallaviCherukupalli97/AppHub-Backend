package csci5308.fall21.appHub.database.dao.university;

import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.model.university.UniversityModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UniversityDao extends JdbcDaoSupport implements IUniversityDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;
    final String dbName = "CSCI5308_2_DEVINT";
    final String TABLE_NAME = "university";

    public UniversityDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

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
    public List<UniversityModel> getAllUniversities() throws SQLException {
        String query = "SELECT * FROM " +  dbName + "." + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UniversityModel> universities = new ArrayList<UniversityModel>();
        while (resultSet.next()) {
            universities.add(new UniversityModel(resultSet.getString("university_name"), resultSet.getString("location")));
        }
        return universities;
    }


    /**
     *
     * @author Rishika Bajaj
     * */
    @Override
    public List<UniversityModel> getUniversity(String user_id) throws SQLException {
        String query = "SELECT * FROM " + dbName + "." + TABLE_NAME + " WHERE user_id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UniversityModel> university = new ArrayList<>();
        while (resultSet.next()) {
            university.add(new UniversityModel(resultSet.getString("university_name"), resultSet.getString("location")));
        }
        return university;

    }

}