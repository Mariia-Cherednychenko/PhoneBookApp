package ua.ithlillel.dnipro.Cherednychenko;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.ContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.contacts.repositories.InMemoryContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.menu.Menu;
import ua.ithlillel.dnipro.Cherednychenko.menu.actions.*;

import java.io.IOException;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        ContactsRepository contactsRepository = new InMemoryContactsRepository();
        contactsRepository.add(new Contact("Vasya", "76583632624076"));
        contactsRepository.add(new Contact("Petya", "+8 093 453 46 1278087"));
        contactsRepository.add(new Contact("Sanya", "(093)4534613"));
        contactsRepository.add(new Contact("Tanya", "+3-8-095-45-34-614"));
        contactsRepository.add(new Contact("Yana", "+38 095 4534 615"));
        contactsRepository.add(new Contact("Kira", "(095)453 4616"));
        contactsRepository.add(new Contact("Valya", "+3 8096 45 34 617"));
        contactsRepository.add(new Contact("Lena", "80964534618"));
        contactsRepository.add(new Contact("Vova", "+3 8097 45 34 619"));
        contactsRepository.add(new Contact("Tara", "+38 096 425-3320"));


        Menu menu = new Menu(scanner);
        menu.addAction(new ShowContactsMenuAction(contactsRepository));
        menu.addAction(new AddContactMenuAction(contactsRepository,scanner));
        menu.addAction(new RemoveContactMenuAction(contactsRepository,scanner));
        menu.addAction(new CheckPartPhoneNumberMenuAction(contactsRepository,scanner));
        menu.addAction(new CheckBeginningNameMenuAction(contactsRepository,scanner));
        menu.addAction(new ExitMenuAction());

        menu.run();

    }
}
