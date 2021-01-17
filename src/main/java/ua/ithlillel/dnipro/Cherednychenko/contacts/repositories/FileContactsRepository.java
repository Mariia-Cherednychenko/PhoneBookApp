package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.ContactsRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class FileContactsRepository implements ContactsRepository {

    private List<Contact> contactsList = new LinkedList<>();
    File file;

   /* public FileContactsRepository(List<Contact> contactsList, File file) {
        this.contactsList = contactsList;
        this.file = file;
    }*/

    @Override
    public List<Contact> getAll() throws IOException {
        List<Contact> contactListFromFile = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;

        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            } else {
                String[] contactLine = line.split(" ");
                contactListFromFile.add(new Contact(contactLine[0], contactLine[1]));
            }
        }
        reader.close();
        return contactListFromFile;
    }

    @Override
    public void remove(int index) throws IOException {
        File tmp = File.createTempFile("tmp", "");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmp, true));
        String line = null;

        for (int i = 0; i < index; i++) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            writer.write(line);
        }
        line = reader.readLine();

        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            writer.write(line);

        }
        reader.close();
        writer.close();

        file.renameTo(new File("OldPhoneBook.txt"));
        tmp.renameTo(new File("PhoneBook.txt"));
    }

    @Override
    public boolean add(Contact contact) throws IOException {

        if (!checkPhoneValidation(contact.getPhone())){
            return false;
        }
        else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(contact.toString() + "\n");
            writer.close();
            return true;
        }

    }

    /*@Override
    public String checkPartPhoneNumber(String partPhoneNumber) throws IOException {
        List<Contact> contactsList = getAll();
        List<Contact> contactsListPartPhone = new ArrayList<>();
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getPhone().replaceAll("\\s", "").contains(partPhoneNumber)) {
                contactsListPartPhone.add(contactsList.get(i));
            }
        }
        return contactsListPartPhone;

    }

    @Override
    public String checkBeginningName(String beginningName) throws IOException {

        String contactsFound = contactsList
                .stream()
                .map(contact -> contact.getName().toLowerCase())
                .filter(contact->contact.startsWith(beginningName))
                .collect(Collectors.joining(" \n"));
        return contactsFound;


         List<Contact> contactsList = getAll();
        List<Contact> contactsListBeginningName = new ArrayList<>();
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getName().toLowerCase().startsWith(beginningName.toLowerCase())){
                contactsListBeginningName.add(contactsList.get(i));
            }
        }
        return contactsListBeginningName;
    }
*/}

