package contact_book.Cherednychenko.services;

import contact_book.Cherednychenko.entities.Contact;
import database.DataBase;

import java.util.List;

public interface ContactsService {

    List<Contact> getAll();

    void add(Contact contact);

    void remove(Integer id);

    List<Contact> findByName(String beginningName);


    List<Contact> findByValue(String valueContact);

    void createContactServiceDatabase();

    DataBase getDataBase();

}
