package units;

import debuffs.*;
import visuals.EmptyPlace;
import visuals.Field;

import java.util.Scanner;

public class BabyDragon extends Unit implements Dragon {
    public BabyDragon() {
        health = 100;
        distanceOfAttack = 3;
        armor = 0;
        distanceOfWalk = 5;
        damage = 5;
        cost = 25;
        reward = 5;
        name = "BabyDragon";
        allySymbol = "\u001B[32m"+"D\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"D\t"+"\u001B[0m";
    }

    @Override
    public void moving(Field field, Field debuffField){
        Scanner scanner = new Scanner(System.in);

        int newX, newY;
            System.out.print("Enter new X coordinate: ");
            newX = scanner.nextInt();
            System.out.print("Enter new Y coordinate: ");
            newY = scanner.nextInt();

            if (newX >= 0 && newX < field.getSizeX() && newY >= 0 && newY < field.getSizeY()) {
                if (Math.abs(newX - this.x) + Math.abs(newY - this.y) <=this.distanceOfWalk) {
                    if (field.getFieldable(newY, newX) instanceof EmptyPlace) {
                        field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                        field.setFieldable(newY, newX, this);
                        setDistanceOfWalk(this.distanceOfWalk - (Math.abs(newX - this.x) + Math.abs(newY - this.y)));
                        this.setX(newX);
                        this.setY(newY);
                        System.out.println("Unit moved to new position: (" + newX + ", " + newY + ")");

                    } else if (field.getFieldable(newY, newX) instanceof Debuffs){
                        field.setFieldable(this.y, this.x, debuffField.getFieldable(this.y, this.x));
                        if (debuffField.getFieldable(newY,newX) instanceof Swamp) {
                            debuffField.setFieldable(newY, newX, new Fire());
                        } else if (debuffField.getFieldable(newY,newX) instanceof Tree) {
                            debuffField.setFieldable(newY, newX, new Swamp());
                        }
                        field.setFieldable(newY, newX, this);
                        setDistanceOfWalk(this.distanceOfWalk - (Math.abs(newX - this.x) + Math.abs(newY - this.y)));
                        this.setX(newX);
                        this.setY(newY);
                        System.out.println("Unit moved to new position: (" + newX + ", " + newY + ")");
                    }
                    else {
                        System.out.println("The cell is occupied. Please choose another destination.");
                    }
                } else {
                    System.out.println("Destination is too far. Please choose a closer destination.");
                }
            } else {
                System.out.println("Coordinates are out of bounds. Please choose coordinates within the field.");
            }

    }



}
