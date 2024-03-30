package visuals;

import debuffs.Debuffs;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class RedactorMenu {
    public static Scanner scanner = new Scanner(System.in);
    private String command;
    private boolean isEnd = false;


    public RedactorMenu(){
        do {

            System.out.println("1: Create new map");
            System.out.println("2: Redactor");
            System.out.println("3: Exit");
            command = scanner.nextLine();
            switch (command){
                case "1" -> Creator();
                case "2" -> Redactor();
                case "3" -> System.out.println("EXITING...");
                default -> System.out.println("Uncorrect command");
            }

        }while(!command.equals("3"));
    }

    private void Creator() {
        System.out.println("Enter field size (x x y), x = ");
        int x = scanner.nextInt();
        System.out.println("y = ");
        int y = scanner.nextInt();
        SettingsMenu settingsMenu = new SettingsMenu(x,y);
        Redactor redactor = new Redactor(settingsMenu);
        redactor.createEmptyField();
        redactor.showField();
        do {
            System.out.println("1: Place debuff on field");
            System.out.println("2: Save field");
            System.out.println("3: Exit");
            int command = scanner.nextInt();
            switch (command){
                case 1 -> {
                    redactor.placeDebuffs();
                    redactor.showField();
                }
                case 2 -> {
                    System.out.println("Enter name file to save:");
                    RedactorMenu.scanner.nextLine();
                    String fileName = scanner.nextLine();
                    redactor.fileOutputer(fileName+".dat");
                    System.out.println("Map has been saved");
                    isEnd = true;
                }
                case 3 -> {
                    System.out.println("EXITING...");
                    isEnd = true;
                }

                default -> System.out.println("Uncorrect command");
            }

        }while (!isEnd);
    }
    private void Redactor(){
        System.out.println("Enter the name of your map:");

        String fileName = scanner.nextLine();
        Redactor redactor = new Redactor(Objects.requireNonNull(FileInputer(fileName + ".dat")));
        redactor.createPrevField();
        redactor.showField();
        do {
            System.out.println("1: Place debuff on field");
            System.out.println("2: Save field");
            System.out.println("3: Exit");
            int command = scanner.nextInt();
            switch (command){
                case 1 -> {
                    redactor.placeDebuffs();
                    redactor.showField();
                }
                case 2 -> {
                    redactor.fileOutputer(fileName + ".dat");
                    System.out.println("Map has been saved");
                    isEnd = true;
                }
                case 3 -> {
                    System.out.println("EXITING...");
                    isEnd = true;
                }

                default -> System.out.println("Uncorrect command");
            }

        }while (!isEnd);
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


}
