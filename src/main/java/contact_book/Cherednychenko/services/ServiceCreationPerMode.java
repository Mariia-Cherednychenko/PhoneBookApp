
package contact_book.Cherednychenko.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import pattern.factory.BuildServiceFactory;
import pattern.factory.ServiceFactory;

import java.net.http.HttpClient;

public class ServiceCreationPerMode {
    private ObjectMapper objectMapper;
    private ContactsSerializer contactsSerializer;
    private HttpClient httpClient;
    private String baseUri;
    private String baseFile;
    private UserService userService;
    private ContactsService contactsService;
    private ServiceFactory buildServiceFactory = new BuildServiceFactory();


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

    public ServiceCreationPerMode invoke() throws FailedToCreateServiceException {
        if (contactsService == null && userService == null) {
            if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.API.toString())) {
                {
                    userService = buildServiceFactory.buildApiUserService(baseUri, objectMapper, httpClient);
                    contactsService = buildServiceFactory.buildApiContactsService(httpClient, objectMapper, baseUri);
                }
            } else if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString())) {
                if (contactsService == null && userService == null)
                    contactsService = buildServiceFactory.buildFileContactsService(contactsSerializer, baseFile);
            } else if (System.getProperty("app.service.workmode").equals(ConfigurationWorkMode.WorkModeType.FILE.toString())) {
                contactsService = buildServiceFactory.buildInMemoryContactsService();
            } else {
                new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфигурации из файла " +
                        "/ Error loading configuration"));
            }
        } else new FailedToCreateServiceException().getMessage();
        
        return this;
    }
}

