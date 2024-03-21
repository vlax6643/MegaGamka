package UNITS;

import UNITS.Unit;
import visuals.Fieldable;

public class cavalry extends Unit implements Horsable {
    public cavalry() {
        health = 50;
        damage = 2;
        distanceOfAttack = 1;
        armor = 7;
        distanceOfWalk = 5;
        cost = 23;
        reward = 5;
        allySymbol = "\u001B[32m"+"C\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"C\t"+"\u001B[0m";
    }

    String symbol = "C\t";

}