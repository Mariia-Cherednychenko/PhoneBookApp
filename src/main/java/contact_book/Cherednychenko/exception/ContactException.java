package contact_book.Cherednychenko.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
abstract class ContactException extends Exception {
    private String message;

    public void getMessage(String messageInput) {
        System.out.println(String.format("EXCEPTION! %s\n%s\n%s\n%s\n%s\n%s",
                "======================",
                this.message,
                messageInput,
                super.getMessage(),
                super.getStackTrace(),
                "======================"));

    }
}

