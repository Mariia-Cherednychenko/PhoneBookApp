package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.services.implementations.*;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public class ContactServiceFactory implements ServiceFactory{

    @Override
    public ApiContactService createApiContactService (HttpClient httpClient, ObjectMapper mapper, String pathUri) {
        return new ApiContactService(httpClient,mapper,pathUri);
    }

    @Override
    public FileContactsService createFileContactService(ContactsSerializer CONTACTSSERIALIZER, String FILEPATH) {
        return new FileContactsService(CONTACTSSERIALIZER,FILEPATH);
    }

    @Override
    public InMemoryContactsService createInMemoryContactService() {
        return new InMemoryContactsService();
    }

    @Override
    public ApiUserService createApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) throws FailedToCreateServiceException {
        throw new FailedToCreateServiceException();
    }

}
