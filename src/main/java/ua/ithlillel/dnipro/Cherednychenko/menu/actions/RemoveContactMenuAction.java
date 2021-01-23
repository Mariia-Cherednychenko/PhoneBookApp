package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.contacts.repositories.ContactsRepository;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;

import java.io.IOException;
import java.util.Scanner;

@AllArgsConstructor
public class RemoveContactMenuAction implements MenuAction {

    ContactsRepository contactRepository;
    Scanner scanner;

    /*public RemoveContactMenuAction(ContactsRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }*/

    @Override
    public String getName() {
        return "remove Contact";
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Please enter the contact No that you would like to remove.");
        int index= scanner.nextInt()-1;
        checkIndexInput(index);
        contactRepository.remove(index);
        scanner.nextLine();
        System.out.println("The contact was removed.");
    }


    public void checkIndexInput(int index) throws IOException {
        if (index<0 || index > contactRepository.getAll().size()){
            System.out.println("Incorrect input");
            execute();
        }
    }

}
