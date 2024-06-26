package units;

public class Crossbow extends Unit implements Bowable {
    public Crossbow() {
        health = 40;
        damage = 7;
        distanceOfAttack = 6;
        armor = 3;
        distanceOfWalk = 2;
        cost = 23;
        reward = 5;
        name = "Crossbow";
        allySymbol = "\u001B[32m"+"S\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"S\t"+"\u001B[0m";
    }



}