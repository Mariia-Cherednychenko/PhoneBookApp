package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;

import java.net.http.HttpClient;

public interface ContactsServiceFactory {
    public ContactsService buildApiContactsService(HttpClient httpClient,
                                                   ObjectMapper mapper,
                                                   String pathUri);

    public ContactsService buildFileContactsService(ContactsSerializer CONTACTSSERIALIZER,
                                                    String FILEPATH);

    public ContactsService buildInMemoryContactsService();

}
