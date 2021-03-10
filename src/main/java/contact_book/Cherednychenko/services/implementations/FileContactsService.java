package contact_book.Cherednychenko.services.implementations;

import contact_book.Cherednychenko.annotations.CreateIfMode;
import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.FailedAddContactException;
import contact_book.Cherednychenko.exception.FailedGetContactException;
import contact_book.Cherednychenko.exception.FailedRemoveContactException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import database.DataBase;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

@CreateIfMode("file")
@RequiredArgsConstructor
public class FileContactsService implements ContactsService {

    private final ContactsSerializer CONTACTSSERIALIZER;
    private final String FILEPATH;
    private List<Contact> contactList = new LinkedList<>();

    private int newId() {
        return getAll().stream()
                .map(Contact::getId)
                .max(Comparator.comparingInt(a -> a))
                .map(id -> id + 1)
                .orElse(1);
    }


    @Override
    public void add(Contact contact) {
        contact.setId(newId());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(CONTACTSSERIALIZER.serialize(contact) + "\r\n");
            writer.flush();
        } catch (IOException e) {
            new FailedAddContactException().getMessage();
        }

    }

    @Override
    public void remove(Integer id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH + 1))) {

            File fileReader = new File(FILEPATH);
            File fileWriter = new File(FILEPATH + 1);
            String line;

            while ((line = reader.readLine()) != null) {
                if (!CONTACTSSERIALIZER.deserialize(line).getId().equals(id)) {
                    writer.write(line + "\n");
                }
                if (line.isEmpty()) continue;
            }
            writer.flush();
            writer.close();
            reader.close();

            fileReader.delete();
            fileWriter.renameTo(fileReader);

        } catch (Exception e) {
            new FailedRemoveContactException().getMessage();
        }
    }



    private List<Contact> readContacts(Predicate<Contact> predicate) {
        // класс содержащий 1 метод, возвращающий - подходит или неподходит

        List<Contact> contactList = new LinkedList<>();

        try (FileInputStream fis = new FileInputStream(FILEPATH)) {
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;
                Contact contact = CONTACTSSERIALIZER.deserialize(line);
                if (predicate.test(contact)) //if(contact.getName().startsWith(name))
                    contactList.add(contact);
            }
        }
        catch (Exception e) {
         new FailedGetContactException().getMessage();
        }
        return contactList;
    }


    @Override
    public List<Contact> findByName(String nameBeginning) {
        return readContacts(c -> c.getName().startsWith(nameBeginning));
    }

    @Override
    public List<Contact> findByValue(String valueContact) {
        return readContacts(c -> c.getValue().contains(valueContact));
    }

    @Override
    public void createContactServiceDatabase() {
        throw new UnsupportedOperationException("Не поддерживается регистрация / register not supported.");
    }

    @Override
    public DataBase getDataBase() {
        throw new UnsupportedOperationException("Не поддерживается регистрация / register not supported.");
    }


    @Override
    public List<Contact> getAll() {
        return readContacts(c -> true);
    }


}

