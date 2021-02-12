package contact_book.Cherednychenko.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config_properties.AppPropertiesProfile;
import contact_book.Cherednychenko.config_properties.ConfigLoader;
import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.services.implementations.ApiContactService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;
import contact_book.Cherednychenko.services.implementations.FileContactsService;
import contact_book.Cherednychenko.services.implementations.InMemoryContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import contact_book.Cherednychenko.utility.PerfoundContactSerializer;
import reflection.ConfigLoadingReflection;

import java.net.http.HttpClient;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ConfigLoadingReflection configLoadingRef = new ConfigLoadingReflection(); // loading properties from the file
        AppPropertiesProfile appPropertiesProfile = configLoadingRef.getSystemProps(AppPropertiesProfile.class);
        appPropertiesProfile.setProfileValue();
        System.out.println(appPropertiesProfile.toString());
        System.out.println("Load file " + appPropertiesProfile.getProfileValue());


       ConfigurationWorkMode configurationWorkMode = new ConfigurationWorkMode();


        Properties configProp = new Properties();
        Scanner scanner = new Scanner(System.in);

        StartMenu startMenu = new StartMenu(configurationWorkMode, scanner);
        boolean menuLaunch = startMenu.checkMenuLaunch();
        ConfigLoader configLoader = new ConfigLoader(appPropertiesProfile,configurationWorkMode);
        boolean configLoaded = configLoader.configurationLoad(configProp);
        if ( menuLaunch == true && configLoaded== true) {

            ObjectMapper objectMapper = new ObjectMapper();
            ContactsSerializer contactsSerializer = new PerfoundContactSerializer();
            HttpClient httpClient = HttpClient.newBuilder().build();
            String baseUri = configProp.getProperty("api.base-url");
            String baseFile = configProp.getProperty("file.path");
            UserService userService = null;
            ContactsService contactsService = null;

            if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)) {
                userService = new ApiUserService(baseUri, objectMapper, httpClient);
                contactsService = new ApiContactService(userService, httpClient, objectMapper, baseUri);
            } else if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.FILE)) {
                contactsService = new FileContactsService(contactsSerializer, baseFile);
            } else if (configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.MEMORY)) {
                contactsService = new InMemoryContactsService();
            } else {
                new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфигурации из файла " +
                        "/ Error loading configuration"));
            }

            MainMenu mainMenu = new MainMenu(configurationWorkMode, userService, scanner);

            mainMenu.addActionsOptionsToMainMenu(configurationWorkMode, userService, contactsService, scanner);

            mainMenu.run();
        }
        else if (menuLaunch== false && configLoaded == true){
            System.out.println("Программа закрывается / Program is closing");
        }



    }
}
