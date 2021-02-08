package contact_book.Cherednychenko.dto.users;

import lombok.Data;

@Data
public class LoginResponse {
    private  String status;
    private String error;
    private String token;
}
