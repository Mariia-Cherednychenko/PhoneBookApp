package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.ContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class CheckBeginningNameMenuAction implements ShowContactList, MenuAction {

    private ContactsRepository contactsRepository;
    private Scanner scanner;

    /*public CheckBeginningNameMenuAction(ContactsRepository contactsRepository, Scanner scanner) {
        this.contactsRepository = contactsRepository;
        this.scanner = scanner;
    }*/

    @Override
    public String getName() {
        return "search contact with beginning of the name";
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Enter part of the beginning of searching contact name: ");
        String beginningName = scanner.nextLine();
        List<Contact> contactList = contactsRepository.checkBeginningName(beginningName);
        showChosenContacts(contactList);

    }
}