package contact_book.Cherednychenko.config;

import lombok.Data;

@Data
public class ConfigurationWorkMode {
    //@SystemProp("app.mode")
    private WorkModeType workMode;

   // EnumMap<WorkModeType, Integer> workModes = new EnumMap<WorkModeType, Integer>(WorkModeType.class);

    public enum WorkModeType{
        API,
        FILE,
        MEMORY
    }


}
