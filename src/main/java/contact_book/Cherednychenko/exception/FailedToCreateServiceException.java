package contact_book.Cherednychenko.exception;

public class FailedToCreateServiceException extends ContactException {
    private String message = "Cоздание сервиса неподдерживается в данном режиме / " +
            "Service creation is not supported in this mode";
}

