package visuals;

import buildings.*;
import debuffs.Debuffs;
import debuffs.Hill;
import debuffs.Swamp;
import debuffs.Tree;
import units.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.Scanner;

public class Game implements Serializable {
    private int sizeX;
    private int sizeY;
    private Field field;
    private Field debaffField;
    private int healthSumAllay = 0;
    private int healthSumEnemy = 0;

    private SettingsMenu settingsMenu;

    public SavedSettings savedSettings;

    private int amountOfUnits = 0;

    public ArrayList<Fieldable> unitsArrayList = new ArrayList<>();
    public ArrayList<Unit> unitTechArrayList = new ArrayList<>();
    public ArrayList<Unit> enemyTechArrayList = new ArrayList<>();

    private ArrayList<Unit> availableUnits = new ArrayList<>();


    private int amountOfEnemies;

    public ArrayList<Building> availableBuildings = new ArrayList<>();
    public ArrayList<Building> buildingsOnField = new ArrayList<>();
    public ArrayList<Building> upgratebleBuildingsOnField = new ArrayList<>();
    public ArrayList<Debuffs> placedDebuffsBuffer  = new ArrayList<>();
    private boolean endGame = false;

    public int hpBuff = 0;
    public int armorBuff = 0;
    public double distanceOfWalkBuff = 0;
    public double debuffBuff = 0;
    public int damageBuff = 0;
    private boolean isAcademyOnField = false;
    private boolean isMarketOnField = false;
    private int workshopcounter = 0;


Scanner scanner = new Scanner(System.in);
    public Game(SettingsMenu settingsMenu){
        this.settingsMenu = settingsMenu;
        this.sizeX= settingsMenu.getSizeX();
        this.sizeY= settingsMenu.getSizeY();
        field = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        debaffField = new Field(settingsMenu.getSizeX(), settingsMenu.getSizeY());
        this.amountOfEnemies = settingsMenu.getAmountOfEnemies();
        availableUnits.add(new BabyDragon());
        availableUnits.add(new Axeman());
        availableUnits.add(new Swordsman());
        availableUnits.add(new Spearman());
        availableUnits.add(new LongArcher());
        availableUnits.add(new ShortArcher());
        availableUnits.add(new Crossbow());
        availableUnits.add(new Knight());
        availableUnits.add(new cavalry());
        availableUnits.add(new HorseArcher());
    }
    public Game(SavedSettings savedSettings){
        this.settingsMenu = savedSettings.settingsMenu;
        this.sizeX = savedSettings.settingsMenu.getSizeX();
        this.sizeY = savedSettings.settingsMenu.getSizeY();
        field = new Field(savedSettings.settingsMenu.getSizeX(), savedSettings.settingsMenu.sizeY);
        debaffField = new Field(savedSettings.settingsMenu.getSizeX(), savedSettings.settingsMenu.sizeY);
        this.availableBuildings=savedSettings.availableBuildings;
        this.availableUnits=savedSettings.availableUnits;
        this.amountOfUnits=savedSettings.availableUnits.size();
        this.buildingsOnField = savedSettings.buildingsOnField;
        this.hpBuff=savedSettings.hpBuff;
        this.distanceOfWalkBuff=savedSettings.distanceOfWalkBuff;
        this.damageBuff=savedSettings.damageBuff;
        this.isMarketOnField=savedSettings.isMarketOnField;
        this.isAcademyOnField= savedSettings.isAcademyOnField;
        this.unitTechArrayList =savedSettings.unitTechArrayList;
        this.armorBuff=savedSettings.armorBuff;
        this.debuffBuff=savedSettings.debuffBuff;
        this.workshopcounter=savedSettings.workshopcounter;
        this.settingsMenu.placedDebuffs = savedSettings.placedDebuffsBuffer;
        this.upgratebleBuildingsOnField=savedSettings.upgratebleBuildingsOnField;
    }

    public void newAvailableBuildings(){
        this.availableBuildings.add(new IbolitHouse());
        this.availableBuildings.add(new Club());
        this.availableBuildings.add(new Smithy());
        this.availableBuildings.add(new Arsenal());
        this.availableBuildings.add(new Academy());
        this.availableBuildings.add(new Market());
        this.availableBuildings.add(new Workshop());
        this.availableBuildings.add(new Workshop());
        this.availableBuildings.add(new Workshop());
        this.availableBuildings.add(new Workshop());
    }



    public void createEmptyField(){
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field.setFieldable(i, j, new EmptyPlace());
                debaffField.setFieldable(i,j, new EmptyPlace());
            }
        }
    }

    public void createSavedField(){
        createPrevField();
        for (Unit unit : unitTechArrayList){
            placeUnit(unit);
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
            placedDebuffsBuffer.add(debuffs);
        }
    }




    public void generateDebuffs() {



        Random random = new Random();

        for (int i = 0; i < settingsMenu.getAmountOfDebuffs(); i++) {
            boolean isPlaced;
                   do{
                        Debuffs genDebuff = null;
                        switch (String.valueOf(random.nextInt(3) + 1)) {
                            case "1" -> genDebuff = new Hill();
                            case "2" -> genDebuff = new Swamp();
                            case "3" -> genDebuff = new Tree();
                        }


                        int x = 1 + random.nextInt(sizeX - 2);
                        int y = 1 + random.nextInt(sizeY - 2);

                        if ((y > 0) && (x > 0) && (field.getFieldable(y, x) instanceof EmptyPlace)) {
                            genDebuff.setX(x);
                            genDebuff.setY(y);
                            field.setFieldable(y, x, genDebuff);
                            //debaffField.setFieldable(y,x, randomDebuff);
                            placedDebuffsBuffer.add(genDebuff);
                            isPlaced= true;
                        } else{
                            isPlaced = false;
                        }
                    }
                    while (!isPlaced);
        }
        settingsMenu.setPlacedDebuffs(placedDebuffsBuffer);

    }

    private void placeDebuffs(){
        for (Debuffs debuffs : settingsMenu.placedDebuffs){
            debaffField.setFieldable(debuffs.getY(), debuffs.getX(), debuffs);
        }
    }

    private void newRound(){
        amountOfEnemies = settingsMenu.getAmountOfEnemies();
        possesEnemies();
        showGame();
        do {
            turn();
            enemyTurn();
            showGame();
        } while (!roundEnd());

        if (amountOfEnemies == 0) {
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                       НАБЕГ ПОВЕРГНУТ                                      ");
            System.out.println("             REWARD 10 wood, 10 rock, 10 coins                              ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            settingsMenu.setRock(settingsMenu.getRock()+10);
            settingsMenu.setWood(settingsMenu.getWood()+10);
            settingsMenu.setMoney(settingsMenu.getMoney() +10);
            System.out.println("Your Workshops give you " + workshopcounter*10 + " Coins");
            settingsMenu.setMoney(settingsMenu.getMoney()+workshopcounter*10);
        }
        if (amountOfUnits == 0) {
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("                              ПРОИГРАЛ                                     ");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            System.out.println("============================================================================");
            endGame = true;
        }
    }

    public void continGame(){
        showGame();
        do {
            System.out.println("1: Start new round");
            System.out.println("2: Buy new units");
            System.out.println("3: Building mod");
            System.out.println("4: Save game");
            System.out.println("5: exit");
            int choose = scanner.nextInt();
            scanner.nextLine();
            switch (choose){
                case 1 -> {
                    newRound();
                }
                case 2 -> {
                    BuyUnit();
                }
                case 3 -> {
                    System.out.println("1: New build ");
                    System.out.println("2: Upgrade buildings");
                    System.out.println("3: Open new unit in academy");
                    System.out.println("4: Change recourses in Market");
                    int choose1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choose1) {
                        case 1 ->  buildNewBuilding();
                        case 2 -> upgradeBuilding();
                        case 3 -> {
                            if (isAcademyOnField){
                                newAcademyUnit(); } else System.out.println("build academy to open new Units");
                        }
                        case 4 -> {
                            if (isMarketOnField){
                                ResourcesChenger(); } else System.out.println("build market to open new Units");
                        }
                        default -> System.out.println("Try again");

                    }
                }
                case 4 -> {
                    savedSettings = new SavedSettings(settingsMenu, availableBuildings, buildingsOnField, unitTechArrayList, availableUnits,
                            placedDebuffsBuffer,upgratebleBuildingsOnField ,hpBuff, armorBuff,distanceOfWalkBuff,debuffBuff,damageBuff,isAcademyOnField,isMarketOnField,workshopcounter);
                    scanner.nextLine();
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    fileOutputer(fileName + ".dat");
                    System.out.println("Game has been saved");
                }
                case 5 -> {
                    System.out.println("EXITING...");
                    endGame =true;
                }
            }

        }while (!endGame);
    }
    public void startGameOnCastom() {
        BuyUnit();
        newAvailableBuildings();
        showGame();
        do {
            System.out.println("1: Start new round");
            System.out.println("2: Buy new units");
            System.out.println("3: Building mod");
            System.out.println("4: Save game");
            System.out.println("5: exit");
            int choose = scanner.nextInt();
            scanner.nextLine();
            switch (choose){
                case 1 -> {
                    newRound();
                }
                case 2 -> {
                    BuyUnit();
                }
                case 3 -> {
                    System.out.println("1: New build ");
                    System.out.println("2: Upgrade buildings");
                    System.out.println("3: Open new unit in academy");
                    System.out.println("4: Change recourses in Market");
                    int choose1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choose1) {
                        case 1 ->  buildNewBuilding();
                        case 2 -> upgradeBuilding();
                        case 3 -> {
                            if (isAcademyOnField){
                                newAcademyUnit(); } else System.out.println("build academy to open new Units");
                        }
                        case 4 -> {
                            if (isMarketOnField){
                                ResourcesChenger(); } else System.out.println("build market to open new Units");
                        }
                        default -> System.out.println("Try again");

                    }
                }
                case 4 -> {
                    savedSettings = new SavedSettings(settingsMenu, availableBuildings, buildingsOnField, unitTechArrayList, availableUnits,
                            placedDebuffsBuffer,upgratebleBuildingsOnField ,hpBuff, armorBuff,distanceOfWalkBuff,debuffBuff,damageBuff,isAcademyOnField,isMarketOnField,workshopcounter);
                    scanner.nextLine();
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    fileOutputer(fileName + ".dat");
                    System.out.println("Game has been saved");
                }
                case 5 -> {
                    System.out.println("EXITING...");
                    endGame =true;
                }
            }

        }while (!endGame);

    }

    public void fileOutputer(String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(savedSettings);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
    public void startDefGame(){
        generateDebuffs();
        placeDebuffs();
        possesDefPlayers();
        newAvailableBuildings();
        showGame();
        do {
            System.out.println("1: Start new round");
            System.out.println("2: Buy new units");
            System.out.println("3: Building mod");
            System.out.println("4: Save game");
            System.out.println("5: exit");
            int choose = scanner.nextInt();
            scanner.nextLine();
            switch (choose){
                case 1 -> {
                    newRound();
                }
                case 2 -> {
                    BuyUnit();
                }
                case 3 -> {
                    System.out.println("1: New build ");
                    System.out.println("2: Upgrade buildings");
                    System.out.println("3: Open new unit in academy");
                    System.out.println("4: Change recourses in Market");
                    int choose1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choose1) {
                        case 1 ->  buildNewBuilding();
                        case 2 -> upgradeBuilding();
                        case 3 -> {
                            if (isAcademyOnField){
                                newAcademyUnit(); } else System.out.println("build academy to open new Units");
                        }
                        case 4 -> {
                            if (isMarketOnField){
                                ResourcesChenger(); } else System.out.println("build market to open new Units");
                        }
                        default -> System.out.println("Try again");
                    }

                }
                case 4 -> {
                    savedSettings = new SavedSettings(settingsMenu, availableBuildings, buildingsOnField, unitTechArrayList, availableUnits,
                            placedDebuffsBuffer,upgratebleBuildingsOnField ,hpBuff, armorBuff,distanceOfWalkBuff,debuffBuff,damageBuff,isAcademyOnField,isMarketOnField,workshopcounter);
                    scanner.nextLine();
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    fileOutputer(fileName + ".dat");
                    System.out.println("Game has been saved");
                }
                case 5 -> {
                    System.out.println("EXITING...");
                    endGame =true;
                }
            }

        }while (!endGame);

    }

    public void startGame(){
        generateDebuffs();
        placeDebuffs();
        BuyUnit();
        newAvailableBuildings();
        showGame();
        do {
            System.out.println("1: Start new round");
            System.out.println("2: Buy new units");
            System.out.println("3: Building mod");
            System.out.println("4: Save game");
            System.out.println("5: exit");
            int choose = scanner.nextInt();

            switch (choose){
                case 1 -> {
                    newRound();
                }
                case 2 -> {
                    BuyUnit();
                }
                case 3 -> {
                    System.out.println("1: New build ");
                    System.out.println("2: Upgrade buildings");
                    System.out.println("3: Open new unit in academy");
                    System.out.println("4: Change recourses in Market");
                    int choose1 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choose1) {
                        case 1 ->  buildNewBuilding();
                        case 2 -> upgradeBuilding();
                        case 3 -> {
                            if (isAcademyOnField){
                            newAcademyUnit(); } else System.out.println("build academy to open new Units");
                        }
                        case 4 -> {
                            if (isMarketOnField){
                                ResourcesChenger(); } else System.out.println("build market to open new Units");
                        }
                        default -> System.out.println("Try again");
                    }
                }
                case 4 -> {
                    savedSettings = new SavedSettings(settingsMenu, availableBuildings, buildingsOnField, unitTechArrayList, availableUnits,
                            placedDebuffsBuffer,upgratebleBuildingsOnField ,hpBuff, armorBuff,distanceOfWalkBuff,debuffBuff,damageBuff,isAcademyOnField,isMarketOnField,workshopcounter);
                    scanner.nextLine();
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    fileOutputer(fileName + ".dat");
                    System.out.println("Game has been saved");
                }
                case 5 -> {
                    System.out.println("EXITING...");
                    endGame =true;
                }
            }

        }while (!endGame);

    }

    private boolean roundEnd() {

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


    private void buildNewBuilding(){
        System.out.println("Your resources: ");
        System.out.println("Rock: " + settingsMenu.getRock());
        System.out.println("Wood: " + settingsMenu.getWood());
        System.out.println("Available building: ");
        for (Building building : availableBuildings){
            System.out.println(availableBuildings.indexOf(building) + ": " + building.getSymbol() + building.getName() +" Wood: "+building.getWoodCost() + " Rock: "+building.getRockCost());
        }
        int choose = scanner.nextInt();
        scanner.nextLine();
        if (choose < availableBuildings.size() && choose >= 0 ) {
            if (settingsMenu.getRock()>=availableBuildings.get(choose).getRockCost() && settingsMenu.getWood()>=availableBuildings.get(choose).getWoodCost()){
                buildingsOnField.add(availableBuildings.get(choose));
                if (availableBuildings.get(choose) instanceof Upgrateble){
                    upgratebleBuildingsOnField.add(availableBuildings.get(choose));
                    addBuff(availableBuildings.get(choose));
                }
                if (availableBuildings.get(choose) instanceof Academy){
                    isAcademyOnField = true;
                }
                if(availableBuildings.get(choose) instanceof Market){
                    isMarketOnField = true;
                }
                if(availableBuildings.get(choose) instanceof Workshop){
                    workshopcounter++;
                }
                settingsMenu.setWood(settingsMenu.getWood()-availableBuildings.get(choose).getWoodCost());
                settingsMenu.setRock(settingsMenu.getRock()-availableBuildings.get(choose).getRockCost());

                availableBuildings.remove(choose);
            } else System.out.println("You need more resources");
        } else System.out.println("Uncorrect command");
    }


    private void upgradeBuilding(){
        System.out.println("Your resources: ");
        System.out.println("Rock: " + settingsMenu.getRock());
        System.out.println("Wood: " + settingsMenu.getWood());
        System.out.println("You can upgrade: ");
       for (Building building : upgratebleBuildingsOnField)
           {
               System.out.println(upgratebleBuildingsOnField.indexOf(building) + ": " + building.getSymbol() + building.getName() +" Wood: "+building.getWoodCost() + " Rock: "+building.getRockCost());
           }
       int choose = scanner.nextInt();
       if (choose<upgratebleBuildingsOnField.size() && choose >= 0){
           if (settingsMenu.getRock()>=upgratebleBuildingsOnField.get(choose).getRockCost() && settingsMenu.getWood()>=upgratebleBuildingsOnField.get(choose).getWoodCost()){
               upgratebleBuildingsOnField.get(choose).setLevel(upgratebleBuildingsOnField.get(choose).getLevel()+1);
               addBuff(upgratebleBuildingsOnField.get(choose));
               settingsMenu.setRock(settingsMenu.getRock()-upgratebleBuildingsOnField.get(choose).getRockCost());
                settingsMenu.setWood(settingsMenu.getWood()-upgratebleBuildingsOnField.get(choose).getWoodCost());

           }else System.out.println("You need more resources");
       }else System.out.println("Try again");
    }
    private void addBuff(Building building){
        if (building instanceof  IbolitHouse){
            hpBuff ++;
            for (Unit unit : unitTechArrayList){
                unit.setHealth(unit.getHealth()+1);
            }
        } else if (building instanceof Club){
            System.out.println("1: get buff for walk distance");
            System.out.println("2: get buff for all debuffs");
            int newshoose = scanner.nextInt();
            switch (newshoose){
                case 1 -> {
                    distanceOfWalkBuff += 0.5;
                    for (Unit unit : unitTechArrayList){
                        unit.setDistanceOfWalk(unit.getDistanceOfWalk()+0.5);
                    }
                }
                case 2 -> {
                    debuffBuff += 0.5;
                    for (Debuffs debuffs : settingsMenu.placedDebuffs)
                    {
                        debuffs.setDebForBowable(debuffs.getDebForBowable()-0.5);
                        if (debuffs.getDebForBowable()<=1){
                            debuffs.setDebForBowable(1);
                        }
                        debuffs.setDebForHorsable(debuffs.getDebForHorsable()-0.5);
                        if (debuffs.getDebForHorsable()<=1){
                            debuffs.setDebForHorsable(1);
                        }
                        debuffs.setDebForWalkable(debuffs.getDebForWalkable()-0.5);
                        if (debuffs.getDebForWalkable()<=1){
                            debuffs.setDebForWalkable(1);
                        }
                    }
                }
            }
        } else if (building instanceof Smithy){
            damageBuff ++;
            for (Unit unit : unitTechArrayList){
                unit.setDamage(unit.getDamage()+1);
            }
        } else if (building instanceof Arsenal){
            armorBuff ++;
            for (Unit unit : unitTechArrayList){
                unit.setArmor(unit.getArmor()+1);
            }
        } else  if (building instanceof Academy){
            newAcademyUnit();
        }

    }

   private void ResourcesChenger() {
       boolean isChange = false;
       do {
           System.out.println("Your resources:");
           System.out.println("Money: " + settingsMenu.getMoney());
           System.out.println("Wood: " + settingsMenu.getWood());
           System.out.println("Rock: " + settingsMenu.getRock());
           System.out.println("1: Buy resources ( 1 Coin = 2 wood/rock");
           System.out.println("2: Sell resources (2 wood/rock = 1 coin)");
           System.out.println("3: Exit");
           int choose = scanner.nextInt();
           switch (choose) {
               case 1 -> {
                    buyRes();
                    isChange =true;
               }

               case 2 -> {
                   sellRes();
                   isChange =true;
               }
               case 3-> {
                   isChange =true;
               }
           }


       } while (!isChange);
   }

   private void sellRes(){
       boolean isChanged = false;
       do {
           System.out.println("I need sell ... ");
           System.out.println("1:wood");
           System.out.println("2:rock");
           System.out.println("3:exit");
           int choose = scanner.nextInt();
           switch (choose) {

               case 1 -> {
                   System.out.println("How many wood you want to sell?");
                   int count = scanner.nextInt();
                   scanner.nextLine();
                   if (count % 2 != 0) {
                       count--;
                   }
                   int check = count / 2;
                   System.out.print("for " + count + " woods you got " + check + " Coins. Are you agree? [y/n]");
                   String yn = scanner.nextLine();
                   if (yn.equals("y")) {
                       if (settingsMenu.getWood() >= count) {
                           settingsMenu.setMoney(settingsMenu.getMoney() + check);
                           settingsMenu.setWood(settingsMenu.getWood() - count);
                           isChanged = true;
                       } else {
                           System.out.println("You need more wood");
                       }
                   } else if (yn.equals("n")){
                       System.out.println("press no");
                       isChanged = true;
                   } else{
                       System.out.println("try again");
                   }
               }

               case 2->{
                   System.out.println("How many rock you want to sell?");
                   int count = scanner.nextInt();
                   scanner.nextLine();
                   if (count % 2 != 0) {
                       count--;
                   }
                   int check = count / 2;
                   System.out.print("for " + count + " rocks you got " + check + " Coins. Are you agree? [y/n]");
                   String yn1 = scanner.nextLine();
                   if (yn1.equals("y")) {
                       if (settingsMenu.getRock() >= count) {
                           settingsMenu.setMoney(settingsMenu.getMoney() + check);
                           settingsMenu.setRock(settingsMenu.getRock() - count);
                           isChanged = true;
                       } else {
                           System.out.println("You need more rock");
                       }
                   } else if (yn1.equals("n")){
                       System.out.println("press no");
                       isChanged = true;
                   } else{
                       System.out.println("try again");
                   }
               }

               case 3-> {System.out.println("Exiting...");
                   isChanged= true;}

               default -> System.out.println("uncorrect command");
           }
       }while (!isChanged);
   }
   private void buyRes(){
        boolean isChanged = false;
        do {
            System.out.println("I need to buy ... ");
            System.out.println("1:wood");
            System.out.println("2:rock");
            System.out.println("3:exit");
            int choose = scanner.nextInt();
            switch (choose) {

                case 1 -> {
                    System.out.println("How many wood you need?");
                    int count = scanner.nextInt();
                    scanner.nextLine();
                    if (count % 2 != 0) {
                        count--;
                    }
                    int check = count / 2;
                    System.out.print("for " + count + " woods you must pay " + check + " Coins. Are you agree? [y/n]");
                    String yn = scanner.nextLine();
                    if (yn.equals("y")) {
                        if (settingsMenu.getMoney() >= check) {
                            settingsMenu.setMoney(settingsMenu.getMoney() - check);
                            settingsMenu.setWood(settingsMenu.getWood() + count);
                            isChanged = true;
                        } else {
                            System.out.println("You need more money");
                        }
                    } else if (yn.equals("n")){
                        System.out.println("press no");
                        isChanged = true;
                    } else{
                        System.out.println("try again");
                    }
                }

                case 2->{
                    System.out.println("How many rock you need?");
                    int count = scanner.nextInt();
                    scanner.nextLine();
                    if (count % 2 != 0) {
                        count--;
                    }
                    int check = count / 2;
                    System.out.print("for " + count + " rocks you must pay " + check + " Coins. Are you agree? [y/n]");
                    String yn1 = scanner.nextLine();
                    if (yn1.equals("y")) {
                        if (settingsMenu.getMoney() >= check) {
                            settingsMenu.setMoney(settingsMenu.getMoney() - check);
                            settingsMenu.setRock(settingsMenu.getRock() + count);
                            isChanged = true;
                        } else {
                            System.out.println("You need more money");
                        }
                    } else if (yn1.equals("n")){
                        System.out.println("press no");
                        isChanged = true;
                    } else{
                        System.out.println("try again");
                    }
                }

                case 3-> {System.out.println("Exiting...");
                isChanged= true;}
                default -> System.out.println("uncorrect command");
            }
        }while (!isChanged);
   }



    private void newAcademyUnit(){

do{
    if (settingsMenu.getMoney()>=10) {
        System.out.println("Lets create new unit ");

        System.out.println("Name of new unit: ");
        String newName = scanner.nextLine();

        System.out.println("Symbol of new unit: ");
        String newSymbol = scanner.nextLine() + "\t";

        System.out.println("Damage of new unit: ");
        int newDamage = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Armor of new unit: ");
        int newArmor = scanner.nextInt();
        scanner.nextLine();
        System.out.println("HP of new unit");
        int newHP = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Distance of attack: ");
        int newDistanceOfAttack = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Distance of walk: ");
        double newDistanceOfWalk = scanner.nextDouble();
        scanner.nextLine();
        String newAllySymbol = "\u001B[32m" + newSymbol + "\u001B[0m";
        String newEnemySymbol = "\u001B[31m" + newSymbol + "\u001B[0m";
        int newCost = (int) ((newHP + newArmor + newDamage + newDistanceOfAttack + newDistanceOfWalk) / 5);

        System.out.println("Choose the class of your unit ");
        System.out.println("1: Walkable unit");
        System.out.println("2: Unit on Horse");
        System.out.println("3: Unit with bow");
        String choose = scanner.nextLine();

        System.out.println("You have to pay 10 coins for exploring this unit. In start of round you can buy this unit for" + newCost + " Деревянных");
        boolean isBuy = false;
        do {
            System.out.println("are we exploring this unit? [y/n]");
            String yn = scanner.nextLine();
            if (yn.equals("y")) {

                switch (choose) {

                    case "1" -> {
                        availableUnits.add(new NewWalkableUnit(newHP, newDamage, newDistanceOfAttack, newArmor, newDistanceOfWalk, newCost, newName, newAllySymbol, newEnemySymbol));
                        settingsMenu.setMoney(settingsMenu.getMoney() - 10);
                        isBuy = true;

                    }
                    case "2" -> {
                        availableUnits.add(new NewHorsableUnit(newHP, newDamage, newDistanceOfAttack, newArmor, newDistanceOfWalk, newCost, newName, newAllySymbol, newEnemySymbol));
                        settingsMenu.setMoney(settingsMenu.getMoney() - 10);
                        isBuy = true;

                    }
                    case "3" -> {
                        availableUnits.add(new NewBowableUnit(newHP, newDamage, newDistanceOfAttack, newArmor, newDistanceOfWalk, newCost, newName, newAllySymbol, newEnemySymbol));
                        settingsMenu.setMoney(settingsMenu.getMoney() - 10);
                        isBuy = true;

                    }
                }
            } else if (yn.equals("n")) {
                System.out.println("Press no");
                isBuy = true;

            } else {
                System.out.println("Try again");
            }
        } while (!isBuy);
        break;
    } else {
        System.out.println(" exploring units cost 10 coins");
        break;
    }
}while (true);
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

    private void BuyUnit() {
        int length = availableUnits.size();
        System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
        System.out.println("You can place next units:");
        for (Unit unit : availableUnits) {
            System.out.println(availableUnits.indexOf(unit) + ": " + unit.getSymbol() + " " + unit.getName() + " " + unit.getCost()+" Деревянных");
        }
        System.out.println(length+": To close shop");
        boolean endShoping = false;
        do {
            int choose = scanner.nextInt();
            if (choose < length && choose >= 0) {
                if (settingsMenu.getMoney() >= availableUnits.get(choose).getCost()) {
                    Unit newUnit = availableUnits.get(choose).clone();
                    newUnit.setTeam("ally");
                    newUnit.setDamage(newUnit.getDamage()+damageBuff);
                    newUnit.setArmor(newUnit.getArmor()+armorBuff);
                    newUnit.setHealth(newUnit.getHealth()+hpBuff);
                    newUnit.setDistanceOfWalk(newUnit.getDistanceOfWalk()-distanceOfWalkBuff);
                    unitsArrayList.add(newUnit);
                    unitTechArrayList.add(newUnit);
                    settingsMenu.setMoney(settingsMenu.getMoney() - newUnit.getCost());
                    amountOfUnits++;
                    placeUnit(newUnit);
                    System.out.println("In your wallet: " + settingsMenu.getMoney() + " Деревянных");
                } else System.out.println("You need more money");
            } else if (choose == length){
                endShoping = true;
            }
            else System.out.println("uncorrect command");
        } while (!endShoping);
    }





   /* private void possesPlayers() {
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
                placeUnit(babyDragon);
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
                placeUnit(axeman);
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
                placeUnit(swordsman);
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
                placeUnit(spearman);
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
                placeUnit(longArcher);
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
                placeUnit(shortArcher);
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
                placeUnit(crossbow);
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
                placeUnit(knight);
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
                placeUnit(cavalry);
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
                placeUnit(horseArcher);
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

    } */

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
        ArrayList<Unit> unitsToDelete = new ArrayList<>();


            for (Unit unit : unitTechArrayList) {
if (unit.getHealth()>0) {
    if (unit.getOnFire() > 0) {
        unit.setOnFire(unit.getOnFire() - 1);
        System.out.println("\u001B[31m" + "Fire is so HOT -2HP for " + "\u001B[0m" + unit.getSymbol());
        System.out.println("\u001B[31m" + "Fire will deal damage for " + "\u001B[0m" + unit.getOnFire() + " more turns");
        unit.takeDamage(2);
        if (unit.getHealth() == 0) {
            amountOfUnits--;
            unitsToDelete.add(unit);

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
                                unit.movingWASD(field, debaffField, settingsMenu.placedDebuffs ,choose);

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

        if (!unitsToDelete.isEmpty()){
            for (Unit unit : unitsToDelete){
                unitTechArrayList.remove(unit);
            }
        }
    }
    private void dragonAttack(Unit unit){

        ArrayList<Unit> enemyToDelete = new ArrayList<>();
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
                                enemyToDelete.add(enemy);
                                field.setFieldable(enemy.getY(), enemy.getX(), new EmptyPlace());
                            }
                        }
                    }
                    if (!enemyToDelete.isEmpty()){
                        for (Unit enemy : enemyToDelete){
                            enemyTechArrayList.remove(enemy);
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

        ArrayList<Unit> enemyToDelete = new ArrayList<>();
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
                                enemyToDelete.add(enemy);
                                field.setFieldable(enemy.getY(), enemy.getX(), new EmptyPlace());
                            }
                        }
                    }
                    if (!enemyToDelete.isEmpty()){
                        for (Unit enemy : enemyToDelete){
                            enemyTechArrayList.remove(enemy);
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
         ArrayList<Unit> unitsToDelete = new ArrayList<>();
         ArrayList<Unit> enemyToDelete = new ArrayList<>();
for (Unit unit : enemyTechArrayList) {

        boolean endTurn = false;
        double distanceBuffer = unit.getDistanceOfWalk();
    if (unit.getHealth() > 0) {
        if (unit.getOnFire() > 0) {
            unit.setOnFire(unit.getOnFire() - 1);
            System.out.println("Fire is so HOT -2HP for " + unit.getSymbol());
            System.out.println("Fire will deal damage for " + unit.getOnFire() + "more turns");
            unit.takeDamage(2);

            if (unit.getHealth() == 0) {
                amountOfEnemies--;
                enemyToDelete.add(unit);
                field.setFieldable(unit.getY(), unit.getX(), new EmptyPlace());
            }
        }
    }
        if (unit.getHealth() > 0) {
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
                        unitsToDelete.add(ally);
                        field.setFieldable(ally.getY(), ally.getX(), new EmptyPlace());
                    }
                }  else {
                    unit.enemyMoving(field, debaffField,  settingsMenu.placedDebuffs);
                    if (unit.getDistanceOfWalk() < 1) {
                        endTurn = true;
                    }
                }
            } else {
                unit.enemyMoving(field, debaffField,  settingsMenu.placedDebuffs);
                if (unit.getDistanceOfWalk() < 1) {
                    endTurn = true;
                }
            }
            showGame();
        } while (!endTurn);
        unit.setDistanceOfWalk(distanceBuffer);
    }
}
if (!enemyToDelete.isEmpty()){
    for (Unit enemy : enemyToDelete){
        enemyTechArrayList.remove(enemy);
    }
}
if (!unitsToDelete.isEmpty()){
    for (Unit unit : unitsToDelete){
        unitTechArrayList.remove(unit);
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
    System.out.println("In your willage:");
    for (Building building : buildingsOnField){
       System.out.print(building.getSymbol() + building.getName() + " level-" + building.getLevel() +" || ");
    }
    System.out.println();
        System.out.println("Your heroes: ");
        for (Unit unit : unitTechArrayList){
            System.out.println(unit.getSymbol() + "(" + unit.getX() + ", " + unit.getY() + ") || " +   "Health "+ unit.getHealth() + " || Armor " + unit.getArmor());
        }

    for (Unit unit : enemyTechArrayList){
        System.out.println(unit.getSymbol() + "(" + unit.getX() + ", " + unit.getY() + ") || " +   "Health "+ unit.getHealth() + " || Armor " + unit.getArmor());
    }
}


}
