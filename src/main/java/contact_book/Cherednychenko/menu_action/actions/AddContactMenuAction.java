package contact_book.Cherednychenko.menu_action.actions;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.exception.IncorrectInputContactException;
import contact_book.Cherednychenko.menu_action.MenuAction;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Scanner;

@AllArgsConstructor
@Data
public class AddContactMenuAction implements MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private ContactsService contactsService;
    private Scanner scanner;


    @Override
    public String getName() {
        return "добавить контакт / add contact";
    }

    @Override
    public void execute() {
        System.out.print("Введите имя контакта / Enter contact name: ");
        String name = scanner.nextLine();

        while (true) {
            System.out.print("Введите значение контакта / Enter contact value: ");
            String value = scanner.nextLine();

            System.out.print("Введите тип контакта (EMAIL/PHONE)/ Enter contact type (EMAIL/PHONE): ");
            String type = scanner.nextLine();

            try {
                Contact contact = new Contact();
                contact.setName(name);
                contact.setValue(value);
                contact.setType(Contact.contactType.valueOf(type.toUpperCase()));
                contactsService.add(contact);

                contactsService.getDataBase().addToDataBase(contact,
                        contactsService.getDataBase().getConnectionToDataBase());

                break;
            } catch (Exception e) {
                new IncorrectInputContactException()
                        .getMessage("Неправильный формат ввода / Incorrect input");
                continue;
            }

        }

    }

    @Override
    public String getToken() {
        return userService.getToken();
    }

    @Override
    public boolean isVisible() {
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? false : true);
        } else {
            return true;
        }
    }
}
