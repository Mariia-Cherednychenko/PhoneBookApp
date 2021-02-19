package pattern.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;

import java.net.http.HttpClient;

public class BuildUserServiceFactory implements UserServiceFactory {


    @Override
    public UserService buildApiUserService(String pathUri, ObjectMapper mapper, HttpClient httpClient) {
        return new ApiUserService(pathUri,mapper,httpClient);
    }
}