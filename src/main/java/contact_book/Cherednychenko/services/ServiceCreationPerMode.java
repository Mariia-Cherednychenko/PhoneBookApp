
package contact_book.Cherednychenko.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import pattern.factory.ContactServiceFactory;
import pattern.factory.ServiceFactory;
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
    private ServiceFactory userServiceFactory = new UserServiceFactory();
    private ServiceFactory contactServiceFactory = new ContactServiceFactory();


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

    public ServiceCreationPerMode createContactAndUserServicesPerMode(ConfigurationWorkMode.WorkModeType workModeType)
            throws FailedToCreateServiceException

    {
        if (contactsService == null && userService == null) {
            if (workModeType.equals(ConfigurationWorkMode.WorkModeType.API)) {
                {
                    userService = userServiceFactory.createApiUserService(baseUri, objectMapper, httpClient);
                    userService.createUserServiceDatabase();
                    contactsService = contactServiceFactory.createApiContactService(httpClient, objectMapper, baseUri);
                    contactsService.createContactServiceDatabase();
                }
            }
            else if (workModeType.equals(ConfigurationWorkMode.WorkModeType.FILE)) {
                if (contactsService == null && userService == null)
                    contactsService = contactServiceFactory.createFileContactService(contactsSerializer, baseFile);
            }
            else if (workModeType.equals(ConfigurationWorkMode.WorkModeType.MEMORY)) {
                contactsService = contactServiceFactory.createInMemoryContactService();
            }
            else {
                new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфигурации из файла " +
                        "/ Error loading configuration"));
            }
        } else new FailedToCreateServiceException().getMessage();
        
        return this;
    }
}

