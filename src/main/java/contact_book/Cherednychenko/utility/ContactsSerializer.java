package contact_book.Cherednychenko.utility;

import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.ParseContactException;

public interface ContactsSerializer {
    String serialize (Contact s);
    Contact deserialize  (String s) throws ParseContactException;
}
