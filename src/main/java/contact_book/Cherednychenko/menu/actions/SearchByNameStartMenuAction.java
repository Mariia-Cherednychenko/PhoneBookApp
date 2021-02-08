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
public class SearchByNameStartMenuAction implements  MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private ContactsService contactsService;
    private Scanner scanner;


    @Override
    public boolean isVisible() {
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? false : true);
        } else {
            return true;
        }
    }

    @Override
    public String getName() {
        return "поиск по началу имени /search contact with beginning of the name";
    }

    @Override
    public void execute() {
        System.out.print("Введите начало имени / Enter part of the beginning of name: ");
        String beginningName = scanner.nextLine();

        List<Contact> contactList = contactsService.findByName(beginningName);
        contactList.stream().forEach(c-> System.out.println(c));

    }

    @Override
    public String getToken() {
        return userService.getToken();
    }
}