package contact_book.Cherednychenko.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailedAddContactException extends ContactException{
    private String message = "Неудалось добавить контакт  / Failed to add the contact";

}
