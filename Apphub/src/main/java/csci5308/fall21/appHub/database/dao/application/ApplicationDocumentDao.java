package csci5308.fall21.appHub.database.dao.application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;
import csci5308.fall21.appHub.util.Utility;

@Repository
public class ApplicationDocumentDao extends JdbcDaoSupport implements IApplicationDocumentDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;
    final String TABLE_NAME = "application_documents";

    public ApplicationDocumentDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private PreparedStatement createPrepareStatement(String sql, Blob blobParam, String... stringParams)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        preparedStatement.setBlob(counter, blobParam);
        for (String param : stringParams) {
            counter++;
            preparedStatement.setString(counter, param);
        }
        return preparedStatement;
    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private PreparedStatement createPrepareStatement(String sql, String... stringParams)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        for (String param : stringParams) {
            preparedStatement.setString(counter, param);
            counter++;
        }
        return preparedStatement;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public int addApplicationDocument(ApplicationDocument applicationDocument) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME
                + " (data, id, application_id, description, document_name, title, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = createPrepareStatement(query,
                new SerialBlob(applicationDocument.getData()), applicationDocument.getId(),
                applicationDocument.getApplicationId(),
                applicationDocument.getDescription(),
                applicationDocument.getDocumentName(),
                applicationDocument.getTitle(),
                applicationDocument.getType());
        return preparedStatement.executeUpdate();
    }

    @Override
    public ApplicationDocument getApplicationDocument(String applicationId, String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id= ? AND application_id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, id, applicationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ApplicationDocument applicationDocument = null;
        while (resultSet.next()) {
            applicationDocument = new ApplicationDocument(resultSet.getString("id"),
                    resultSet.getString("application_id"), resultSet.getString("title"),
                    resultSet.getString("description"), resultSet.getString("document_name"),
                    resultSet.getString("type"),
                    Utility.blobToBytes(resultSet.getBlob("data")));
        }
        return applicationDocument;
    }

    @Override
    public List<ApplicationDocument> getAllApplicationDocuments(String applicationId) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE application_id= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ApplicationDocument> users = new ArrayList<ApplicationDocument>();

        while (resultSet.next()) {
            users.add(new ApplicationDocument(resultSet.getString("id"), resultSet.getString("application_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"), resultSet.getString("document_name"),
                    resultSet.getString("type"),
                    Utility.blobToBytes(resultSet.getBlob("data"))));
        }
        return users;
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @Override
    public List<Application> getApps(String applicantId) throws SQLException {
        String tableName = "application";
        String query = "SELECT * FROM " + tableName
                + " WHERE user_id= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicantId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Application> applications = new ArrayList<Application>();

        while (resultSet.next()) {
            applications.add(new Application(resultSet.getString("id"),resultSet.getString("status"),resultSet.getString("user_id"),resultSet.getString("program_name"),resultSet.getString("university_name")));
        }
        return applications;
    }

}
