package database;

import contact_book.Cherednychenko.entities.User;
import contact_book.Cherednychenko.exception.ParseContactException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDataBase extends DataBase {
    public UserDataBase() {
        super();
    }

    @Override
    public <T> void addToDataBase(T t, DataBaseConnection connection) {
       User user = (User) t;
        try {
            connection.preparedStatement=connection.connection.prepareStatement(
                    "INSERT INTO users  " +
                            "(user_name, user_password, user_login, user_date_born ) " +
                            "VALUES(?) (?) (?) (?)");
            connection.preparedStatement.setString(1,
                    user.getName());
            connection.preparedStatement.setString(2,
                    user.getPassword());
            connection.preparedStatement.setString(3,
                    user.getLogin());
            connection.preparedStatement.setString(4,
                    user.getDate_born());
            connection.preparedStatement.execute();
            connection.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeFromDataBase(int id, DataBaseConnection connection) {
        try {
            connection.preparedStatement=connection.connection.prepareStatement(
                    "DELETE from users  " +
                            " WHERE user_id = id ");

            connection.preparedStatement.execute();
            connection.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public List<User> getAllInfoFromDataBase (DataBaseConnection connection) {
        List<User> usersList = new LinkedList<>();
        try {
            ResultSet resultSet =connection.preparedStatement.executeQuery(
                    "SELECT (user_name, user_login, user_date_born, user_id) " +
                            "FROM users ");

            getUsers(usersList, resultSet);

            connection.connection.close();

        } catch (SQLException | ParseContactException throwables) {
            throwables.printStackTrace();
        }

        return usersList;
    }

    public int getIdByLoginAndPass(String login, String password, DataBaseConnection connection) {
        int id= Integer.parseInt(null);
        try {
            ResultSet resultSet =connection.preparedStatement.executeQuery(
                    "SELECT (user_id) " +
                            "FROM users +" +
                            "WHERE user_login = login AND user_password=password");

            id = resultSet.getInt("user_id");

            connection.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return id;
    }

    @Override
    public  List<User> findByNameFromDataBase (String name, DataBaseConnection connection) {
        List<User> usersList = new LinkedList<>();
        try {
            ResultSet resultSet =connection.preparedStatement.executeQuery(
                    "SELECT (user_name, user_login, user_date_born, user_id) " +
                            "FROM users +" +
                            "WHERE user_name LIKE'name%'");

            getUsers(usersList, resultSet);

            connection.connection.close();

        } catch (SQLException | ParseContactException throwables) {
            throwables.printStackTrace();
        }

        return usersList;
    }

    private void getUsers(List<User> usersList, ResultSet resultSet) throws SQLException, ParseContactException {
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("user_password"));
            user.setLogin(resultSet.getString("user_login"));
            user.setDate_born(resultSet.getString("user_date_born"));
            usersList.add(user);
        }
    }
}

