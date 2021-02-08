package contact_book.Cherednychenko.utility;

import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.ParseContactException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PerfoundContactSerializer implements ContactsSerializer {

    //id[email:name|value]
    private final static String PATTERN = "(\\d+)\\[(\\w+)\\:(.+)\\|(.+)\\]";
    

    @Override
    public String serialize(Contact contact) {
        return String.format("%d[%s:%s|%s]",
                contact.getId(), contact.getType(), contact.getName(), contact.getValue());
    }
    

    @Override
    public Contact deserialize(String s) throws ParseContactException {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            String id = matcher.group(1);
            String type = matcher.group(2);
            String name = matcher.group(3);
            String value = matcher.group(4);

            Contact contact = new Contact();
            contact.setId(Integer.parseInt(id));
            contact.setType(Contact.contactType.valueOf(type.toUpperCase()));
            contact.setName(name);
            contact.setValue(value);

            return contact;
        }
        throw new ParseContactException();
    }
}
