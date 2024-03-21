package UNITS;

import Debuffs.*;

import visuals.Fieldable;
import visuals.Field;
import visuals.EmptyPlace;
import visuals.Game;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public abstract class Unit implements Fieldable {
    protected int x, y; // Текущие координаты юнита
    protected int health;
    protected int damage;
    protected int distanceOfAttack;
    protected int armor;
    protected double distanceOfWalk;
    protected int cost;
    protected int reward;
    protected String team;
    protected String allySymbol;
    protected String enemySymbol;

    // Геттеры и сеттеры для координат x и y
    @Override
    public String getSymbol (){
        if (team.equals("ally")){
            return allySymbol;
        }
        else if (team.equals("enemy")){
            return enemySymbol;
        }
        return null;
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public int getDamage() {
        return damage;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDistanceOfWalk(double distanceOfWalk) {
        this.distanceOfWalk = distanceOfWalk;
    }
    public double getDistanceOfWalk() {
        return distanceOfWalk;
    }

    public int getDistanceOfAttack() {
        return distanceOfAttack;
    }

    public int getCost(){
        return cost;
    }
    public void Attack() {
        // Реализация атаки
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }


    public void TakeDamage(int damage){
        if (armor>0){
            if (armor<=damage){
                damage -= armor;
                setArmor(0);
                setHealth(health-damage);

                if (health<0) { setHealth(0); }
            } else  {
                setArmor(armor-damage);


            }
        } else {
            setHealth(health-damage);
            if (health<0){
                setHealth(0);
            }
        }
    }


    private double getDebuff(Field field, Field debuffField, int x, int y){
        double penya = 0;
        if (this instanceof Bowable){
            if(debuffField.getFieldable(y,x) instanceof Hill) {
                penya = 2.2;
            } else if (debuffField.getFieldable(y,x) instanceof Swamp) {
                penya =  1.8;
            } else if (debuffField.getFieldable(y,x) instanceof Tree) {
                penya =  1;
            }
        } else if (this instanceof Horsable){
            if(debuffField.getFieldable(y,x) instanceof Hill) {
                penya =  1.2;
            } else if (debuffField.getFieldable(y,x) instanceof Swamp) {
                penya =  2.2;
            } else if (debuffField.getFieldable(y,x) instanceof Tree) {
                penya =  1.5;
            }
        } else if (this instanceof Walkable) {
            if(debuffField.getFieldable(y,x) instanceof Hill) {
                penya =  2;
            } else if (debuffField.getFieldable(y,x) instanceof Swamp) {
                penya =  1.5;
            } else if (debuffField.getFieldable(y,x) instanceof Tree) {
                penya =  1.2;
            }
        } return penya;

}




    public  void MovingWASD(Field field, Field debuffField,String command){

        System.out.println("Current position: (" + x + ", " + y + ")");
        System.out.println("You can move up to " + distanceOfWalk + " steps.");




            switch (command){


                case ("w"):
                    if ((this.y-1)>= 0) {
                        if ((field.getFieldable(this.y-1, this.x) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y - 1, this.x, this);
                            this.setY(this.y - 1);
                            setDistanceOfWalk(distanceOfWalk-1);

                        } else if ((field.getFieldable(this.y-1, this.x) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y - 1, this.x, this);
                            this.setY(this.y - 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));

                        } else { System.out.println("The cell is occupied. Please choose another destination."); }

                        }
                    else {System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");}
                    break;



                case ("a"):
                    if ((this.x-1)>= 0) {
                        if ((field.getFieldable(this.y, this.x-1) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x - 1, this);
                            this.setX(this.x - 1);
                            setDistanceOfWalk(distanceOfWalk-1);


                        } else if ((field.getFieldable(this.y, this.x-1) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x-1, this);
                            this.setX(this.x - 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));

                        }
                        else { System.out.println("The cell is occupied. Please choose another destination."); }
                    }
                    else {System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");}

                    break;
                case ("s"):
                    if ((this.y+1)< field.getSizeY()) {
                        if ((field.getFieldable(this.y+1, this.x) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y + 1, this.x, this);
                            this.setY(this.y + 1);
                            setDistanceOfWalk(distanceOfWalk-1);

                        }else if ((field.getFieldable(this.y+1, this.x) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y + 1, this.x, this);
                            this.setY(this.y + 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));

                        }
                        else { System.out.println("The cell is occupied. Please choose another destination."); }
                    }
                    else {System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");}
                    break;
                case ("d"):
                    if ((this.x+1)< field.getSizeX()) {
                        if ((field.getFieldable(this.y, this.x+1) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x + 1, this);
                            this.setX(this.x + 1);
                            setDistanceOfWalk(distanceOfWalk-1);

                        }else if ((field.getFieldable(this.y, this.x+1) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x+1, this);
                            this.setX(this.x + 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));

                        }
                        else { System.out.println("The cell is occupied. Please choose another destination."); }
                    }
                    else {System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");}
                    break;
                    }




    }

    public void EnemyMoving(Field field, Field debuffField){
        Random random = new Random();
            switch (random.nextInt(4)){
                case (0):
                    if ((this.y-1)>= 0) {
                        if ((field.getFieldable(this.y-1, this.x) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y - 1, this.x, this);
                            this.setY(this.y - 1);
                            setDistanceOfWalk(distanceOfWalk-1);

                        } else if ((field.getFieldable(this.y-1, this.x) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y - 1, this.x, this);
                            this.setY(this.y - 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));

                        }
                    }
                    break;

                case (1):
                    if ((this.x-1)>= 0) {
                        if ((field.getFieldable(this.y, this.x-1) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x - 1, this);
                            this.setX(this.x - 1);
                            setDistanceOfWalk(distanceOfWalk-1);


                        } else if ((field.getFieldable(this.y, this.x-1) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x-1, this);
                            this.setX(this.x - 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));


                        }

                    }

                    break;
                case (2):
                    if ((this.y+1)< field.getSizeY()) {
                        if ((field.getFieldable(this.y+1, this.x) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y + 1, this.x, this);
                            this.setY(this.y + 1);
                            setDistanceOfWalk(distanceOfWalk-1);


                        }else if ((field.getFieldable(this.y+1, this.x) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y + 1, this.x, this);
                            this.setY(this.y + 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));


                        }

                    }

                    break;
                case (3):
                    if ((this.x+1)< field.getSizeX()) {
                        if ((field.getFieldable(this.y, this.x+1) instanceof EmptyPlace)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x + 1, this);
                            this.setX(this.x + 1);
                            setDistanceOfWalk(distanceOfWalk-1);


                        }else if ((field.getFieldable(this.y, this.x+1) instanceof Debuffs)) {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y, this.x+1, this);
                            this.setX(this.x + 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));


                        }

                    }
                    break;
            }

    }




    public void Moving(Field field) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current position: (" + x + ", " + y + ")");
        System.out.println("You can move up to " + distanceOfWalk + " steps. Enter new X and Y coordinates:");

        int newX, newY;
        do {
            System.out.print("Enter new X coordinate: ");
            newX = scanner.nextInt();
            System.out.print("Enter new Y coordinate: ");
            newY = scanner.nextInt();

            if (newX >= 0 && newX < field.getSizeX() && newY >= 0 && newY < field.getSizeY()) {
                if (Math.abs(newX - this.x) + Math.abs(newY - this.y) <= distanceOfWalk) {
                    if ((field.getFieldable(newY, newX) instanceof EmptyPlace) || (field.getFieldable(newY, newX) instanceof Debuffs)) {





                        field.setFieldable(this.y, this.x, new EmptyPlace());
                        field.setFieldable(newY, newX, this);
                        this.setX(newX);
                        this.setY(newY);
                        System.out.println("Unit moved to new position: (" + newX + ", " + newY + ")");
                        break;




                    } else {
                        System.out.println("The cell is occupied. Please choose another destination.");
                        Moving(field);
                    }
                } else {
                    System.out.println("Destination is too far. Please choose a closer destination.");
                    Moving(field);
                }
            } else {
                System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");
                Moving(field);
            }
        } while (true);
    field.showField();
    }



    // Методы для работы с health, damage и другими параметрами юнита...
}

