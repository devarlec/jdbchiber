package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection sqlConnection;

    public UserDaoJDBCImpl() {
        try {
            this.sqlConnection = Util.getMySQLConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String createUsersTableCommand = "CREATE TABLE IF NOT EXISTS " + Util.USER_TABLE_NAME + " ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(45) NOT NULL,"
                + "lastName VARCHAR(45) NOT NULL,"
                + "age INT NOT NULL,"
                + "PRIMARY KEY (id))"
                + "ENGINE = InnoDB AUTO_INCREMENT = 1";
        executeUpdateSQLCommand(createUsersTableCommand);
    }

    @Override
    public void dropUsersTable() {
        String dropUsersTableCommand = "DROP TABLE IF EXISTS " + Util.USER_TABLE_NAME;
        executeUpdateSQLCommand(dropUsersTableCommand);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserCommand = "INSERT INTO " + Util.USER_TABLE_NAME + " (name, lastName, age) VALUES (?, ?, ?)";
        executeUpdateSQLCommand(saveUserCommand, name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        String removeUserCommand = "DELETE FROM " + Util.USER_TABLE_NAME + " WHERE ID = ?";
        executeUpdateSQLCommand(removeUserCommand, id);
    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersCommand = "DELETE FROM " + Util.USER_TABLE_NAME;
        executeUpdateSQLCommand(cleanUsersCommand);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String getAllUsersCommand = "SELECT id, name, lastName, age FROM " + Util.USER_TABLE_NAME;
        try (PreparedStatement statement = sqlConnection.prepareStatement(getAllUsersCommand)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private void executeUpdateSQLCommand(String command, Object... args) {
        try (PreparedStatement statement = sqlConnection.prepareStatement(command)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
