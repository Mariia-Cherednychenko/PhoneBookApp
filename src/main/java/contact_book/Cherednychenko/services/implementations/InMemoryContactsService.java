package contact_book.Cherednychenko.services.implementations;

import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.services.ContactsService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class InMemoryContactsService implements ContactsService {

    List<Contact> contacts = new ArrayList<>();
//  List<Contact> contactsList = new LinkedList<>();

    private int newId() {
        if(contacts.isEmpty()) return 1;
        return contacts.stream()
                .map(Contact::getId)
                .max(Comparator.comparingInt(a -> a))
                .map(id->id+1)
                .orElse(1);
    }

    @Override
    public List<Contact> getAll() {
        return contacts;
    }

    @Override
    public void add(Contact contact) {
        contact.setId(newId());
        contacts.add(contact);
    }

    /*@Override
    public boolean add(Contact contact) {

        if (!checkPhoneValidation(contact.getContact(Contact.contactType.PHONE))) {
            return false;
        } else {
            contactsList.add(contact);
            return true;
        }
    }*/

    @Override
    public void remove(Integer id) {
        contacts = contacts.stream()
                .filter(c->!c.getId().equals(id))
                .collect(Collectors.toList());
    }

    /*@Override
    public void remove(int index) {
        contactsList.remove(index);
    }*/

    @Override
    public List<Contact> findByName(String beginningName) {
       return contacts.stream()
                .filter(c->c.getName().startsWith(beginningName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> findByValue(String valueContact) {
        return contacts.stream()
                .filter(c->c.getValue().contains(valueContact))
                .collect(Collectors.toList());
    }

}



