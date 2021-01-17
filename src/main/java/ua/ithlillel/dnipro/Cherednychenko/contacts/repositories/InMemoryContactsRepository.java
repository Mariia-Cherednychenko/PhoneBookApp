package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.ContactsRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


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

        if (!checkPhoneValidation(contact.getPhone())){
            return false;
        }
        else {
            contactsList.add(contact);
            return true;
        }
    }

  /*  @Override
    public String checkPartPhoneNumber(String partPhoneNumber) {
        List<Contact> contactsListPartPhone = new LinkedList<>();

        String contactsFound = contactsList
                .stream()
                .map(contact -> contact.getPhone().replaceAll("\\s",""))
                .filter(contact->contact.contains(partPhoneNumber))
                .collect(Collectors.joining(" \n"));
        return contactsFound;

        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getPhone().replaceAll("\\s", "").contains(partPhoneNumber)) {
                contactsListPartPhone.add(contactsList.get(i));
            }
        }
        return contactsListPartPhone;

    }

    @Override
    public String checkBeginningName(String beginningName) throws IOException {

        List<Contact> contactsListPartPhone = new LinkedList<>();

        String contactsFound = contactsListPartPhone
                .stream()
                .map(contact -> contact.getName().toLowerCase())
                .filter(contact->contact.startsWith(beginningName))
                .collect(Collectors.joining(" \n"));
        return contactsFound;

        List<Contact> contactsListBeginningName = new ArrayList<>();
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getName().toLowerCase().startsWith(beginningName.toLowerCase())){
                contactsListBeginningName.add(contactsList.get(i));
            }
        }
        return contactsListBeginningName;
    }
*/
}
