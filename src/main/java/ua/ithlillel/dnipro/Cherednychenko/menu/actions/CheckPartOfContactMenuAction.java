package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.repositories.ContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class CheckPartOfContactMenuAction implements ShowContactList, MenuAction {

    private ContactsRepository contactsRepository;
    private Scanner scanner;


    @Override
    public String getName() {
        return "search contact with part of the phone number or e-mail";
    }

    @Override
    public void execute()  {
        Contact.Type contactType;
        while(true) {
            System.out.println("Please Enter '1' if you would like to search the Contact with part of the phone number,\n" +
                    "Please Enter '2' if you would like to search the Contact with part of the e-mail: ");

            try {
                int input = scanner.nextInt();

                if (input==1){contactType= Contact.Type.PHONE; break;}

               else if (input==2) {contactType= Contact.Type.EMAIL; break;}

               else System.out.println("Incorrect Input!");

            } catch (Exception e) {
                System.out.println("Incorrect Input!");
            }
        }

        scanner.nextLine();
        System.out.println("Enter part of the contact: ");
        String contactPart = scanner.nextLine();
        List<Contact> contactList = contactsRepository.checkPartPhoneNumber(contactPart,contactType);
        showChosenContacts(contactList);

    }
}