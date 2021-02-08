package contact_book.Cherednychenko.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailedGetContactException extends ContactException {
    private String message = "Неудалось получить контакты / Failed to get the contacts";

}

