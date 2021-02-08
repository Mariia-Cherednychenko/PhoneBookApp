package contact_book.Cherednychenko.config;

import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import lombok.AllArgsConstructor;

import java.io.FileInputStream;
import java.util.Properties;

@AllArgsConstructor
public class ConfigLoader {

    AppProperties appProperties;
    private ConfigurationWorkMode configurationWorkMode;

    public boolean configurationLoad (Properties property){
        try {

            //setConfigWorkMode();
            property.load(new FileInputStream(appProperties.getProfileValue()));

            return true;
        } catch (Exception e) {
            new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфирупации из файла " +
                    "/ Error loading configuration"));
        }
        return false;
    }

     private void setConfigWorkMode(){
        System.setProperty("app.service.workmode", configurationWorkMode.getWorkMode().toString());
     }
}



