package reflection;

import contact_book.Cherednychenko.annotations.SystemProp;
import contact_book.Cherednychenko.config_properties.AppPropertiesProfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ConfigLoadingReflection {

    public  <T> T getSystemProps (Class <T> clazz){
        Object object = createObject(clazz);
        extractedProps(object,System.getProperties());
        return (T) object;
    }

    private Object createObject (Class clazz) {

        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("NEED DEFAULT CONSTRUCTOR",e);
        }

    }

    private static void extractedProps(Object object, Properties properties) {
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            // проверка - помечено ли поле аннотацией
            if (field.isAnnotationPresent(SystemProp.class)) {
                // достать свойство для аннотации
                SystemProp annotation = field.getAnnotation(SystemProp.class);
                // достали то, что весит над свойством
                String propName = annotation.value();
                // достаем свойство и записываем в поле
                String value = properties.getProperty(propName); // достаем свойства из любылх мест (более универсальный)
                // сделали доступным для записи, т.к. оно приватное
                field.setAccessible(true);
                try {
                    // задем поле объектом, который пришел на вход и знаением из proprties
                    field.set(object, AppPropertiesProfile.ProfileType.valueOf(value.toUpperCase()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T getFileProp(Class <T> clazz, String file) {

        try (InputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Object object = createObject(clazz);
            extractedProps(object, properties);
            return (T) object;
        } catch (IOException e) {
            throw new RuntimeException("Failes load properties from "+ file,e);
        }
    }
}
