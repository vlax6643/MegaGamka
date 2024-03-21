package visuals;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private String command;

    SettingsMenu settingsMenu = new SettingsMenu();


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
                    settings();
                    break;

                case "4":
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (!command.equals("4"));
    }

    private void startChooseGame() {
        Game game = new Game(settingsMenu);
        game.createEmptyField();
        game.startGame();
    }

    private void startDefaultGame() {
        Game game = new Game(settingsMenu);
        game.createEmptyField();
        game.startDefGame();
    }


    private void settings() {
        System.out.println("new amount of debuffs: ");
        settingsMenu.setAmountOfDebuffs(scanner.nextInt());
        System.out.println("new amount of Units: ");
        settingsMenu.setAmountOFUnits(scanner.nextInt());
        System.out.println("new amount of money:");
        settingsMenu.setMoney(scanner.nextInt());
    }

}
