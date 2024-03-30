package units;

import visuals.Fieldable;

public class Knight extends Unit implements Horsable, Fieldable {
    public Knight() {
        health = 30;
        damage = 5;
        distanceOfAttack = 1;
        armor = 3;
        distanceOfWalk = 6;
        cost = 20;
        reward = 5;
        allySymbol = "\u001B[32m"+"K\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"K\t"+"\u001B[0m";
    }

}