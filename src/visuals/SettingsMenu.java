package visuals;

import debuffs.*;

import java.io.Serializable;
import java.util.ArrayList;

public class SettingsMenu implements Serializable {
    int amountOfDebuffs;
    int amountOFUnits;
    int amountOfEnemies;
    int money;
    int sizeX;
    int sizeY;


    public ArrayList<Debuffs> placedDebuffs;
    public ArrayList<Debuffs> availableDebuffs = new ArrayList<>();

    public SettingsMenu(){
       amountOfDebuffs = 9;
       amountOFUnits = 3;
       amountOfEnemies = 3;
       money=60;
       sizeX =10;
       sizeY =10;
       placedDebuffs = null;

    }

    public SettingsMenu(int sizeX, int sizeY){
        this.amountOfEnemies = 3;
        this.money=60;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.placedDebuffs = null;
        availableDebuffs.add(new Hill());
        availableDebuffs.add(new Tree());
        availableDebuffs.add(new Swamp());
    }

    public void setAvailableDebuffs(ArrayList<Debuffs> availableDebuffs) {
        this.availableDebuffs = availableDebuffs;
    }

    public ArrayList<Debuffs> getAvailableDebuffs() {
        return availableDebuffs;
    }

    public ArrayList<Debuffs> getPlacedDebuffs() {
        return placedDebuffs;
    }

    public void setPlacedDebuffs(ArrayList<Debuffs> placedDebuffs) {
        this.placedDebuffs = placedDebuffs;
    }

    public void setMoney(int money){
        this.money=money;
    }

    public int getAmountOfEnemies() {
        return amountOfEnemies;
    }

    public void setAmountOfEnemies(int amountOfEnemies) {
        this.amountOfEnemies = amountOfEnemies;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getMoney() {
        return money;
    }

    public void setAmountOfDebuffs(int amountOfDebuffs){
        this.amountOfDebuffs=amountOfDebuffs;
    }

    public int getAmountOfDebuffs() {
        return amountOfDebuffs;
    }

    public void setAmountOFUnits(int amountOFUnits){
        this.amountOFUnits = amountOFUnits;
    }

    public int getAmountOFUnits() {
        return amountOFUnits;
    }
}
