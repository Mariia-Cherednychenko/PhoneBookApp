package contact_book.Cherednychenko.dto.contacts;

import lombok.Data;

import java.util.List;

@Data
public class GetContactListResponse {
    private String status;
    private String error;
    private List<Contacts> contacts;

    @Data
    public static class Contacts {
        private Integer id;
        private String name;
        private String value;
        private String type;
    }
}
