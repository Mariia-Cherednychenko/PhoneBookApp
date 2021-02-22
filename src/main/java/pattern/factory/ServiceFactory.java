package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public interface ServiceFactory {
    public ContactsService buildApiContactsService(HttpClient httpClient,
                                                   ObjectMapper mapper,
                                                   String pathUri) throws FailedToCreateServiceException;

    public ContactsService buildFileContactsService(ContactsSerializer CONTACTSSERIALIZER,
                                                    String FILEPATH) throws FailedToCreateServiceException;

    public ContactsService buildInMemoryContactsService() throws FailedToCreateServiceException;

    public UserService buildApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) throws FailedToCreateServiceException;

}
