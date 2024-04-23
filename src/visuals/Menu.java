package visuals;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    public SettingsMenu settingsMenu;

public SavedSettings savedSettings;
    // Constructor

    public  Menu() {



        do {
            System.out.println("Welcome to Gamka, let's go");
            System.out.println("1: Start game with choose units");
            System.out.println("2: Start game with default units");
            System.out.println("3: Start game on castom field");
            System.out.println("4: Continue saved game");
            System.out.println("5: Exit");

            command = scanner.nextLine();
            switch (command) {
                case "1":
                    startChooseGame();
                    break;

                case "2":
                    startDefaultGame();
                    break;

                case "3":
                    startGameOnCastomMap();
                case "4":
                    startSavedGame();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (!command.equals("5"));
    }

    private void startSavedGame() {
        boolean isCreated = false;
        do {
            System.out.println("Enter the name of your map:");
            String fileName = scanner.nextLine();
            SavedSettings savedSettings1 = FileGameInputer(fileName + ".dat");
            if (savedSettings1 != null){
            Game game = new Game(Objects.requireNonNull(savedSettings1));
            game.createSavedField();
            game.continGame();
            isCreated = true;
            }else {
                System.out.println("Не удалось загрузить карту.");
            }
        }while (!isCreated);
    }
    private void startGameOnCastomMap() {
        boolean isCreated = false;
        do {
            System.out.println("Enter the name of your map:");
            String fileName = scanner.nextLine();
            SettingsMenu settingsMenu = FileInputer(fileName + ".dat");
            if (settingsMenu != null) {
                Game game = new Game(Objects.requireNonNull(settingsMenu));
                game.settingsMenu.setWood(100);
                game.settingsMenu.setRock(100);
                game.createPrevField();
                game.startGameOnCastom();
                isCreated = true;
            } else {
                System.out.println("Не удалось загрузить карту.");
            }
        }while (!isCreated);
    }


    private SettingsMenu FileInputer(String fileName) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SettingsMenu) ois.readObject();
        } catch (Exception ex) {
            System.out.println("Произошла ошибка при чтении файла: " + ex.getMessage());
        }
        return null;
    }



    private SavedSettings FileGameInputer(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не существует: " + fileName);
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SavedSettings) ois.readObject();
        } catch (Exception ex) {
            System.out.println("Произошла ошибка при чтении файла: " + ex.getMessage());
        }
        return null;
    }

    private void startChooseGame() {
        settingsMenu =new SettingsMenu();
        Game game = new Game(settingsMenu);
        game.createEmptyField();
        game.startGame();
    }

    private void startDefaultGame() {
        settingsMenu =new SettingsMenu();
        Game game = new Game(settingsMenu);
        game.createEmptyField();
        game.startDefGame();
    }


    private void settings() {
      System.out.println("404");
    }

}
