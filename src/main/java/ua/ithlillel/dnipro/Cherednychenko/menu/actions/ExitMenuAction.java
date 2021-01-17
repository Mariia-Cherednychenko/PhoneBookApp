package ua.ithlillel.dnipro.Cherednychenko.menu.actions;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;



public class ExitMenuAction implements MenuAction {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute() {
        System.out.println("Good bye!");

    }

    @Override
    public boolean closeAfter() {
        return true;
    }
}
