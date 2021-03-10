package contact_book.Cherednychenko.menu_action.actions;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.entities.User;
import contact_book.Cherednychenko.menu_action.MenuAction;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class LoginMenuAction implements MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private Scanner scanner;


    @Override
    public String getToken(){
        return  userService.getToken();}

    @Override
    public boolean isVisible(){
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? true: false);
        } else {
            return false;
        }
    }


    @Override
    public String getName() {
        return "войти / enter";
    }

    @Override
    public void execute() {
        System.out.print("\nВведитe логин / Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Введитe пароль / Enter password: ");
        String password = scanner.nextLine();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userService.login(user);

        if (userService.getToken() == null) System.out.println("Вход не выполнен / Failed to enter");
        else {
            userService.isAuth();
            userService.getDataBase().addToDataBase(user, userService.getDataBase().getConnectionToDataBase());

            System.out.println("\nВход выполнен успешно / Successful enter");
        }
    }
}
