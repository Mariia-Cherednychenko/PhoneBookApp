package contact_book.Cherednychenko.config_properties;

import contact_book.Cherednychenko.annotations.SystemProp;
import contact_book.Cherednychenko.exception.IncorrectProfileException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppPropertiesProfile {


    private String profileValue;

    @SystemProp("app.profile")
    private  ProfileType profile;

    public enum ProfileType{
        DEV,
        PROD
    }


    public AppPropertiesProfile(ProfileType profileType) {
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
