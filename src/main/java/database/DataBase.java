package database;

import lombok.Data;

import java.util.List;

@Data
public abstract class DataBase {
  DataBaseConnection connectionToDataBase;

    public DataBase() {
        if (connectionToDataBase==null) {
            connectionToDataBase = new DataBaseConnection() {
            };
        }
    }

    public abstract  <T> void addToDataBase(T t, DataBaseConnection connection);

    public abstract void removeFromDataBase(int id, DataBaseConnection connection);

    public  abstract <T> List<T> getAllInfoFromDataBase(DataBaseConnection connection);

    public abstract <T> List<T> findByNameFromDataBase(String name, DataBaseConnection connection);


}
