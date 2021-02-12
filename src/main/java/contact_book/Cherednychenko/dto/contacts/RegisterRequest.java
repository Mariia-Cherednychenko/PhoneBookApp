package contact_book.Cherednychenko.dto.contacts;

import lombok.Data;

@Data
public class RegisterRequest {
    private String login;
    private String password;
    private String date_born;
    //private String error;
}
