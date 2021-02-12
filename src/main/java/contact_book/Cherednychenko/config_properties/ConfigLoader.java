package contact_book.Cherednychenko.config_properties;

import contact_book.Cherednychenko.exception.FailedLoadigConfigurationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.util.Properties;

@AllArgsConstructor
@NoArgsConstructor
public class ConfigLoader {

    AppPropertiesProfile appPropertiesProfile;
    private ConfigurationWorkMode configurationWorkMode;


    public boolean configurationLoad(Properties property) {
        try {
            //setConfigWorkMode();
            property.load(new FileInputStream(appPropertiesProfile.getProfileValue()));

            return true;
        } catch (Exception e) {
            new FailedLoadigConfigurationException().getMessage(("Ошибка при загрузке конфирупации из файла " +
                    "/ Error loading configuration"));
        }
        return false;
    }


}






