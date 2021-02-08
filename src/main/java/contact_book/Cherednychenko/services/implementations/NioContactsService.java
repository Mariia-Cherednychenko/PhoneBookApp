package contact_book.Cherednychenko.services.implementations;

import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.FailedAddContactException;
import contact_book.Cherednychenko.exception.FailedGetContactException;
import contact_book.Cherednychenko.exception.FailedRemoveContactException;
import contact_book.Cherednychenko.exception.ParseContactException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class NioContactsService implements ContactsService {

    private final ContactsSerializer CONTACTSSERIALIZER;
    private final String FILEPATH;
    private List<Contact> contactList = new LinkedList<>();
    SeekableByteChannel fileChannel;
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);

    private int newId() {

            return getAll().stream()
                    .map(Contact::getId)
                    .max(Comparator.comparingInt(a -> a))
                    .map(id -> id + 1)
                    .orElse(1);

    }

    public void readByLine(Consumer<String> online) {
        try (ByteChannel byteChannel = Files.newByteChannel(Path.of(FILEPATH), StandardOpenOption.READ)) {
            // allocate (для разовой операции) - открывается в обычной ОП
            // allocateDirect (удобно если буфер постоянно переиспользуется) - открівает буфер в адресном пространстве ОС,
            // работа с файлом бістрее, но создание такого буфера - дольше по времени
            String stringBuffer = "";
            while (byteChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                stringBuffer += getStringFromBuffer(byteBuffer);
                // делим на массив с разделителем
                String[] parts = stringBuffer.split("\n");
                // если 1 элемент то в массив не зайдем
                // послений не берем т.к. не знаем целая ли это строка или это неполный кусок
                for (int i = 0; i < parts.length - 1; i++) {
                    online.accept(parts[i]);
                }
                stringBuffer = parts[parts.length - 1];
                byteBuffer.clear();
            }
            online.accept(stringBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringFromBuffer(ByteBuffer byteBuffer) {
        return new String(
                byteBuffer.array(),
                byteBuffer.position(),
                byteBuffer.limit());
    }


    @Override
    public List<Contact> getAll() {
        List<Contact> contactsRead=new LinkedList<>();
        readByLine(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    contactsRead.add(CONTACTSSERIALIZER.deserialize(s));

                } catch (ParseContactException e) {
                    new FailedGetContactException().getMessage();
                }
            }
        });
        return contactsRead;
    }

    @Override
    public void add(Contact contact) {
        contact.setId(newId());
        byteBuffer.clear();
        try (FileOutputStream fileIn = new FileOutputStream(Path.of(FILEPATH).toFile(),true)) {
            byte [] contactPartsArr =(CONTACTSSERIALIZER.serialize(contact)+"\n").getBytes();
            for (int i = 0; i < contactPartsArr.length; i++) {
                byteBuffer.put(contactPartsArr[i]);
                fileChannel = fileIn.getChannel();
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (Exception e) {
            new FailedAddContactException().getMessage();
        }
    }

    /*@Override
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


    }*/


    @Override
    public void remove(Integer id) {
       // File oldFile = new File(FILEPATH);
        File tempFile = new File("temp.txt");

        readByLine(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    Contact readContact = CONTACTSSERIALIZER.deserialize(s);
                    if (!readContact.getId().equals(id)) {
                        byteBuffer.put(readContact.toString().getBytes());
                        try (FileOutputStream fileOut = new FileOutputStream(tempFile, true)) {
                            byteBuffer.flip();
                            fileChannel = fileOut.getChannel();
                            fileChannel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }
                } catch (Exception e) {
                    new FailedRemoveContactException().getMessage();
                }
            }
        });

        //oldFile.delete();
        tempFile.renameTo(Path.of(FILEPATH).toFile());
    }


    @Override
    public List<Contact> findByName(String beginningName) {
        List<Contact> contactList = new LinkedList<>();
        readByLine(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    Contact contact = CONTACTSSERIALIZER.deserialize(s);
                    if (contact.getName().startsWith(s)) {
                        contactList.add(contact);
                    }
                } catch (Exception e) {
                    new FailedGetContactException().getMessage();
                }
            }
        });
        return contactList;
    }


    @Override
    public List<Contact> findByValue(String valueContact) {
        List<Contact> contactList = new LinkedList<>();
        readByLine(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    Contact contact = CONTACTSSERIALIZER.deserialize(s);
                    if (contact.getValue().contains(s)) {
                        contactList.add(contact);
                    }
                } catch (Exception e) {
                    new FailedGetContactException().getMessage();
                }
            }
        });
        return contactList;
    }
}


