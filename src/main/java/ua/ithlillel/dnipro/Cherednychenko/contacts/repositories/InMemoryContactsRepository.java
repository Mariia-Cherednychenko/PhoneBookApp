package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;

import java.util.LinkedList;
import java.util.List;


public class InMemoryContactsRepository implements ContactsRepository {

    private List<Contact> contactsList = new LinkedList<>();

    @Override
    public List<Contact> getAll() {
        return contactsList;
    }

    @Override
    public void remove(int index) {
        contactsList.remove(index);
    }

    @Override
    public boolean add(Contact contact) {

        if (!checkPhoneValidation(contact.getContact(Contact.Type.PHONE))){
            return false;
        }
        else {
            contactsList.add(contact);
            return true;
        }
    }


}
