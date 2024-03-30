package units;

import visuals.Fieldable;

public class Spearman extends Unit implements Walkable, Fieldable {
    public Spearman() {
        health =25;
        damage = 3;
        distanceOfAttack = 1;
        armor = 4;
        distanceOfWalk = 6;
        cost = 15;
        reward = 5;
        allySymbol = "\u001B[32m"+"P\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"P\t"+"\u001B[0m";
    }



}
