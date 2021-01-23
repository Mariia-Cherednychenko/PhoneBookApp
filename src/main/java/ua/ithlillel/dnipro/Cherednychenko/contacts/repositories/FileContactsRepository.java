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

        /*File tmp=null;
        try {
           tmp = File.createTempFile("tmp", "");

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
        }
        catch (IOException e){
            e.printStackTrace();
        }

        file.renameTo(new File("OldPhoneBook.txt"));
        tmp.renameTo(new File("PhoneBook.txt"));
    }*/

    @Override
    public boolean add(Contact contact) {

        if (!checkPhoneValidation(contact.getPhone())) {
            return false;
        } else {

            try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
                writer.write(contact.toString());
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
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
*/
}

