package contact_book.Cherednychenko.menu;

import contact_book.Cherednychenko.config_properties.ConfigurationWorkMode;
import contact_book.Cherednychenko.exception.IncorrectInputContactException;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class StartMenu {

    ConfigurationWorkMode configurationWorkMode;
    Scanner scanner;

    public boolean checkMenuLaunch() {

        System.out.println("\nПрограмма запущена / Program is launched\n" +
                "-------------------------------------------\n");

        System.out.print("Программа предоставляет 3 варианта ведения работы с контактами." +
                "\n/ Program provides 3 modes of work with contact" +
                "\n1 - работа через приложение / work via app" +
                "\n2 - работа через файл / work via file" +
                "\n3 - работа через внутреннюю память / work via inner memory" +
                "\n0 - выйти из программы  / leave the program" +
                "\nВведите вариант работы / Enter the work mode: ");

        int input = scanner.nextInt();
        return getStartMenuResponse(input);
    }

    private boolean getStartMenuResponse(int input) {
        try {
            scanner.nextLine();
            switch (input) {
                case 1: {
                    String mode = ConfigurationWorkMode.WorkModeType.API.toString();
                    configurationWorkMode.setWorkMode(ConfigurationWorkMode.WorkModeType.valueOf(mode));
                    System.setProperty("app.service.workmode",mode);
                    return true;
                }

                case 2: {
                    String mode = ConfigurationWorkMode.WorkModeType.FILE.toString();
                    configurationWorkMode.setWorkMode(ConfigurationWorkMode.WorkModeType.valueOf(mode));
                    System.setProperty("file.service.workmode",mode);
                    return true;
                }

                case 3: {
                    String mode = ConfigurationWorkMode.WorkModeType.MEMORY.toString();
                    configurationWorkMode.setWorkMode(ConfigurationWorkMode.WorkModeType.valueOf(mode));
                    System.setProperty("memory.service.workmode",mode);
                    return true;
                }
                case 4: {
                    return false;
                }

            }
        } catch (Exception e) {
            new IncorrectInputContactException().getMessage("Введите значения от 1 до 4");
            return false;
        }
        scanner.nextLine();
        return false;
    }
}

