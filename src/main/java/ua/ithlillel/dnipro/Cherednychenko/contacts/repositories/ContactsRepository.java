package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface ContactsRepository {

    List<Contact> getAll() ;
    void remove (int index) ;
    boolean add (Contact contact) ;

    default  List<Contact> checkPartPhoneNumber(String partOfContact, Contact.Type contactType){
        List<Contact> contactsFound = getAll()
                .stream()
                .filter(contact->contact.getContact(contactType).replaceAll("\\s","").contains(partOfContact))
                .collect(Collectors.toList());
        return contactsFound;
    }

    default   List<Contact> checkBeginningName(String beginningName){
        List<Contact> contactsFound = getAll()
                    .stream()
                    .filter(contact->contact.getName().toLowerCase().startsWith(beginningName))
                    .collect(Collectors.toList());
        return contactsFound;
    }


    default boolean checkPhoneValidation(String phone) {

        try {
            Pattern pattern = Pattern.compile("^((\\+3\\s?\\-?8\\s?\\-?\\(?0)|(8\\s?\\-?\\(?0)|(\\(?0))\\d{2}\\s?\\-?\\)?((\\d{2}\\s?\\-?\\d{2}\\s?\\-?\\d{3})|(\\d{3}\\s?\\-?\\d{2}\\s?\\-?\\d{2}))$");
            Matcher matcher = pattern.matcher(phone);
            return matcher.matches();
        }

        catch (Exception e){
            return  false;
        }

    }
}
