package visuals;

public class SettingsMenu {
    int amountOfDebuffs = 9;
    int amountOFUnits = 3;
    int amountOfEnemies = 3;
    int money=60;
    int sizeX =10;
    int sizeY =10;

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
