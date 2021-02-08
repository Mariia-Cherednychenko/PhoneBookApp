package contact_book.Cherednychenko.dto.contacts;

import lombok.Data;

@Data
public class AddContactRequest {
    private String type;
    private  String value;
    private String name;
}
