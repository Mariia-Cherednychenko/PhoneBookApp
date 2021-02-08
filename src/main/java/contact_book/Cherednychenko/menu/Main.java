package contact_book.Cherednychenko.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.config.AppProperties;
import contact_book.Cherednychenko.config.ConfigLoader;
import contact_book.Cherednychenko.config.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import contact_book.Cherednychenko.services.ContactsService;
import contact_book.Cherednychenko.services.UserService;
import contact_book.Cherednychenko.services.implementations.ApiContactService;
import contact_book.Cherednychenko.services.implementations.ApiUserService;
import contact_book.Cherednychenko.services.implementations.FileContactsService;
import contact_book.Cherednychenko.services.implementations.InMemoryContactsService;
import contact_book.Cherednychenko.utility.ContactsSerializer;
import contact_book.Cherednychenko.utility.PerfoundContactSerializer;

import java.net.http.HttpClient;
import java.util.Properties;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        AppProperties appProperties = new AppProperties(AppProperties.ProfileType.valueOf(System.getProperties().getProperty("profile").toUpperCase()));
        ConfigurationWorkMode configurationWorkMode = new ConfigurationWorkMode();
        ConfigLoader configLoader = new ConfigLoader(appProperties, configurationWorkMode);
        Properties configProp = new Properties();
        Scanner scanner = new Scanner(System.in);


        StartMenu startMenu = new StartMenu(configurationWorkMode, scanner);
        boolean menuLaunch = startMenu.checkMenuLaunch();
        boolean congirLoaded = configLoader.configurationLoad(configProp);
        if ( menuLaunch == true && congirLoaded== true) {

            ObjectMapper objectMapper = new ObjectMapper();
            ContactsSerializer contactsSerializer = new PerfoundContactSerializer();
            HttpClient httpClient = HttpClient.newBuilder().build();
            String baseUri = configProp.getProperty("api.base-url");
            String baseFile = configProp.getProperty("file.path");
            UserService userService = null;
            ContactsService contactsService = null;

            if(configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.API)){
                userService = new ApiUserService(baseUri,objectMapper,httpClient);
                contactsService = new ApiContactService(userService,httpClient,objectMapper,baseUri);
            }
            else if(configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.FILE)){
                contactsService = new FileContactsService(contactsSerializer, baseFile);
            }
            else if(configurationWorkMode.getWorkMode().equals(ConfigurationWorkMode.WorkModeType.MEMORY)){
                contactsService = new InMemoryContactsService();
            }
            else{
                new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфигурации из файла " +
                        "/ Error loading configuration"));
            }

            MainMenu mainMenu = new MainMenu(configurationWorkMode, userService, scanner);



            mainMenu.addActionsOptionsToMainMenu(configurationWorkMode,userService, contactsService, scanner);

            mainMenu.run();
        }
    }
}

