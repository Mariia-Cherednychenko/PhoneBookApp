package contact_book.Cherednychenko.menu_action;

import database.DataBaseConnection;

public interface MenuAction {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    String getName();
    void execute();
    default boolean closeAfter(){
        return false;
    }
    String getToken();
    boolean isVisible();

    default DataBaseConnection getDataBaseConnection() {
       return dataBaseConnection;
    }



}
