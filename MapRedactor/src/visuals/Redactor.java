package visuals;

import debuffs.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Redactor {
    private int sizeX;
    private int sizeY;
    private Field field;
    private Field debaffField;
    private SettingsMenu settingsMenu;
    private ArrayList<Debuffs> availableDebuffs = new ArrayList<>();

    private ArrayList<Debuffs> placedBuffer = new ArrayList<>();


    public Redactor(SettingsMenu settingsMenu){
        this.settingsMenu = settingsMenu;
        this.sizeX= settingsMenu.getSizeX();
        this.sizeY= settingsMenu.getSizeY();
        field = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        debaffField = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        this.availableDebuffs = settingsMenu.getAvailableDebuffs();

    }

    public void createEmptyField(){
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field.setFieldable(i, j, new EmptyPlace());
                debaffField.setFieldable(i,j, new EmptyPlace());

            }
        }
    }

    public void createPrevField(){
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field.setFieldable(i, j, new EmptyPlace());
                debaffField.setFieldable(i,j, new EmptyPlace());
            }
        }
        for (Debuffs debuffs : settingsMenu.placedDebuffs){
            field.setFieldable(debuffs.getY(),debuffs.getX(),debuffs);
            debaffField.setFieldable(debuffs.getY(),debuffs.getX(),debuffs);
        }
        this.placedBuffer = settingsMenu.getPlacedDebuffs();
    }

    public void placeDebuffs(){
System.out.println("You can place next debuffs:");
for (Debuffs debuffs : settingsMenu.availableDebuffs){
    System.out.println(settingsMenu.availableDebuffs.indexOf(debuffs)+": " + debuffs.getSymbol() + " "  + debuffs.getName());}
System.out.println(settingsMenu.availableDebuffs.size()+": Create new debuff");
int choose = RedactorMenu.scanner.nextInt();
if (choose < settingsMenu.availableDebuffs.size()){
    System.out.println("Enter X position: ");
     int placeX = RedactorMenu.scanner.nextInt();
    System.out.println("Enter Y position: ");
    int placeY = RedactorMenu.scanner.nextInt();
    if (placeX>=0 && placeX < field.getSizeX() && placeY>=0 && placeY<field.getSizeY()){
        if (debaffField.getFieldable(placeY, placeX) instanceof EmptyPlace){
            Debuffs newDebuff = settingsMenu.availableDebuffs.get(choose).clone();
            debaffField.setFieldable(placeY, placeX, newDebuff);
            field.setFieldable(placeY, placeX, newDebuff);
            newDebuff.setY(placeY);
            newDebuff.setX(placeX);
            placedBuffer.add(newDebuff);
        }
        else System.out.println("Can't place here");
    }else System.out.println("Can't place here");

}
else if (choose == settingsMenu.availableDebuffs.size()){
    debuffCreator();
}
settingsMenu.setPlacedDebuffs(placedBuffer);
    }


    private void debuffCreator(){
        RedactorMenu.scanner.nextLine();
        System.out.println("Name of new debuff: ");
        String name = RedactorMenu.scanner.nextLine();

        System.out.println("Symbol of new debuff:");
        String symbol = RedactorMenu.scanner.nextLine() + "\t";

        System.out.println("Fall for walk units:");
        double debForWalkable = RedactorMenu.scanner.nextDouble();

        System.out.println("Fall for units with horse:");
        double debForHorsable = RedactorMenu.scanner.nextDouble();

        System.out.println("Fall for units with bow:");
        double debForBowable = RedactorMenu.scanner.nextDouble();

        availableDebuffs.add(new NewDebuff(debForWalkable, debForBowable, debForHorsable, symbol, name));
        settingsMenu.setAvailableDebuffs(availableDebuffs);
    }
    public void showField(){
        field.showField();
    }

    public void fileOutputer(String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(settingsMenu);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
}

