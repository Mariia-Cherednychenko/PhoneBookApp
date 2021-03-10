package contact_book.Cherednychenko.menu_action.actions;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.menu_action.MenuAction;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowAllContactsMenuAction implements  MenuAction {

    private ConfigurationWorkMode configurationWorkMode;
    private UserService userService;
    private ContactsService contactsService;


    @Override
    public String getName() {
        return "показать все контакты / show all contacts";
    }

    @Override
    public void execute()  {
        contactsService.getAll().stream().forEach(c-> System.out.println(c));
    }

    @Override
    public String getToken(){
        return  userService.getToken();}


    @Override
    public boolean isVisible() {
        if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
            return (userService.getToken() == null ? false : true);
        } else {
            return true;
        }
    }

}


