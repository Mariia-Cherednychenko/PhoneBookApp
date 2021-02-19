
package contact_book.Cherednychenko.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import pattern.factory.BuildContactServiceFactory;
import pattern.factory.BuildUserServiceFactory;
import pattern.factory.ContactsServiceFactory;
import pattern.factory.UserServiceFactory;

import java.net.http.HttpClient;

public class ServiceCreationPerMode {
    private ObjectMapper objectMapper;
    private ContactsSerializer contactsSerializer;
    private HttpClient httpClient;
    private String baseUri;
    private String baseFile;
    private UserService userService;
    private ContactsService contactsService;
    private ContactsServiceFactory buildContactServiceFactory = new BuildContactServiceFactory();
    private UserServiceFactory buildUserServiceFactory = new BuildUserServiceFactory();



    public ServiceCreationPerMode(ObjectMapper objectMapper, ContactsSerializer contactsSerializer,
                                  HttpClient httpClient, String baseUri, String baseFile) {
        this.objectMapper = objectMapper;
        this.contactsSerializer = contactsSerializer;
        this.httpClient = httpClient;
        this.baseUri = baseUri;
        this.baseFile = baseFile;
    }

    public UserService getUserService() {
        return userService;
    }

    public ContactsService getContactsService() {
        return contactsService;
    }

    public ServiceCreationPerMode invoke() {
        if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.API.toString())) {
           userService = buildUserServiceFactory.buildApiUserService(baseUri, objectMapper, httpClient);
           contactsService =buildContactServiceFactory.buildApiContactsService(httpClient, objectMapper, baseUri);
        }
        else if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString())) {
            contactsService = buildContactServiceFactory.buildFileContactsService(contactsSerializer, baseFile);
        } else if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString())) {
           contactsService =buildContactServiceFactory.buildInMemoryContactsService();
          
        } else {
            new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфигурации из файла " +
                    "/ Error loading configuration"));
        }
            return this;
        }
    }

