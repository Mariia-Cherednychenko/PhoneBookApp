package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.services.implementations.ApiContactService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;
import contact_book.Cherednychenko.services.implementations.FileContactsService;
import contact_book.Cherednychenko.services.implementations.InMemoryContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public class BuildServiceFactory implements ServiceFactory {

    @Override
    public ContactsService buildApiContactsService(HttpClient httpClient, ObjectMapper mapper, String pathUri) {
        return new ApiContactService(httpClient,mapper,pathUri);
    }

    @Override
    public ContactsService buildFileContactsService(ContactsSerializer CONTACTSSERIALIZER, String FILEPATH) {
        return new FileContactsService(CONTACTSSERIALIZER,FILEPATH);
    }

    @Override
    public ContactsService buildInMemoryContactsService() {
        return new InMemoryContactsService();
    }

      @Override
    public UserService buildApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) {
        return new ApiUserService(pathUri,mapper,httpClient);
    }
}
