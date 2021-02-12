package contact_book.Cherednychenko.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {
    private  String status;
    private String error;
    private String token;
}
