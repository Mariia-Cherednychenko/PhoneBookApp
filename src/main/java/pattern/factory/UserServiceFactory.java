package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.services.implementations.ApiContactService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;
import contact_book.Cherednychenko.services.implementations.FileContactsService;
import contact_book.Cherednychenko.services.implementations.InMemoryContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public class UserServiceFactory implements ServiceFactory {

    @Override
    public ApiContactService createApiContactService(HttpClient httpClient, ObjectMapper mapper, String pathUri) throws FailedToCreateServiceException {
        throw new FailedToCreateServiceException();
    }

    @Override
    public FileContactsService createFileContactService(ContactsSerializer CONTACTSSERIALIZER, String FILEPATH) throws FailedToCreateServiceException {
        throw new FailedToCreateServiceException();
    }

    @Override
    public InMemoryContactsService createInMemoryContactService() throws FailedToCreateServiceException {
        throw new FailedToCreateServiceException();
    }

    @Override
    public ApiUserService createUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) throws FailedToCreateServiceException {
        return new ApiUserService(pathUri,mapper,httpClient);
    }
}
