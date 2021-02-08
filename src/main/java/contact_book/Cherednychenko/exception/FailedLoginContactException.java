package contact_book.Cherednychenko.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailedLoginContactException extends ContactException{
    private String message = "Неудалось зайти / Failed to login";

}
