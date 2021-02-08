package contact_book.Cherednychenko.services;

import contact_book.Cherednychenko.dto.contacts.GetUserListResponse;
import contact_book.Cherednychenko.entities.User;

import java.util.List;

public interface UserService {
    String getToken();
    boolean isAuth();
    void register (User user);
    void login (User user);
    List<GetUserListResponse.Users> getAll();

}
