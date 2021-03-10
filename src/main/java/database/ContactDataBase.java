package database;

import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.ParseContactException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ContactDataBase extends DataBase {

    public ContactDataBase() {
        super();
    }

    @Override
    public <T> void addToDataBase(T t, DataBaseConnection connection)
    {
       Contact contact = (Contact) t;
        try {
            connection.preparedStatement=connection.connection.prepareStatement(
                    "INSERT INTO contacts  " +
                            "(contact_id, contact_name, contact_value, contact_type, user_id ) " +
                            "VALUES(?) (?) (?) (?) (?)");
            connection.preparedStatement.setString(1, contact.getId().toString());
            connection.preparedStatement.setString(2, contact.getName());
            connection.preparedStatement.setString(3,
                    contact.getValue());
            connection.preparedStatement.setString(4,
                    contact.getType().toString());
            connection.preparedStatement.setInt(5,
                    contact.getUserId());
            connection.preparedStatement.execute();
          //  connection.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeFromDataBase(int id, DataBaseConnection connection) {
        try {
            connection.preparedStatement=connection.connection.prepareStatement(
                    "DELETE from contacts  " +
                            " WHERE contact_id = id ");

            connection.preparedStatement.execute();
            connection.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public  List<Contact> getAllInfoFromDataBase (DataBaseConnection connection) {
        List<Contact> contactList = new LinkedList<>();
        try {

            PreparedStatement preparedStatement= connection.connection.prepareStatement(
                    "SELECT (contact_name, contact_value, contact_type, contact_id) " +
                            "FROM contacts ");

            getContacts(contactList, preparedStatement.executeQuery());


        } catch (SQLException | ParseContactException throwables) {
            throwables.printStackTrace();
        }

        return contactList;
    }

    @Override
    public  List<Contact> findByNameFromDataBase (String name, DataBaseConnection connection) {
        List<Contact> contactList = new LinkedList<>();
        try {
            ResultSet resultSet =connection.preparedStatement.executeQuery(
                    "SELECT (contact_name, contact_value, contact_type, contact_id) " +
                            "FROM contacts +" +
                            "WHERE contact_name LIKE'name%'");

            getContacts(contactList, resultSet);

            connection.connection.close();

        } catch (SQLException | ParseContactException throwables) {
            throwables.printStackTrace();
        }

        return contactList;
    }

    public  List<Contact> findByValue (String value, DataBaseConnection connection) {
        List<Contact> contactList = new LinkedList<>();
        try {
            ResultSet resultSet =connection.preparedStatement.executeQuery(
                    "SELECT (contact_name, contact_value, contact_type, contact_id) " +
                            "FROM contacts +" +
                            "WHERE contact_name LIKE'%value%'");

            getContacts(contactList, resultSet);

            connection.connection.close();

        } catch (SQLException | ParseContactException throwables) {
            throwables.printStackTrace();
        }

        return contactList;
    }

    private void getContacts(List<Contact> contactList, ResultSet resultSet) throws SQLException, ParseContactException {
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(Integer.parseInt(resultSet.getString("contact_id")));
            contact.setType(Contact.contactType.valueOf(resultSet.getString("contact_type").toUpperCase()));
            contact.setName(resultSet.getString("contact_name"));
            contact.setValue(resultSet.getString("contact_value"));

            contactList.add(contact);
        }
    }
}
