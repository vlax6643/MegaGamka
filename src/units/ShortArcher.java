package units;

import visuals.Fieldable;

public class ShortArcher extends Unit implements Bowable, Fieldable {
    public ShortArcher() {
        health = 25;
        damage = 3;
        distanceOfAttack = 3;
        armor = 4;
        distanceOfWalk = 4;
        cost = 19;
        reward = 5;
        name = "ShortArcher";
        allySymbol = "\u001B[32m"+"S\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"S\t"+"\u001B[0m";
    }


}