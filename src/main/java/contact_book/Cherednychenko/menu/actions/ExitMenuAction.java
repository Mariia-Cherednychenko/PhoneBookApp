package contact_book.Cherednychenko.menu.actions;
import contact_book.Cherednychenko.menu.MenuAction;
import contact_book.Cherednychenko.services.UserService;
import lombok.Data;

@Data
public class ExitMenuAction implements MenuAction {
    private UserService userService;

    @Override
    public String getName() {
        return "выход / exit";
    }

    @Override
    public void execute() {
        System.out.println("Программа закрывается / Program is closing");

    }

    @Override
    public boolean closeAfter() {
        return true;
    }

    @Override
    public String getToken() {
        return userService.getToken();
    }

    @Override
    public boolean isVisible(){
        return  true;
    }

}
