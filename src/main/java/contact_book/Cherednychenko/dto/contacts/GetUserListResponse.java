package contact_book.Cherednychenko.dto.contacts;

import lombok.Data;

import java.util.List;

@Data
public class GetUserListResponse {
    private String status;
    private String error;
    private List<Users> users;

    @Data
    public static class Users {
        private String login;
        private String date_born;

    }
}
