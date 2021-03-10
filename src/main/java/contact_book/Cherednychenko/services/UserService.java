package contact_book.Cherednychenko.services;

import contact_book.Cherednychenko.entities.User;
import database.DataBase;

import java.util.List;

public interface UserService {
    String getToken();
    boolean isAuth();
    void register (User user);
    void login (User user);
    <T>List<T> getAll();
    void createUserServiceDatabase();
    DataBase getDataBase();
    int getUserId();
}
