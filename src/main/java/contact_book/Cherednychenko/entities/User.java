package contact_book.Cherednychenko.entities;

import database.ContactDataBase;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private String login;
    private String name;
    private String password;
    private String date_born;
    private ContactDataBase contactDataBase= new ContactDataBase();
}
