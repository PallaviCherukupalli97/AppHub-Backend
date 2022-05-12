package csci5308.fall21.appHub.database.dao.user;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.model.user.User;
import csci5308.fall21.appHub.util.Utility;

@Repository
public class UserDao extends JdbcDaoSupport implements IUserDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;
    final String TABLE_NAME = "users";

    public UserDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private PreparedStatement createPrepareStatement(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        for (String param : params) {
            preparedStatement.setString(counter, param);
            counter++;
        }
        return preparedStatement;
    }

    @Override
    public int addUser(User user) throws SQLException, NoSuchAlgorithmException {
        String query = "INSERT INTO " + TABLE_NAME
                + " (id, first_name, last_name, email, password, phone, security_question, security_answer, role, password_reset_token, password_reset_token_expiry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = createPrepareStatement(query, user.getId(), user.getFirstName(),
                user.getLastName(), user.getEmail(), Utility.hashInput(user.getPassword()), user.getPhone(),
                user.getSecurityQuestion(), user.getSecurityAnswer(), user.getRole(), user.getPasswordResetToken(),
                user.getPasswordResetTokenExpiry());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int deleteUser(String id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, id);
        return preparedStatement.executeUpdate();
    }

    @Override
    public User getUser(String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        while (resultSet.next()) {
            user = new User(resultSet.getString("id"), resultSet.getString("first_name"),
                    resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
                    resultSet.getString("phone"), resultSet.getString("security_question"),
                    resultSet.getString("security_answer"), resultSet.getString("role"),
                    resultSet.getString("password_reset_token"), resultSet.getString("password_reset_token_expiry"));
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<User>();

        while (resultSet.next()) {
            users.add(new User(resultSet.getString("id"), resultSet.getString("first_name"),
                    resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
                    resultSet.getString("phone"), resultSet.getString("security_question"),
                    resultSet.getString("security_answer"), resultSet.getString("role"),
                    resultSet.getString("password_reset_token"), resultSet.getString("password_reset_token_expiry")));
        }
        return users;
    }

    @Override
    public User updateUser(User user) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET password= ?, phone= ?, email= ? WHERE id = ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, user.getPassword(), user.getPhone(),
                user.getEmail(), user.getId());
        preparedStatement.executeUpdate();
        return getUser(user.getId());
    }

    @Override
    public User updateResetToken(User user) throws SQLException {
        String query = "UPDATE " + TABLE_NAME
                + " SET password_reset_token= ?, password_reset_token_expiry= ? WHERE id = ?";

        String passwordResetToken = UUID.randomUUID().toString();
        Date date = new Date();
        String passwordResetTokenExpiry = Long.toString((date.getTime() / 1000) + 3600);

        PreparedStatement preparedStatement = createPrepareStatement(query, passwordResetToken,
                passwordResetTokenExpiry, user.getId());
        preparedStatement.executeUpdate();

        return getUser(user.getId());

    }

    @Override
    public User updatePassword(User user) throws SQLException {
        String query = "UPDATE " + TABLE_NAME
                + " SET password=?, password_reset_token=NULL, password_reset_token_expiry=NULL WHERE id = ?";

        PreparedStatement preparedStatement = createPrepareStatement(query, user.getPassword(), user.getId());
        preparedStatement.executeUpdate();

        return getUser(user.getId());
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public boolean userExists(String email) throws SQLException {
        List<User> users = this.getAllUsers();
        Optional<User> userExists = users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
        return userExists.isPresent();
    }

}
