package visuals;

import Debuffs.Debuffs;
import Debuffs.Hill;
import Debuffs.Swamp;
import Debuffs.Tree;
import UNITS.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int sizeX;
    private int sizeY;
    private Field field;
    private Field debaffField;
    private int healthSumAllay = 0;
    private int healthSumEnemy = 0;

private SettingsMenu settingsMenu;

    private int amountOfDebuffs;

    private int amountOfUnits = 0;
    private boolean isGameFinished = false;
    public ArrayList<Fieldable> unitsArrayList = new ArrayList<>();
    public ArrayList<Unit> unitTechArrayList = new ArrayList<>();
    public ArrayList<Unit> enemyTechArrayList = new ArrayList<>();
    EmptyPlace empty = new EmptyPlace();
    private int money;
    private int amountOfEnemies;


Scanner scanner = new Scanner(System.in);
    public Game(SettingsMenu settingsMenu){
        this.settingsMenu = settingsMenu;
        this.sizeX= settingsMenu.getSizeX();
        this.sizeY= settingsMenu.getSizeY();
        field = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        debaffField = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        this.amountOfEnemies = settingsMenu.getAmountOfEnemies();
    }

    public void createEmptyField(){
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field.setFieldable(i, j, new EmptyPlace());
                debaffField.setFieldable(i,j, new EmptyPlace());
            }
        }
    }






    public void generateDebuffs() {
        ArrayList<Fieldable> debuffs = new ArrayList<>();
        debuffs.add(new Hill());
        debuffs.add(new Swamp());
        debuffs.add(new Tree());

        Random random = new Random();

        for (int i = 0; i < settingsMenu.getAmountOfDebuffs(); i++) {
            boolean isPlaced;
                    do {
                        int x = 1 + random.nextInt(sizeX - 2);
                        int y = 1 + random.nextInt(sizeY - 2);
                        Fieldable randomDebuff = debuffs.get(random.nextInt(debuffs.size()));
                        if ((y > 0) && (x > 0) && (field.getFieldable(y, x) instanceof EmptyPlace)) {
                            field.setFieldable(y, x, randomDebuff);
                            debaffField.setFieldable(y,x, randomDebuff);
                            isPlaced= true;
                        } else{
                            isPlaced = false;
                        }
                    }
                    while (!isPlaced);
        }

    }

    public void placeDebuffOnField(int x, int y, Fieldable debuff) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            field.setFieldable(y, x, debuff);
        } else {
            System.out.println("Coordinates are out of bounds.");
        }
    }



    public void startDefGame(){
        generateDebuffs();
        possesDefPlayers();
        possesEnemies();
        showGame();
        do {
            turn();
            enemyTurn();
            showGame();
        }while (!gameEnd());

        if (amountOfEnemies==0){
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                      УРА ПОБЕДА!!!                                       ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
        }
        if (amountOfUnits==0){
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                              ПРОИГРАЛ                                       ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
        }


    }

    public void startGame(){
        generateDebuffs();
        possesPlayers();
        possesEnemies();
        showGame();
do {
    turn();
    enemyTurn();
    showGame();
}while (!gameEnd());

        if (amountOfEnemies==0){
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                      УРА ПЕРАМОГА!!!                                       ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
        }
        if (amountOfUnits==0){
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                              OH Shit                                       ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
        }


    }

    private boolean gameEnd() {

       for (Unit unit : unitTechArrayList)
       {
            healthSumAllay += unit.getHealth();
       }
       for (Unit unit : enemyTechArrayList)
       {
           healthSumEnemy += unit.getHealth();
       }

        return amountOfEnemies==0 || amountOfUnits==0;
    }



    private void possesDefPlayers(){
        Axeman axeman =new Axeman();
        axeman.setTeam("ally");
        unitsArrayList.add(axeman);
        unitTechArrayList.add(axeman);
        amountOfUnits++;

        LongArcher longArcher = new LongArcher();
        longArcher.setTeam("ally");
        unitsArrayList.add(longArcher);
        unitTechArrayList.add(longArcher);
        amountOfUnits++;

        HorseArcher horseArcher =new HorseArcher();
        horseArcher.setTeam("ally");
        unitsArrayList.add(horseArcher);
        unitTechArrayList.add(horseArcher);
        amountOfUnits++;

        for (Unit unit : unitTechArrayList) {
            placeUnit(unit);
        }
    }

    private void possesPlayers() {
        String choose;
        int babyDragonCost = (new BabyDragon().getCost());
        int axemanCost = (new Axeman()).getCost();
        int swordsmanCost = (new Swordsman()).getCost();
        int spearmanCost = (new Spearman()).getCost();
        int longArcherCost = (new LongArcher()).getCost();
        int shortArcherCost = (new ShortArcher()).getCost();
        int crossbowCost = (new Crossbow()).getCost();
        int knightCost = (new Knight()).getCost();
        int cavalryCost = (new cavalry()).getCost();
        int horseArcherCost = (new HorseArcher()).getCost();

        System.out.println("Choose " + " units");
        System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
        System.out.println("0: Baby Dragon " + babyDragonCost+" Деревянных");
        System.out.println("1:Axeman " +axemanCost+" Деревянных");
        System.out.println("2:Swordsman " + swordsmanCost+" Деревянных");
        System.out.println("3:Spearman " + spearmanCost+" Деревянных");
        System.out.println("4:LongArcher " + longArcherCost+" Деревянных");
        System.out.println("5:ShortArcher " + shortArcherCost+" Деревянных");
        System.out.println("6:Crossbow " + crossbowCost+" Деревянных");
        System.out.println("7:Knight " + knightCost +" Деревянных");
        System.out.println("8:cavalry " + cavalryCost+" Деревянных");
        System.out.println("9:HorseArcher " + horseArcherCost+" Деревянных");
        System.out.println("enter 10 to close shop");
        boolean shoping = false;
do {
    choose = scanner.nextLine();
    switch (choose) {
        case "0":
            if ((settingsMenu.getMoney() - babyDragonCost) >= 0) {
                BabyDragon babyDragon = new BabyDragon();
                babyDragon.setTeam("ally");
                unitsArrayList.add(babyDragon);
                unitTechArrayList.add(babyDragon);
                settingsMenu.setMoney(settingsMenu.getMoney() - babyDragonCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");
            }

            break;
        case "1":
            if ((settingsMenu.getMoney() - axemanCost) >= 0) {
                Axeman axeman =new Axeman();
                axeman.setTeam("ally");
                unitsArrayList.add(axeman);
                unitTechArrayList.add(axeman);
                settingsMenu.setMoney(settingsMenu.getMoney() - axemanCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }

            break;
        case "2":
            if ((settingsMenu.getMoney() - swordsmanCost) >= 0) {
                Swordsman swordsman = new Swordsman();
                swordsman.setTeam("ally");
                unitsArrayList.add(swordsman);
                unitTechArrayList.add(swordsman);
                settingsMenu.setMoney(settingsMenu.getMoney() - swordsmanCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }

            break;
        case "3":
            if ((settingsMenu.getMoney() - spearmanCost) >= 0) {
                Spearman spearman =new Spearman();
                spearman.setTeam("ally");
                unitsArrayList.add(spearman);
                unitTechArrayList.add(spearman);
                settingsMenu.setMoney(settingsMenu.getMoney() - spearmanCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }
            break;
        case "4":
            if ((settingsMenu.getMoney() - longArcherCost) >= 0) {
                LongArcher longArcher = new LongArcher();
                longArcher.setTeam("ally");
                unitsArrayList.add(longArcher);
                unitTechArrayList.add(longArcher);
                settingsMenu.setMoney(settingsMenu.getMoney() - longArcherCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }
            break;
        case "5":
            if ((settingsMenu.getMoney() - shortArcherCost) >= 0) {
                ShortArcher shortArcher = new ShortArcher();
                shortArcher.setTeam("ally");
                unitsArrayList.add(shortArcher);
                unitTechArrayList.add(shortArcher);
                settingsMenu.setMoney(settingsMenu.getMoney() - shortArcherCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }
            break;
        case "6":
            if ((settingsMenu.getMoney() - crossbowCost) >= 0) {
                Crossbow crossbow = new Crossbow();
                crossbow.setTeam("ally");
                unitsArrayList.add(crossbow);
                unitTechArrayList.add(crossbow);
                settingsMenu.setMoney(settingsMenu.getMoney() - crossbowCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }
            break;
        case "7":
            if ((settingsMenu.getMoney() - knightCost) >= 0) {
                Knight knight =new Knight();
                knight.setTeam("ally");
                unitsArrayList.add(knight);
                unitTechArrayList.add(knight);
                settingsMenu.setMoney(settingsMenu.getMoney() - knightCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");

            }
            break;
        case "8":
            if ((settingsMenu.getMoney() - cavalryCost) >= 0) {
                cavalry cavalry = new cavalry();
                cavalry.setTeam("ally");
                unitsArrayList.add(cavalry);
                unitTechArrayList.add(cavalry);
                settingsMenu.setMoney(settingsMenu.getMoney() - cavalryCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("Try again");
            }
            break;
        case "9":
            if ((settingsMenu.getMoney() - horseArcherCost) >= 0) {
                HorseArcher horseArcher =new HorseArcher();
                horseArcher.setTeam("ally");
                unitsArrayList.add(horseArcher);
                unitTechArrayList.add(horseArcher);
                settingsMenu.setMoney(settingsMenu.getMoney() - horseArcherCost);
                amountOfUnits++;
                System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
            } else {
                System.out.println("in your wallet not enough money");

            }

            break;

        case "10":
            System.out.println("Good luck");
            shoping = true;
            break;
        default:
            System.out.println("error");

    }
}while (!shoping);


        for (Unit unit : unitTechArrayList) {
            placeUnit(unit);
        }

    }

    private void placeUnit(Unit unit) {
        Random random = new Random();
            boolean isPlaced;
            do {
                int y = sizeY-1;
                int x = random.nextInt(sizeX);

                if ((field.getFieldable(y, x) instanceof EmptyPlace)) {
                    field.setFieldable(y, x, unit);
                        unit.setX(x);
                        unit.setY(y);

                    isPlaced= true;
                } else{
                    isPlaced = false;
                }
            }
            while (!isPlaced);
        }


    private void possesEnemies() {
        Random random = new Random();
        String rand;
        int amountBuff = amountOfEnemies;
        for (int i = 0; i < amountOfEnemies ; i++) {


            rand = String.valueOf(random.nextInt(9) + 1);
            switch (rand) {
                case "1":
                        Axeman axeman = new Axeman();
                        axeman.setTeam("enemy");
                        enemyTechArrayList.add(axeman);

                    break;
                case "2":

                        Swordsman swordsman = new Swordsman();
                        swordsman.setTeam("enemy");
                        enemyTechArrayList.add(swordsman);



                    break;
                case "3":

                        Spearman spearman = new Spearman();
                        spearman.setTeam("enemy");
                        enemyTechArrayList.add(spearman);
                    break;
                case "4":
                        LongArcher longArcher = new LongArcher();
                        longArcher.setTeam("enemy");
                        enemyTechArrayList.add(longArcher);

                    break;
                case "5":

                        ShortArcher shortArcher = new ShortArcher();
                        shortArcher.setTeam("enemy");
                        enemyTechArrayList.add(shortArcher);

                    break;
                case "6":

                        Crossbow crossbow = new Crossbow();
                        crossbow.setTeam("enemy");
                        enemyTechArrayList.add(crossbow);

                    break;
                case "7":

                        Knight knight = new Knight();
                        knight.setTeam("enemy");
                        enemyTechArrayList.add(knight);

                    break;
                case "8":

                        cavalry cavalry = new cavalry();
                        cavalry.setTeam("enemy");
                        enemyTechArrayList.add(cavalry);

                    break;
                case "9":

                        HorseArcher horseArcher = new HorseArcher();
                        horseArcher.setTeam("enemy");
                        enemyTechArrayList.add(horseArcher);

                    break;

                default:
                    System.out.println("error");

            }
        }
        amountOfEnemies =amountBuff;


        for (Unit unit : enemyTechArrayList) {
            placeEnemy(unit);
        }
    }

    private void placeEnemy(Unit unit) {
        Random random = new Random();
        boolean isPlaced;
        do {
            int y = 0;
            int x = random.nextInt(sizeX);

            if ((field.getFieldable(y, x) instanceof EmptyPlace)) {
                field.setFieldable(y, x, unit);
                unit.setX(x);
                unit.setY(y);

                isPlaced= true;
            } else{
                isPlaced = false;
            }
        }
        while (!isPlaced);
    }




    private void turn() {
        System.out.println();


            for (Unit unit : unitTechArrayList) {
if (unit.getHealth()>0) {
    if (unit.getOnFire() > 0) {
        unit.setOnFire(unit.getOnFire() - 1);
        System.out.println("\u001B[31m" + "Fire is so HOT -2HP for " + "\u001B[0m" + unit.getSymbol());
        System.out.println("\u001B[31m" + "Fire will deal damage for " + "\u001B[0m" + unit.getOnFire() + " more turns");
        unit.takeDamage(2);
        if (unit.getHealth() == 0) {
            amountOfEnemies--;
            field.setFieldable(unit.getY(), unit.getX(), debaffField.getFieldable(unit.getY(), unit.getX()));
        }
    }
}
                if (unit.getHealth() > 0) {
                    boolean endTurn = false;
                    System.out.println("This turn for: " + unit.getSymbol() + "(" + unit.getX() + "," + unit.getY() + ")");

                    double distanceBuffer = unit.getDistanceOfWalk();
                   if (unit instanceof Dragon){


                       do {
                           System.out.println("You can move up to " + unit.getDistanceOfWalk() + " steps.");
                           System.out.println("To fly press w, to attack press q, to skip turn press x");
                           String choose = scanner.nextLine();
                           switch (choose) {
                               case "w" -> {
                                   unit.moving(field, debaffField);
                                   if (unit.getDistanceOfWalk() < 1) {
                                       endTurn = true;
                                   }
                                   showGame();
                               }
                               case "q" -> {
                                   dragonAttack(unit);
                                   endTurn = true;
                                   showGame();
                               }
                               case "x" -> endTurn = true;
                               default -> System.out.println("uncorrected command, try again");
                           }
                       } while (!endTurn);
                   }
                   else {

                       do {

                        System.out.println("To walk use WASD, to attack press q, to skip turn press x");
                        System.out.println("You can move up to " + unit.getDistanceOfWalk() + " steps.");
                        System.out.println("Your radius of attack " + unit.getDistanceOfAttack());
                        String choose = scanner.nextLine();

                        switch (choose) {
                            case "w", "a", "s", "d" -> {
                                System.out.println("moving");
                                unit.movingWASD(field, debaffField, choose);

                                if (unit.getDistanceOfWalk() < 1) {
                                    endTurn = true;
                                }
                                showGame();
                            }
                            case "q" -> {
                                attack(unit);
                                endTurn = true;
                                showGame();
                            }
                            case "x" -> endTurn = true;
                            default -> System.out.println("uncorrected command, try again");
                        }

                    } while (!endTurn);
                   }

                    unit.setDistanceOfWalk(distanceBuffer);

                }
            }

    }
    private void dragonAttack(Unit unit){
        int enemyX;
        int enemyY;
        System.out.print("Enter X coordinate: ");
        enemyX = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        enemyY = scanner.nextInt();
        if (enemyX >= 0 && enemyX < field.getSizeX() && enemyY >= 0 && enemyY < field.getSizeY()) {
            if (Math.abs(enemyX - unit.getX()) + Math.abs(enemyY - unit.getY()) <= unit.getDistanceOfAttack()) {
                if (field.getFieldable(enemyY, enemyX) instanceof Unit ) {
                    for (Unit enemy : enemyTechArrayList){
                        if (enemy.equals(field.getFieldable(enemyY,enemyX))){
                            enemy.takeDamage(unit.getDamage());
                            System.out.println("FIIIRE -" + unit.getDamage() + "HP for " + enemy.getSymbol());
                            enemy.setOnFire(enemy.getOnFire()+3);
                            if (enemy.getHealth()==0){
                                amountOfEnemies--;
                                field.setFieldable(enemy.getY(), enemy.getX(), new EmptyPlace());
                            }
                        }
                    }

                } else {
                    System.out.println("You can't attack this place");
                }
            } else {
                System.out.println("Destination is too far. Please choose a closer destination.");
            }
        } else {
            System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");
        }

    }
    private void attack(Unit unit){
        int enemyX;
        int enemyY;
        System.out.print("Enter X coordinate: ");
        enemyX = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        enemyY = scanner.nextInt();
        if (enemyX >= 0 && enemyX < field.getSizeX() && enemyY >= 0 && enemyY < field.getSizeY()) {
            if (Math.abs(enemyX - unit.getX()) + Math.abs(enemyY - unit.getY()) <= unit.getDistanceOfAttack()) {
                if (field.getFieldable(enemyY, enemyX) instanceof Unit ) {
                    for (Unit enemy : enemyTechArrayList){
                        if (enemy.equals(field.getFieldable(enemyY,enemyX))){
                            enemy.takeDamage(unit.getDamage());

                            if (enemy.getHealth()==0){
                                amountOfEnemies--;

                                field.setFieldable(enemy.getY(), enemy.getX(), new EmptyPlace());
                            }
                        }
                    }

                } else {
                    System.out.println("You can't attack this place");
                }
            } else {
                System.out.println("Destination is too far. Please choose a closer destination.");
            }
        } else {
            System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");
        }

    }

    private void enemyTurn(){
for (Unit unit : enemyTechArrayList) {
    if (unit.getHealth() > 0) {
        boolean endTurn = false;
        double distanceBuffer = unit.getDistanceOfWalk();
        if (unit.getOnFire()>0){
            unit.setOnFire(unit.getOnFire()-1);
            System.out.println("Fire is so HOT -2HP for " + unit.getSymbol());
            System.out.println("Fire will deal damage for "+ unit.getOnFire() +"more turns");
            unit.takeDamage(2);}
        do {
            Unit ally = isElementInRadius(unit.getDistanceOfAttack(), unit);
            if (ally != null) {
                if (ally.getHealth()>0) {
                    ally.takeDamage(unit.getDamage());
                    System.out.println(unit.getDistanceOfAttack() + "oh no, " + unit.getSymbol() + " attacked your unit. -" + unit.getDamage());
                    System.out.println(ally.getSymbol() + " HP: " + ally.getHealth());
                    endTurn = true;
                    if (ally.getHealth() == 0) {
                        amountOfUnits--;

                        field.setFieldable(ally.getY(), ally.getX(), new EmptyPlace());
                    }
                }  else {
                    unit.enemyMoving(field, debaffField);
                    if (unit.getDistanceOfWalk() < 1) {
                        endTurn = true;
                    }
                }
            } else {
                unit.enemyMoving(field, debaffField);
                if (unit.getDistanceOfWalk() < 1) {
                    endTurn = true;
                }
            }
            showGame();
        } while (!endTurn);
        unit.setDistanceOfWalk(distanceBuffer);
    }
}
    }

    public Unit isElementInRadius(int distance, Unit unit) {
for (Unit ally : unitTechArrayList){
    if (Math.abs(unit.getX() - ally.getX()) + Math.abs(unit.getY() - ally.getY()) <= unit.getDistanceOfAttack())
    {
        return ally;
    }
}
return null;
    }
       /* for (int i = (int) (unit.getY() - unit.getDistanceOfWalk()); i < unit.getY() + unit.getDistanceOfWalk(); i++) {
            for (int j = (int) (unit.getX() - unit.getDistanceOfWalk()); j < (int) (unit.getX() + unit.getDistanceOfWalk()); j++) {
                if (i >= 0 && j >= 0 && i<field.getSizeY() && j < field.getSizeX()) {
                    if (field.getFieldable(i, j) instanceof Unit) {
                        for (Unit ally : unitTechArrayList) {
                            if (ally.equals(field.getFieldable(i, j))) {
                                return ally;
                            }
                        }
                    }
                }
            }

        }
        return null;
    }*/


        /*for (int i = 0; i < field.getSizeY(); i++) {
            for (int j = 0; j < field.getSizeX(); j++) {
                for (int di =-distance; di <= +distance; di++) {
                    for (int dj = -distance; dj <= + distance; dj++) {
                        int ni = i + di;
                        int nj = j + dj;
                        if (ni >= 0 && ni < field.getSizeY() && nj >= 0 && nj < field.getSizeX()) {
                            if (field.getFieldable(ni,nj) instanceof Unit) {
                                for (Unit ally : unitTechArrayList){
                                    if (ally.equals(field.getFieldable(ni,nj))){
                                        return ally;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;*/


private void showGame(){
        field.showField();
        System.out.println("Your heroes: ");
        for (Unit unit : unitTechArrayList){
            System.out.println(unit.getSymbol() + "(" + unit.getX() + ", " + unit.getY() + ") || " +   "Health "+ unit.getHealth() + " || Armor " + unit.getArmor());
        }
    for (Unit unit : enemyTechArrayList){
        System.out.println(unit.getSymbol() + "(" + unit.getX() + ", " + unit.getY() + ") || " +   "Health "+ unit.getHealth() + " || Armor " + unit.getArmor());
    }
}

}
