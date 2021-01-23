package ua.ithlillel.dnipro.Cherednychenko.menu.actions;

import lombok.AllArgsConstructor;
import ua.ithlillel.dnipro.Cherednychenko.menu.MenuAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@AllArgsConstructor
public class ReadFileMenuAction implements MenuAction {

    File file;

    @Override
    public String getName() {
        return "read file";
    }

    @Override
    public void execute()  {

       try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
           String line ;
           System.out.println("=============================");
           while((line=reader.readLine())!=null){
               System.out.println(line);
           }
           System.out.println("=============================");

       }
       catch (IOException e){
           e.printStackTrace();
       }
    }
}
