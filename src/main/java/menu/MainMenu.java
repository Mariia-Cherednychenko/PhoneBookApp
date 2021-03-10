package menu;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.menu_action.MenuAction;
import contact_book.Cherednychenko.menu_action.actions.*;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {

    private MenuAction[] actions;
    private ConfigurationWorkMode configurationWorkMode;
    private Scanner scanner;
    private ArrayList<MenuAction> userMenu;
    private UserService userService;


    public MainMenu(ConfigurationWorkMode configurationWorkMode, UserService userService, Scanner scanner) {
        this.actions = new MenuAction[0];
        this.configurationWorkMode = configurationWorkMode;
        this.scanner = scanner;
        this.userMenu = new ArrayList<>();
        this.userService = userService;
    }


    public void addActionsOptionsToMainMenu(ConfigurationWorkMode configurationWorkMode, UserService userService, ContactsService contactsService, Scanner scanner) {
        addAction(new RegisterMenuAction(configurationWorkMode, userService, scanner));
        addAction(new LoginMenuAction(configurationWorkMode,userService, scanner));
        addAction(new ShowAllUsersMenuAction(configurationWorkMode,userService));
        addAction(new ShowAllContactsMenuAction(configurationWorkMode, userService, contactsService));
        addAction(new AddContactMenuAction(configurationWorkMode, userService, contactsService, scanner));
        addAction(new RemoveContactMenuAction(configurationWorkMode, userService, contactsService, scanner));
        addAction(new SearchByContactMenuAction(configurationWorkMode, userService, contactsService, scanner));
        addAction(new SearchByNameStartMenuAction(configurationWorkMode, userService, contactsService, scanner));
        addAction(new ExitMenuAction());
    }


    private void addAction(MenuAction action) {
        actions = Arrays.copyOf(actions, actions.length + 1);
        actions[actions.length - 1] = action;
    }

    public void run() {

        while (true) {
            showUserMenu();
            int choice;
            try {
                choice = getMenuIndexFromUser();
            } catch (Exception E) {
                System.out.println("Неверный ввод / Invalid input");
                scanner.nextLine();
                continue;
            }
            if (!validateMenuIndex(choice)) {
                System.out.println("Неверный ввод / Invalid input");
                continue;
            }
            userMenu.get(choice).execute();
            if (userMenu.get(choice).closeAfter()) break;

        }
    }

    private boolean validateMenuIndex(int choice) {
        return (choice >= 0 && choice < actions.length);
    }

    private int getMenuIndexFromUser() {
        System.out.print("\nВведите Ваш выбор / Enter your choice:  ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        return ch - 1;
    }

    private void showUserMenu() {
        int index = 0;
        userMenu.clear();
        System.out.println("---------------------");
        for (int i = 0; i < actions.length; i++) {
            if (actions[i].isVisible()) {
                userMenu.add(actions[i]);
                System.out.printf("%2d - %s\n", index + 1, userMenu.get(index).getName());
                index = index + 1;
            }
        }
        System.out.println("---------------------");

    }

    private void showStartMenuAction() {
        System.out.println();
    }


}

