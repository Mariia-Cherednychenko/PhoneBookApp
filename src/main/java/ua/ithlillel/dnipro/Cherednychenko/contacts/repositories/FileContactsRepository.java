package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileContactsRepository implements ContactsRepository {

    private List<Contact> contactsList = new LinkedList<>();
    File file;

   public FileContactsRepository(File file) {
        this.file = file;
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contactListFromFile = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    Pattern contactPattern=Pattern.compile("^(\\w+)\\[\\w+:\\s(\\+(?:\\d+\\s*)+)](?:\\w+)\\[\\w+:\\s((?:(?:\\w+)@(?:\\w+).(?:[A-Za-z]+)))]$");
                    Matcher contactMatcher = contactPattern.matcher(line+reader.readLine());
                    if(contactMatcher.matches()){
                        contactListFromFile.add(new Contact(contactMatcher.group(1),contactMatcher.group(2),contactMatcher.group(3) ));

                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return contactListFromFile;
    }

    @Override
    public void remove(int index) {
        List<Contact> contactListFromFile = getAll();
        contactListFromFile.remove(index);
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Contact contact : contactListFromFile) {
            add(contact);
        }
    }



    @Override
    public boolean add(Contact contact) {

        if (!checkPhoneValidation(contact.getContact(Contact.Type.PHONE))) {
            return false;
        } else {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(contact.toString());
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }




}

