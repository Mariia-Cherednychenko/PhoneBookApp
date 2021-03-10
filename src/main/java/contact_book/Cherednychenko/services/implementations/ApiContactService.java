package contact_book.Cherednychenko.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.annotations.CreateIfMode;
import contact_book.Cherednychenko.dto.contacts.AddContactRequest;
import contact_book.Cherednychenko.dto.contacts.FindContactPerNameRequest;
import contact_book.Cherednychenko.dto.contacts.GetContactListResponse;
import contact_book.Cherednychenko.dto.users.StatusResponse;
import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.FailedAddContactException;
import contact_book.Cherednychenko.exception.FailedGetContactException;
import contact_book.Cherednychenko.exception.FailedRemoveContactException;
import contact_book.Cherednychenko.services.ContactsService;
import database.ContactDataBase;
import database.DataBase;
import database.DataBaseConnection;
import lombok.RequiredArgsConstructor;
import pattern.factory.HttpRequestFactory;
import pattern.request.JsonRequestFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
@CreateIfMode("api")
@RequiredArgsConstructor
public class ApiContactService implements ContactsService {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final String pathUri;
    private HttpRequestFactory httpRequestFactory;
    private ContactDataBase contactDataBase;

    @Override
    public List<Contact> getAll() {
        httpRequestFactory = new JsonRequestFactory(mapper);
        HttpRequest httpRequest = httpRequestFactory.createGetRequest(pathUri + "/contacts");

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactListResponse statusResponse = mapper.readValue(response.body(), GetContactListResponse.class);

            if ("error".equals(statusResponse.getStatus())) {
                new FailedGetContactException().getMessage(statusResponse.getError());
            }

            return statusResponse.getContacts().stream()
                    .map(c -> {
                        Contact contact = new Contact();
                        contact.setId(c.getId());
                        contact.setValue(c.getValue());
                        contact.setType(Contact.contactType.valueOf(c.getType().toUpperCase()));
                        contact.setName(c.getName());
                        return contact;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public void add(Contact contact) {
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setName(contact.getName());
        addContactRequest.setType(contact.getType().toString().toLowerCase());
        addContactRequest.setValue(contact.getValue());
        httpRequestFactory = new JsonRequestFactory(mapper);

        try {
            HttpRequest httpRequest = httpRequestFactory.createPostRequest("/contacts/add", addContactRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            StatusResponse statusResponse = mapper.readValue(response.body(), StatusResponse.class);
            if ("error".equals(statusResponse.getStatus())) {
                new FailedAddContactException().getMessage(statusResponse.getError());
            }

        } catch (Exception e) {
            new FailedAddContactException().getMessage();
        }

    }

    @Override
    public void remove(Integer id) {

        contactDataBase.removeFromDataBase(id, new DataBaseConnection());
        new FailedRemoveContactException()
                .getMessage("Невозможно удалить контакт из онлайн сервера / " +
                        "It is impossible to remove the contact form the online server");

    }

    @Override
    public List<Contact> findByName(String beginningName) {
        List<Contact> contactList = new LinkedList<>();

        FindContactPerNameRequest findContactRequest = new FindContactPerNameRequest();
        findContactRequest.setNameBeginning(beginningName);
        httpRequestFactory = new JsonRequestFactory(mapper);

        try {
            HttpRequest httpRequest = httpRequestFactory.createPostRequest("/contacts/find", findContactRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactListResponse contactResponse = mapper.readValue(response.body(), GetContactListResponse.class);
            if ("error".equals(contactResponse.getStatus())) {
                new FailedGetContactException().getMessage(contactResponse.getError());

                return contactResponse.getContacts().stream()
                        .map(c -> {
                            Contact contact = new Contact();
                            contact.setId(c.getId());
                            contact.setValue(c.getValue());
                            contact.setType(Contact.contactType.valueOf(c.getType().toUpperCase()));
                            contact.setName(c.getName());
                            return contact;
                        })
                        .filter(contact -> contact.getName().startsWith(beginningName))
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            new FailedGetContactException().getMessage();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> findByValue(String valueContact) {
        List<Contact> contactList = new LinkedList<>();

        FindContactPerNameRequest findContactRequest = new FindContactPerNameRequest();
        findContactRequest.setNameBeginning(valueContact);
        httpRequestFactory = new JsonRequestFactory(mapper);

        try {
            HttpRequest httpRequest = httpRequestFactory.createPostRequest("/contacts/find", findContactRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactListResponse contactResponse = mapper.readValue(response.body(), GetContactListResponse.class);
            if ("error".equals(contactResponse.getStatus())) {
                new FailedGetContactException().getMessage(contactResponse.getError());

                return contactResponse.getContacts().stream()
                        .map(c -> {
                            Contact contact = new Contact();
                            contact.setId(c.getId());
                            contact.setValue(c.getValue());
                            contact.setType(Contact.contactType.valueOf(c.getType().toUpperCase()));
                            contact.setName(c.getName());
                            return contact;
                        })
                        .filter(contact -> contact.getValue().contains(valueContact))
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            new FailedGetContactException().getMessage();
        }
        return Collections.emptyList();
    }

    @Override
    public void createContactServiceDatabase(){
        if (contactDataBase== null){
            contactDataBase = new ContactDataBase();
        }
    }

    @Override
    public DataBase getDataBase() {
        return contactDataBase;
    }

}
