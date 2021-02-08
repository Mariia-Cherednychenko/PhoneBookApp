package contact_book.Cherednychenko.menu.actions;

import contact_book.Cherednychenko.config.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.IncorrectInputContactException;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.menu.MenuAction;

import java.io.IOException;
import java.util.Scanner;

@AllArgsConstructor
public class RemoveContactMenuAction implements MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private ContactsService contactsService;
    private Scanner scanner;

    @Override
    public String getName() {
        return "удалить контакт / remove Contact";
    }

    @Override
    public void execute()  {
        System.out.print("Введите ID контакта на удаление / " +
                "Please enter contact's ID for removing: ");
        int id= scanner.nextInt()-1;
        try {
            checkIndexInput(id);
        } catch (IOException e) {
            new IncorrectInputContactException().getMessage("Неверный формат ID / Incorrect ID format");
        }
        contactsService.remove(id);
        scanner.nextLine();
        System.out.println("Контакт был удален / The contact was removed");
    }

    @Override
    public String getToken(){
        return  userService.getToken();}


    @Override
    public boolean isVisible(){
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? false : true);
        } else {
            return true;
        }
    }

    public void checkIndexInput(int index) throws IOException {
        if (index<0 || index > contactsService.getAll().size()){
            System.out.println("Неверный формат ID / Incorrect ID format");
            execute();
        }
    }

}
