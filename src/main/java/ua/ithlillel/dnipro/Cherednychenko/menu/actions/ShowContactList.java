package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import ua.ithlillel.dnipro.Cherednychenko.contacts.Contact;

import java.util.List;

interface ShowContactList {

       default void showChosenContacts( List<Contact> contactList) {
        System.out.println("====================================");
        for (int i = 0; i < contactList.size(); i++) {
            /*System.out.printf("%s - %7s, %20s\n", i, contactList.get(i).getName(),
                    contactList.get(i).getPhone());*/
            System.out.printf("%d %s",(i+1), contactList.get(i));
        }
        System.out.println("====================================");

    }
}
