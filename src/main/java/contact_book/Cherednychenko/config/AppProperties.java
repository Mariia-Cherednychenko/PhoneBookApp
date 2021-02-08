package contact_book.Cherednychenko.config;

import contact_book.Cherednychenko.exception.IncorrectProfileException;
import lombok.Data;

@Data
public class AppProperties {
   // @SystemProp("app.prop")

    private String profileValue;
    private  ProfileType profile;
    public enum ProfileType{
        DEV,
        PROD
    }

    public AppProperties(ProfileType profileType) {
        profile=profileType;
        setProfileValue();
    }

    public void setProfileValue() {
        if (profile.equals(ProfileType.DEV)) profileValue="app-dev.properties";
       else if(profile.equals(ProfileType.PROD)) profileValue="app-prod.properties";
       else new IncorrectProfileException().getMessage("Неверный профиль, должен быть DEV или PROD/" +
                    "Incorrect profile, it should be DEV or PROD");

    }
}
