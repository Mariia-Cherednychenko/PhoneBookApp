import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config_properties.AppPropertiesProfile;
import contact_book.Cherednychenko.config_properties.ConfigLoader;
import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedToCreateServiceException;
import contact_book.Cherednychenko.menu.MainMenu;
import contact_book.Cherednychenko.menu.StartMenu;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.ServiceCreationPerMode;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import contact_book.Cherednychenko.utility.PerfoundContactSerializer;
import reflection.ConfigLoadingReflection;

import java.net.http.HttpClient;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FailedToCreateServiceException {

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


            ServiceCreationPerMode serviceCreationPerMode = new ServiceCreationPerMode(
                    objectMapper,
                    contactsSerializer,
                    httpClient,
                    baseUri,
                    baseFile);
            serviceCreationPerMode.invoke();

            UserService userService=serviceCreationPerMode.getUserService();
            ContactsService contactsService=serviceCreationPerMode.getContactsService();

            MainMenu mainMenu = new MainMenu(configurationWorkMode, userService, scanner);

            mainMenu.addActionsOptionsToMainMenu(configurationWorkMode, userService, contactsService, scanner);

            mainMenu.run();
        }
        else if (menuLaunch== false && configLoaded == true){
            System.out.println("Программа закрывается / Program is closing");
        }



    }
}
