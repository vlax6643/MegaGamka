package units;

import visuals.Fieldable;

public class HorseArcher extends Unit implements Horsable, Fieldable {
    public HorseArcher() {
        health = 25;
        damage = 40;
        distanceOfAttack = 3;
        armor = 2;
        distanceOfWalk = 15;
        cost = 25;
        reward = 5;
        name = "HorseArcher";
        allySymbol = "\u001B[32m"+"H\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"H\t"+"\u001B[0m";
    }


}