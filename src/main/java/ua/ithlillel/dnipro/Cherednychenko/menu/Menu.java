package ua.ithlillel.dnipro.Cherednychenko.menu;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private MenuAction[] actions;
    private Scanner scanner;


    public Menu(Scanner scanner) {
        this.actions = new MenuAction[0];
        this.scanner = scanner;
    }

    public void addAction(MenuAction action)  {
        actions = Arrays.copyOf(actions, actions.length+1);
        actions[actions.length-1]=action;
    }

    public void run() throws IOException {
        while (true){
            showMenu();
            int choice;
           try {
               choice = getMenuIndexFromUser();
           }
           catch (Exception E){
               System.out.println("Invalid input");
               scanner.nextLine();
               continue;
           }
            if (!validateMenuIndex (choice)){
                System.out.println("Invalid input");
                continue;
            }
            actions[choice].execute();
            if (actions[choice].closeAfter()) break;
        }
    }

    private boolean validateMenuIndex(int choice) {
        return choice>=0 && choice<actions.length;
    }

    private  int getMenuIndexFromUser() throws Exception {
        System.out.print("Enter your choice:  ");
        int ch=scanner.nextInt();
        scanner.nextLine();
        return ch-1;
    }

    private void showMenu() {
        for (int i=0; i<actions.length; i++){
            System.out.printf("%2d - %s\n", i+1, actions[i].getName());
        }
    }
}

