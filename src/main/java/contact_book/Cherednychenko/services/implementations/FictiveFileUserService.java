package contact_book.Cherednychenko.services.implementations;

import contact_book.Cherednychenko.annotations.CreateIfMode;
import contact_book.Cherednychenko.dto.contacts.GetUserListResponse;
import contact_book.Cherednychenko.entities.User;
import contact_book.Cherednychenko.services.UserService;

import java.util.Collections;
import java.util.List;

@CreateIfMode({"memory", "file"})
public class FictiveFileUserService implements UserService {
    @Override
    public String getToken() {
        return null;
    }

    @Override
    public boolean isAuth() {
        return true; // always  authorized
    }

    @Override
    public void register(User user) {
        throw new UnsupportedOperationException("Не поддерживается регистрация / register not supported.");
    }

    @Override
    public void login(User user) {
        throw new UnsupportedOperationException("Не поддерживается вход / login not supported.");
    }

    @Override
    public List<GetUserListResponse.Users> getAll() {
        return Collections.emptyList();
    }
}
