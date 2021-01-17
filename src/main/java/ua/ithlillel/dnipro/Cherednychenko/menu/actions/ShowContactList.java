package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;
import ua.ithlillel.dnipro.Cherednychenko.contacts.ContactsRepository;

import java.io.IOException;
import java.util.List;

interface ShowContactList {

       default void showChosenContacts( List<Contact> contactList) throws IOException {
        System.out.println("====================================");
        for (int i = 0; i < contactList.size(); i++) {
            System.out.printf("%2d - %7s, %20s\n", i, contactList.get(i).getName(),
                    contactList.get(i).getPhone());
        }
        System.out.println("====================================");

    }
}
