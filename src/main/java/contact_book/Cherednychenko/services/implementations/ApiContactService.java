package contact_book.Cherednychenko.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.annotations.CreateIfMode;
import contact_book.Cherednychenko.dto.contacts.AddContactRequest;
import contact_book.Cherednychenko.dto.contacts.FindContactPerNameRequest;
import contact_book.Cherednychenko.dto.users.StatusResponse;
import contact_book.Cherednychenko.dto.contacts.GetContactListResponse;
import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.FailedAddContactException;
import contact_book.Cherednychenko.exception.FailedGetContactException;
import contact_book.Cherednychenko.exception.FailedRemoveContactException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
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
    private final UserService userService;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final String pathUri;


    @Override
    public List<Contact> getAll() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(pathUri + "/contacts"))
                .GET()
                .header("Authorization", "Bearer " + userService.getToken())
                .build();

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

        try {
            HttpRequest httpRequest = createAuthorizedPostRequest("/contacts/add", addContactRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            StatusResponse statusResponse = mapper.readValue(response.body(), StatusResponse.class);
            if ("error".equals(statusResponse.getStatus())) {
                new FailedAddContactException().getMessage(statusResponse.getError());
            }
        } catch (Exception e) {
            new FailedAddContactException().getMessage();
        }

    }

    private HttpRequest createAuthorizedPostRequest
            (String path, Object addContactRequest) throws JsonProcessingException {
        return HttpRequest.newBuilder()
                .uri(URI.create(pathUri + path))
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(addContactRequest)))
                .header("Authorization", "Bearer " + userService.getToken())
                .header("Content-type", "application/json")
                .build();
    }

   /* public boolean addContact(String token, String link, Contact contact) {

        try {
            String AddContactRequest = objectMapper.writeValueAsString(contact);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(link))
                    .POST(HttpRequest.BodyPublishers.ofString(AddContactRequest))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.statusCode());
            //System.out.println(response.body());

            return true;

        } catch (Exception e) {
            new ServerException("Ошибка при добавлении контакта").getMessage();
        }
        return false;

    }*/

    @Override
    public void remove(Integer id) {

        new FailedRemoveContactException()
                .getMessage("Невозможно удалить контакт из онлайн сервера / " +
                        "It is impossible to remove the contact form the online server");

    }

    @Override
    public List<Contact> findByName(String beginningName) {
        List<Contact> contactList = new LinkedList<>();

        FindContactPerNameRequest findContactRequest = new FindContactPerNameRequest();
        findContactRequest.setNameBeginning(beginningName);

        try {
            HttpRequest httpRequest = createAuthorizedPostRequest("/contacts/find", findContactRequest);
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

        try {
            HttpRequest httpRequest = createAuthorizedPostRequest("/contacts/find", findContactRequest);
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
}
