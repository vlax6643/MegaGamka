package units;

public class Axeman extends Unit implements Walkable{
    public Axeman() {
        health = 45;
        damage = 9;
        distanceOfAttack = 1;
        armor = 3;
        distanceOfWalk = 4;
        cost = 20;
        reward = 5;
        name = "Axeman";
        allySymbol = "\u001B[32m"+"A\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"A\t"+"\u001B[0m";
    }



}
