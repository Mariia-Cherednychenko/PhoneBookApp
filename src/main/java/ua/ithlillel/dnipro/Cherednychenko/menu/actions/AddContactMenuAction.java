package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.repositories.ContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;

import java.io.IOException;
import java.util.Scanner;

@AllArgsConstructor
public class AddContactMenuAction implements MenuAction {

    private ContactsRepository contactsRepository;
    private Scanner scanner;

    /*public AddContactMenuAction(ContactsRepository contactsRepository, Scanner scanner) {
        this.contactsRepository = contactsRepository;
        this.scanner = scanner;
    }*/

    @Override
    public String getName() {
        return "add contact";
    }

    @Override
    public void execute()  {
        System.out.println("Enter contact name: ");
        String name = scanner.nextLine();

        while(true) {
            System.out.println("Enter contact phone: ");
            String phone = scanner.nextLine();

            System.out.println("Enter contact e-mail: ");
            String email = scanner.nextLine();

            try {
                if (!contactsRepository.add(new Contact(name, phone,email))){
                   System.out.println("Incorrect Input");
                   continue;
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }

    }

}
