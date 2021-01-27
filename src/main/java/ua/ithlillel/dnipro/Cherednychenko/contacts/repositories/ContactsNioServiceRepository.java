package ua.ithlillel.dnipro.Cherednychenko.contacts.repositories;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContactsNioServiceRepository implements ContactsRepository {

    private List<Contact> contactsList = new LinkedList<>();
    File file;
    SeekableByteChannel fileChannel;
    ByteBuffer buffer=ByteBuffer.allocate(1000);

    public ContactsNioServiceRepository(File file) {
        this.file = file.getAbsoluteFile();
    }


    @Override
    public List<Contact> getAll() {
        List<Contact> contactListFromFile = new ArrayList<>();

        try {
            buffer.clear();

            fileChannel = Files.newByteChannel(Path.of(file.getName()));


            String line = "";
            while(true) {
                int size = fileChannel.read(buffer);
                if (size == -1) break;
                buffer.flip();
                line = line+new String(buffer.array());
                buffer.clear();


            }
            fileChannel.close();

                String[] lines = line.split("\n");
                Pattern contactPattern = Pattern.compile("^(\\w+)\\[\\w+:\\s(\\+(?:\\d+\\s*)+)](?:\\w+)\\[\\w+:\\s((?:(?:\\w+)@(?:\\w+).(?:[A-Za-z]+)))]$");
                for (int i=0; i<lines.length-1;i=i+2) {
                    String s=lines[i]+lines[i+1];
                    Matcher contactMatcher = contactPattern.matcher(s);
                    if (contactMatcher.matches()) {
                        contactListFromFile.add(new Contact(contactMatcher.group(1), contactMatcher.group(2), contactMatcher.group(3)));
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactListFromFile;
    }

    @Override
    public boolean add(Contact contact) {

        if (!checkPhoneValidation(contact.getContact(Contact.Type.PHONE))) {
            return false;
        }
        else {
            try {
               buffer.clear();
               buffer.put(contact.toString().getBytes());

                FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath(),true);
                fileChannel = fileOut.getChannel();

                 buffer.flip();
                 fileChannel.write(buffer);
                buffer.clear();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                try {
                    fileChannel.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return false;
        }


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
}

