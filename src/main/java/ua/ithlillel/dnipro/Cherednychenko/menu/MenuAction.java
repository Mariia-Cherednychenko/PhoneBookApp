package ua.ithlillel.dnipro.Cherednychenko.menu;

import java.io.IOException;

public interface MenuAction {

    String getName();
    void execute() throws IOException;
    default boolean closeAfter(){
            return false;
    }
}
