package contact_book.Cherednychenko.menu.actions;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.menu.MenuAction;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class ShowAllUsersMenuAction implements MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;


    @Override
    public String getToken(){
        return  userService.getToken();}


    @Override
    public boolean isVisible() {
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? false : true);
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "показать пользователей / show all users";
    }

    @Override
    public void execute() {
        userService.getAll().stream().forEach(u-> System.out.println(u));
    }
}
