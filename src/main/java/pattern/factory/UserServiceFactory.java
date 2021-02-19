package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.services.UserService;

import java.net.http.HttpClient;

public interface UserServiceFactory {
    public UserService buildApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient);

}
