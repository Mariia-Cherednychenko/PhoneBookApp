/*
package contact_book.Cherednychenko.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config.ConfigurationWorkMode;
import contact_book.Cherednychenko.services.implementations.ApiContactService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;
import contact_book.Cherednychenko.services.implementations.FileContactsService;
import contact_book.Cherednychenko.services.implementations.InMemoryContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public class ServiceCreationPerMode {
    private ObjectMapper objectMapper;
    private ContactsSerializer contactsSerializer;
    private HttpClient httpClient;
    private String baseUri;
    private String baseFile;
    private UserService userService;
    private ContactsService contactsService;


    public ServiceCreationPerMode(ObjectMapper objectMapper, ContactsSerializer contactsSerializer, HttpClient httpClient, String baseUri, String baseFile, UserService userService, ContactsService contactsService) {
        this.objectMapper = objectMapper;
        this.contactsSerializer = contactsSerializer;
        this.httpClient = httpClient;
        this.baseUri = baseUri;
        this.baseFile = baseFile;
        this.userService = userService;
        this.contactsService = contactsService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ContactsService getContactsService() {
        return contactsService;
    }

    public ServiceCreationPerMode invoke() {
        if(System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.API.toString().toLowerCase())){
            userService = new ApiUserService(baseUri,objectMapper,httpClient);
            contactsService = new ApiContactService(userService,httpClient,objectMapper,baseUri);
        }
        else if(System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString().toLowerCase())){
            contactsService = new FileContactsService(contactsSerializer, baseFile);
        }
        else if(System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString().toLowerCase())){
            contactsService = new InMemoryContactsService();
        }
        return this;
    }
}*/
