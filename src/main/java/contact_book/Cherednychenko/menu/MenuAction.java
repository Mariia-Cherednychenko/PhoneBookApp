package contact_book.Cherednychenko.menu;

public interface MenuAction {

    String getName();
    void execute();
    default boolean closeAfter(){
        return false;
    }
    String getToken();

    boolean isVisible();
}
