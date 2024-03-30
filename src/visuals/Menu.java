package visuals;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    public SettingsMenu settingsMenu;


    // Constructor

    public  Menu() {



        do {
            System.out.println("Welcome to Gamka, let's go");
            System.out.println("1: Start game with choose units");
            System.out.println("2: Start game with default units");
            System.out.println("3: Settings");
            System.out.println("4: Exit");

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
                    settings();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (!command.equals("5"));
    }

    private void startGameOnCastomMap() {
        System.out.println("Enter the name of your map:");
        String fileName = scanner.nextLine();
        Game game = new Game(Objects.requireNonNull(FileInputer(fileName + ".dat")));
        game.createPrevField();
        game.startGameOnCastom();
    }
    private SettingsMenu FileInputer(String fileName){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            return (SettingsMenu) ois.readObject();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
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
