package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.services.implementations.*;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public interface ServiceFactory {
    ApiContactService createApiContactService(HttpClient httpClient, ObjectMapper mapper, String pathUri) throws FailedToCreateServiceException;
    FileContactsService createFileContactService(ContactsSerializer CONTACTSSERIALIZER, String FILEPATH) throws FailedToCreateServiceException;
    InMemoryContactsService createInMemoryContactService() throws FailedToCreateServiceException;
    ApiUserService createApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) throws FailedToCreateServiceException;

}
