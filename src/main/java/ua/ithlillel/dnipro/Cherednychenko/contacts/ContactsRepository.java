package ua.ithlillel.dnipro.Cherednychenko.contacts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface ContactsRepository {

    List<Contact> getAll() throws IOException;
    void remove (int index) throws IOException;
    boolean add (Contact contact) throws IOException;

    default  List<Contact> checkPartPhoneNumber(String partPhoneNumber) throws IOException{
        List<Contact> contactsFound = getAll()
                .stream()
                .filter(contact->contact.getPhone().replaceAll("\\s","").contains(partPhoneNumber))
                .collect(Collectors.toList());
        return contactsFound;
    }

    default   List<Contact> checkBeginningName(String beginningName) throws IOException{
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
