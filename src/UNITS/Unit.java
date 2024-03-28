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

    protected int onFire = 0;
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

    public int getOnFire() {
        return onFire;
    }

    public void setOnFire(int onFire) {
        this.onFire = onFire;
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


    public void takeDamage(int damage){
        if (armor>0){
            if (armor<=damage){
                damage -= armor;
                this.setArmor(0);
                this.setHealth(health-damage);

                if (health<0) { setHealth(0); }
            } else  {
                this.setArmor(armor-damage);

//todo методы с маленькой, пакеты с маленькой. Добавить this. Дракон летает не учитывает debuff, если дракон сел в лес, то оно становиться болотом, болото становиться огнем, на гору просто садится. Если наступил на огонь 3 хода теряет по 3 хп. Атакует тоже огнем.
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




    public  void movingWASD(Field field, Field debuffField,String command){

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
                            if ((field.getFieldable(this.y-1, this.x) instanceof Fire)){
                                field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                                field.setFieldable(this.y - 1, this.x, this);
                                this.setY(this.y - 1);
                                this.setOnFire(this.onFire+3);
                                System.out.println("\u001B[31m"+"Fire is so HOT -2HP for " + "\u001B[0m" + this.getSymbol());
                                System.out.println("\u001B[31m"+"Fire will deal damage for " + "\u001B[0m"+ this.getOnFire() +" more turns");
                                this.takeDamage(2);
                                setDistanceOfWalk(distanceOfWalk-1);
                            }else {
                            field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                            field.setFieldable(this.y - 1, this.x, this);
                            this.setY(this.y - 1);
                            setDistanceOfWalk(distanceOfWalk-getDebuff(field, debuffField, this.x, this.y));
                            }

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

    public void enemyMoving(Field field, Field debuffField){
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




    public void moving(Field field, Field debuufField) {

}



    // Методы для работы с health, damage и другими параметрами юнита...
}

