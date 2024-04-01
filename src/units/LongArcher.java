package units;

import visuals.Fieldable;

public class LongArcher extends Unit implements Bowable, Fieldable {
    public LongArcher() {
        health = 30;
        damage = 6;
        distanceOfAttack = 5;
        armor = 8;
        distanceOfWalk = 2;
        cost = 15;
        reward = 5;
        name = "LongArcher";
        allySymbol = "\u001B[32m"+"L\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"L\t"+"\u001B[0m";
    }

}