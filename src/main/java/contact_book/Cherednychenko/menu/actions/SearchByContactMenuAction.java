package contact_book.Cherednychenko.menu.actions;

import contact_book.Cherednychenko.config.ConfigurationWorkMode;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;
import contact_book.Cherednychenko.entities.Contact;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.menu.MenuAction;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class SearchByContactMenuAction implements  MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private ContactsService contactsService;
    private Scanner scanner;


    @Override
    public String getName() {
        return "поиск по значению контакта / search contact by value";
    }

    @Override
    public void execute() {

        while (true) {
            System.out.print("Введите часть значения контакта / Enter part of the contact: ");
            String contactPart = scanner.nextLine();
            List<Contact> contactList = contactsService.findByValue(contactPart);
            contactList.stream().forEach(c-> System.out.println(c));
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