package contact_book.Cherednychenko.config_properties;

import contact_book.Cherednychenko.annotations.SystemProp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigurationWorkMode {

    @SystemProp("app.mode")
    private WorkModeType workMode;

// EnumMap<WorkModeType, Integer> workModes = new EnumMap<WorkModeType, Integer>(WorkModeType.class);

public enum WorkModeType{
    API,
    FILE,
    MEMORY,
    DATABASE
}

}