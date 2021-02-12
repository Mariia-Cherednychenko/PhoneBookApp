package contact_book.Cherednychenko.menu.actions;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.entities.User;
import contact_book.Cherednychenko.menu.MenuAction;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class RegisterMenuAction implements MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private Scanner scanner;


    @Override
    public String getToken(){
        return  userService.getToken();}


    @Override
    public boolean isVisible(){
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? true : false);
        } else {
            return false;
        }
    }


    @Override
    public String getName() {
        return "регистрация / registration";
    }

    @Override
    public void execute() {
        User user = new User();
        System.out.print("Введитe логин / Enter login: ");
        user.setLogin(scanner.nextLine());

        System.out.print("Введитe пароль / Enter password :");
        user.setPassword(scanner.nextLine());


        System.out.print("Введитe дату рождения / Enter date of birth: ");
        user.setDate_born(scanner.nextLine());

       userService.register(user);
    }
}
